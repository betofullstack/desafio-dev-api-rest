package br.com.dock.financial.account.service.holder;

import br.com.dock.financial.account.entity.AccountHolder;
import br.com.dock.financial.account.exception.BusinessException;
import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.repository.holder.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {

  @Autowired AccountHolderRepository accountHolderRepository;
  @Autowired MessageSource messageSource;

  @Override
  public List<AccountHolder> getAllAccountHolders() {
    return accountHolderRepository.findAll();
  }

  @Override
  public AccountHolder getAccountHolderById(Long accountHolderId) throws ResourceNotFoundException {
    AccountHolder accountHolder = getAccountHolderByParam(accountHolderId);
    return accountHolder;
  }

  @Override
  public AccountHolder createAccountHolder(AccountHolder accountHolder) throws BusinessException {
    try {
      AccountHolder holder = getAccountHolderByCpf(accountHolder.getCpf());
      if (holder != null) {
        throw new BusinessException(messageSource.getMessage(
                "error.already.exists.account.holder",
                new Object[] {accountHolder.getCpf()},
                LocaleContextHolder.getLocale()));
      }
      return accountHolderRepository.save(accountHolder);
    } catch (BusinessException ex) {
      throw new BusinessException(ex.getMessage());
    } catch (Exception ex) {
      throw new BusinessException(messageSource.getMessage(
              "error.on.save.account.holder",
              new Object[] {accountHolder.getCpf()},
              LocaleContextHolder.getLocale()));
    }
  }

  @Override
  public AccountHolder updateAccountHolder(Long accountHolderId, AccountHolder accountHolderDetails)
      throws ResourceNotFoundException {
    AccountHolder accountHolder = getAccountHolderByParam(accountHolderId);

    accountHolder.setCpf(accountHolderDetails.getCpf());
    accountHolder.setName(accountHolderDetails.getName());
    return accountHolderRepository.save(accountHolder);
  }

  @Override
  public void deleteAccountHolder(Long accountHolderId) throws ResourceNotFoundException {
    AccountHolder accountHolder = getAccountHolderByParam(accountHolderId);
    accountHolderRepository.delete(accountHolder);
  }

  @Override
  public AccountHolder getAccountHolderByCpf(String cpf) {
    return accountHolderRepository.getByCpf(cpf);
  }

  private AccountHolder getAccountHolderByParam(Long accountHolderId)
      throws ResourceNotFoundException {
    AccountHolder accountHolder =
        accountHolderRepository
            .findById(accountHolderId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        messageSource.getMessage(
                            "error.notfound.holder.by.id",
                            new Object[] {accountHolderId},
                            LocaleContextHolder.getLocale())));
    return accountHolder;
  }
}
