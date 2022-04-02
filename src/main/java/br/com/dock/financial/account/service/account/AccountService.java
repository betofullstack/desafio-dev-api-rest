package br.com.dock.financial.account.service.account;

import br.com.dock.financial.account.entity.Account;
import br.com.dock.financial.account.exception.BusinessException;
import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.service.account.dto.AccountDto;
import br.com.dock.financial.account.service.account.dto.CreateAccountDto;
import br.com.dock.financial.account.service.account.dto.DepositAccountDto;
import br.com.dock.financial.account.service.account.dto.WithDrawAccountDto;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts();

    AccountDto getAccountById(Long accountId) throws ResourceNotFoundException;

    AccountDto getAccountByCpf(String cpf) throws ResourceNotFoundException;

    AccountDto createAccount(CreateAccountDto accountDto) throws BusinessException;

    AccountDto updateAccountStatus(String cpf, boolean blocked) throws ResourceNotFoundException;

    void deleteAccountByCpf(String cpf) throws ResourceNotFoundException;

    AccountDto activeAccount(String cpf, boolean active) throws ResourceNotFoundException;

    AccountDto deposit(DepositAccountDto depositAccountDto) throws ResourceNotFoundException, BusinessException;

    AccountDto withdraw(WithDrawAccountDto withDrawAccountDto) throws ResourceNotFoundException, BusinessException;

}
