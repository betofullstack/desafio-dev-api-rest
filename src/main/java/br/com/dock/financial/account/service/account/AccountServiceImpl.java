package br.com.dock.financial.account.service.account;

import br.com.dock.financial.account.entity.Account;
import br.com.dock.financial.account.entity.AccountHolder;
import br.com.dock.financial.account.entity.OperationTypeEnum;
import br.com.dock.financial.account.exception.BusinessException;
import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.repository.account.AccountRepository;
import br.com.dock.financial.account.service.account.dto.AccountDto;
import br.com.dock.financial.account.service.account.dto.CreateAccountDto;
import br.com.dock.financial.account.service.account.dto.DepositAccountDto;
import br.com.dock.financial.account.service.account.dto.WithDrawAccountDto;
import br.com.dock.financial.account.service.holder.AccountHolderService;
import br.com.dock.financial.account.service.statement.StatementService;
import br.com.dock.financial.account.service.statement.dto.StatementDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired AccountRepository accountRepository;

  @Autowired AccountHolderService accountHolderService;

  @Autowired MessageSource messageSource;

  @Autowired
  private StatementService statementService;

  @Override
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public AccountDto getAccountById(Long accountId) throws ResourceNotFoundException {
    return convertToDto(getAccountByParam(accountId));
  }

  @Override
  public AccountDto getAccountByCpf(String cpf) throws ResourceNotFoundException {
    try {
      AccountHolder accountHolder = accountHolderService.getAccountHolderByCpf(cpf);
      return convertToDto(accountRepository.getByAccountHolderId(accountHolder.getId()));
    } catch (Exception ex) {
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "error.notfound.account.by.cpf",
              new Object[] {cpf},
              LocaleContextHolder.getLocale()));
    }
  }

  @Override
  public AccountDto createAccount(CreateAccountDto createAccountDto) throws BusinessException {
    try {
      AccountHolder accountHolder =
          accountHolderService.getAccountHolderByCpf(createAccountDto.getCpf());
      Account account = accountRepository.getByAccountHolderId(accountHolder.getId());
      if (account != null) {
        throw new BusinessException(
            messageSource.getMessage(
                "error.already.exists.account",
                new Object[] {createAccountDto.getCpf()},
                LocaleContextHolder.getLocale()));
      }
      Account newAccount = new Account();
      newAccount.setAccountHolder(accountHolder);
      newAccount.setBalance(
          createAccountDto.getBalance() == null ? BigDecimal.ZERO : createAccountDto.getBalance());
      newAccount.setBankBranch(createAccountDto.getBankBranch());
      newAccount.setBlocked(false);
      newAccount.setActive(true);
      return convertToDto(accountRepository.save(newAccount));
    } catch (ConstraintViolationException | ResourceNotFoundException cs) {
      throw new BusinessException(cs.getMessage());
    }
  }

  private AccountDto convertToDto(Account account) {
    AccountDto accountDto = new AccountDto();
    accountDto.setCpf(account.getAccountHolder().getCpf());
    accountDto.setBankBranch(account.getBankBranch());
    accountDto.setBalance(account.getBalance());
    accountDto.setCreatedAt(account.getCreatedAt());
    accountDto.setId(account.getId());
    accountDto.setBlocked(account.isBlocked());
    accountDto.setActive(account.isActive());
    return accountDto;
  }

  @Override
  public AccountDto updateAccountStatus(String cpf, boolean blocked)
      throws ResourceNotFoundException {
    AccountHolder accountHolder = accountHolderService.getAccountHolderByCpf(cpf);
    Account account = accountRepository.getByAccountHolderId(accountHolder.getId());
    account.setBlocked(blocked);
    return convertToDto(accountRepository.save(account));
  }

  @Override
  public void deleteAccountByCpf(String cpf) throws ResourceNotFoundException {
    AccountHolder accountHolder = accountHolderService.getAccountHolderByCpf(cpf);
    Account account = accountRepository.getByAccountHolderId(accountHolder.getId());
    accountRepository.delete(account);
  }

  @Override
  public AccountDto activeAccount(String cpf, boolean active) throws ResourceNotFoundException {
    AccountHolder accountHolder = accountHolderService.getAccountHolderByCpf(cpf);
    Account account = accountRepository.getByAccountHolderId(accountHolder.getId());
    account.setActive(active);
    return convertToDto(accountRepository.save(account));
  }

  @Override
  public AccountDto deposit(DepositAccountDto depositAccountDto)
          throws ResourceNotFoundException, BusinessException {
    AccountHolder accountHolder =
        accountHolderService.getAccountHolderByCpf(depositAccountDto.getCpf());
    Account account = accountRepository.getByAccountHolderId(accountHolder.getId());

    if (account == null || !account.isActive() || account.isBlocked()) {
      throw new BusinessException(messageSource.getMessage(
              "error.account.operation.deposit.not.available",
              new Object[] {account != null? account.getId(): 0L},
              LocaleContextHolder.getLocale()));
    }
    BigDecimal newBalance = account.getBalance().add(depositAccountDto.getAmount());
    account.setBalance(newBalance);
    saveInStatement(account, depositAccountDto.getAmount(), "Deposit Transaction", OperationTypeEnum.CREDIT);
    return convertToDto(accountRepository.save(account));
  }

  @Override
  public AccountDto withdraw(WithDrawAccountDto withDrawAccountDto)
          throws ResourceNotFoundException, BusinessException {
    AccountHolder accountHolder =
        accountHolderService.getAccountHolderByCpf(withDrawAccountDto.getCpf());
    Account account = accountRepository.getByAccountHolderId(accountHolder.getId());
    if (account == null) {
      throw new BusinessException(messageSource.getMessage(
              "error.account.operation.withdraw.not.available",
              new Object[] {withDrawAccountDto.getCpf()},
              LocaleContextHolder.getLocale()));
          }
    BigDecimal newBalance = account.getBalance().subtract(withDrawAccountDto.getAmount());
    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new BusinessException(messageSource.getMessage(
              "error.account.balance.insuficient",
              new Object[] {account != null? account.getBalance(): 0L},
              LocaleContextHolder.getLocale()));
    }
    validateWithDrawDailyLimitAmount(withDrawAccountDto.getCpf(), withDrawAccountDto.getAmount());
    account.setBalance(newBalance);
    saveInStatement(account, withDrawAccountDto.getAmount(), "WithDraw Transaction", OperationTypeEnum.DEBIT);
    return convertToDto(accountRepository.save(account));

  }

  private void saveInStatement(Account account, BigDecimal amount, String description, OperationTypeEnum operationTypeEnum) {
    StatementDto statementDto = new StatementDto();
    statementDto.setAccount(account);
    statementDto.setAmount(amount);
    statementDto.setDescription(description);
    statementDto.setOperationType(operationTypeEnum);
    statementService.registerStatementOperation(statementDto);
  }

  private void validateWithDrawDailyLimitAmount(String cpf, BigDecimal withDrawAmount) throws ResourceNotFoundException, BusinessException {
    List<StatementDto> statementDtos = statementService.getStatementByCpfAndPeriod(cpf, "DEBIT", LocalDate.now().toString().concat(" 00:00:00"), LocalDate.now().toString().concat(" 23:59:59"));
    BigDecimal sumWithDraw = statementDtos.stream().map(e -> e.getAmount()).reduce(BigDecimal.ZERO, (subtotal, element) -> subtotal.add(element));
    BigDecimal dailyLimitAmount = sumWithDraw.add(withDrawAmount);
    if (dailyLimitAmount.compareTo(BigDecimal.valueOf(2000).setScale(2)) > 0) {
      throw new BusinessException(messageSource.getMessage(
              "error.account.withdraw.daily.limit",
              new Object[] {cpf},
              LocaleContextHolder.getLocale()));

    }
  }

  private Account getAccountByParam(Long accountId) throws ResourceNotFoundException {
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        messageSource.getMessage(
                            "error.notfound.account.by.id",
                            new Object[] {accountId},
                            LocaleContextHolder.getLocale())));
    return account;
  }
}
