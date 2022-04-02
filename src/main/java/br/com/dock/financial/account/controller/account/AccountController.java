package br.com.dock.financial.account.controller.account;

import br.com.dock.financial.account.entity.Account;
import br.com.dock.financial.account.entity.AccountStatement;
import br.com.dock.financial.account.exception.BusinessException;
import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.service.account.AccountService;
import br.com.dock.financial.account.service.account.dto.AccountDto;
import br.com.dock.financial.account.service.account.dto.CreateAccountDto;
import br.com.dock.financial.account.service.account.dto.DepositAccountDto;
import br.com.dock.financial.account.service.account.dto.WithDrawAccountDto;
import br.com.dock.financial.account.service.statement.StatementService;
import br.com.dock.financial.account.service.statement.dto.StatementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

  @Autowired private AccountService accountService;
  @Autowired private StatementService statementService;

  @GetMapping("/account")
  public List<Account> getAllAccounts() {
    return accountService.getAllAccounts();
  }

  @GetMapping("/account/holder/{cpf}")
  public ResponseEntity<AccountDto> getAccountByHolderCpf(@PathVariable(value = "cpf") String cpf)
      throws ResourceNotFoundException {
    return ResponseEntity.ok().body(accountService.getAccountByCpf(cpf));
  }

  @GetMapping("/account/holder/{cpf}/statement")
  public ResponseEntity<List<StatementDto>> getAccountStatemantByHolderCpfAndPeriod(
      @PathVariable(value = "cpf") String cpf,
      @RequestParam("initialDate") String initialDate,
      @RequestParam("finalDate") String finalDate,
      @RequestParam("operationType") String operationType)
      throws ResourceNotFoundException {
    return ResponseEntity.ok().body(statementService.getStatementByCpfAndPeriod(cpf, operationType, initialDate, finalDate));
  }

  @PostMapping("/account")
  public AccountDto createAccount(@Validated @RequestBody CreateAccountDto accountHolder) throws BusinessException {
    return accountService.createAccount(accountHolder);
  }

  @PutMapping("/account/holder/{cpf}/block")
  public ResponseEntity<AccountDto> blockAccountStatus(
      @PathVariable(value = "cpf") String cpf)
      throws ResourceNotFoundException {
    return ResponseEntity.ok(accountService.updateAccountStatus(cpf, true));
  }

  @PutMapping("/account/holder/{cpf}/unblock")
  public ResponseEntity<AccountDto> unblockAccountStatus(
          @PathVariable(value = "cpf") String cpf)
          throws ResourceNotFoundException {
    return ResponseEntity.ok(accountService.updateAccountStatus(cpf, false));
  }

  @PutMapping("/account/holder/{cpf}/active")
  public ResponseEntity<AccountDto> activeAccountStatus(
          @PathVariable(value = "cpf") String cpf)
          throws ResourceNotFoundException {
    return ResponseEntity.ok(accountService.activeAccount(cpf, true));
  }

  @PutMapping("/account/holder/{cpf}/inactive")
  public ResponseEntity<AccountDto> inactiveAccountStatus(
          @PathVariable(value = "cpf") String cpf)
          throws ResourceNotFoundException {
    return ResponseEntity.ok(accountService.activeAccount(cpf, false));
  }

  @PostMapping("/account/withdraw")
  public ResponseEntity<AccountDto> withdraw(
          @RequestBody WithDrawAccountDto withDrawAccountDto)
          throws ResourceNotFoundException, BusinessException {
    return ResponseEntity.ok(accountService.withdraw(withDrawAccountDto));
  }

  @PostMapping("/account/deposit")
  public ResponseEntity<AccountDto> deposit(
          @RequestBody DepositAccountDto depositAccountDto)
          throws ResourceNotFoundException, BusinessException {
    return ResponseEntity.ok(accountService.deposit(depositAccountDto));
  }

  @DeleteMapping("/account/holder/{cpf}")
  public ResponseEntity<Void> deleteAccount(@PathVariable(value = "cpf") String cpf)
      throws ResourceNotFoundException {
    accountService.deleteAccountByCpf(cpf);
    return ResponseEntity.noContent().build();
  }
}
