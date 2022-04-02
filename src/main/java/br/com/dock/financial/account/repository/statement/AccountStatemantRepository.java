package br.com.dock.financial.account.repository.statement;

import br.com.dock.financial.account.entity.AccountStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountStatemantRepository extends JpaRepository<AccountStatement, Long> {

    List<AccountStatement> findByAccountIdAndOperationTypeAndCreatedAtBetween(Long id, String operation, LocalDateTime starDate, LocalDateTime endDate);
}