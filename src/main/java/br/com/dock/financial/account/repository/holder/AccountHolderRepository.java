package br.com.dock.financial.account.repository.holder;

import br.com.dock.financial.account.entity.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    AccountHolder getByCpf(String cpf);
}