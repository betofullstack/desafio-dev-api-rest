package br.com.dock.financial.account.service.holder;

import br.com.dock.financial.account.entity.AccountHolder;
import br.com.dock.financial.account.exception.BusinessException;
import br.com.dock.financial.account.exception.ResourceNotFoundException;

import java.util.List;

public interface AccountHolderService {

    List<AccountHolder> getAllAccountHolders();

    AccountHolder getAccountHolderById(Long accountHolderId) throws ResourceNotFoundException;

    AccountHolder createAccountHolder(AccountHolder accountHolder) throws BusinessException;

    AccountHolder updateAccountHolder(Long accountHolderId, AccountHolder accountHolderDetails) throws ResourceNotFoundException;

    void deleteAccountHolder(Long accountHolderId) throws ResourceNotFoundException;

    AccountHolder getAccountHolderByCpf(String cpf) throws ResourceNotFoundException;
}
