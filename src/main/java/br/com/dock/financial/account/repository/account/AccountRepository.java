package br.com.dock.financial.account.repository.account;

import br.com.dock.financial.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a from Account a where a.accountHolder.id = ?1")
    Account getByAccountHolderId(Long id);
}
