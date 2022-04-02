package br.com.dock.financial.account.controller.holder;

import br.com.dock.financial.account.entity.AccountHolder;
import br.com.dock.financial.account.exception.BusinessException;
import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.service.holder.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountHolderController {

  @Autowired private AccountHolderService accountHolderService;

  @GetMapping("/account-holder")
  public List<AccountHolder> getAllAccountHolders() {
    return accountHolderService.getAllAccountHolders();
  }

  @GetMapping("/account-holder/{id}")
  public ResponseEntity<AccountHolder> getAccountHolderById(@PathVariable(value = "id") Long accountHolderId)
      throws ResourceNotFoundException {
    return ResponseEntity.ok().body(accountHolderService.getAccountHolderById(accountHolderId));
  }

  @PostMapping("/account-holder")
  public AccountHolder createAccountHolder(@Validated @RequestBody AccountHolder accountHolder) throws BusinessException {
    return accountHolderService.createAccountHolder(accountHolder);
  }

  @PutMapping("/account-holder/{id}")
  public ResponseEntity<AccountHolder> updateAccountHolder(
      @PathVariable(value = "id") Long accountHolderId, @Validated @RequestBody AccountHolder accountHolderDetails)
      throws ResourceNotFoundException {
    return ResponseEntity.ok(accountHolderService.updateAccountHolder(accountHolderId, accountHolderDetails));
  }

  @DeleteMapping("/account-holder/{id}")
  public ResponseEntity<Void> deleteAccountHolder(@PathVariable(value = "id") Long accountHolderId)
      throws ResourceNotFoundException {
    accountHolderService.deleteAccountHolder(accountHolderId);
    return ResponseEntity.noContent().build();
  }
}
