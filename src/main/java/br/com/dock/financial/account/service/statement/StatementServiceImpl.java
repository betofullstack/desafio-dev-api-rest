package br.com.dock.financial.account.service.statement;

import br.com.dock.financial.account.entity.AccountStatement;
import br.com.dock.financial.account.entity.OperationTypeEnum;
import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.repository.statement.AccountStatemantRepository;
import br.com.dock.financial.account.service.account.AccountService;
import br.com.dock.financial.account.service.account.dto.AccountDto;
import br.com.dock.financial.account.service.statement.dto.StatementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementServiceImpl implements StatementService {

  @Autowired AccountStatemantRepository statementRepository;
  @Autowired @Lazy AccountService accountService;

  @Autowired MessageSource messageSource;

  private StatementDto convertToDto(AccountStatement accountStatement) {
    StatementDto statement = new StatementDto();
    statement.setAmount(accountStatement.getAmount());
    statement.setAccount(accountStatement.getAccount());
    statement.setDescription(accountStatement.getDescription());
    statement.setCreatedAt(accountStatement.getCreatedAt());
    statement.setId(accountStatement.getId());
    statement.setOperationType(OperationTypeEnum.valueOf(accountStatement.getOperationType()));
    return statement;
  }

  @Override
  public List<StatementDto> getStatementByCpfAndPeriod(
      String cpf, String operationType, String initialDate, String finalDate) throws ResourceNotFoundException {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime starDate = LocalDateTime.parse(initialDate, formatter);
    LocalDateTime endDate = LocalDateTime.parse(finalDate, formatter);
    AccountDto accountDto = accountService.getAccountByCpf(cpf);
    List<AccountStatement> accountStatements =
        statementRepository.findByAccountIdAndOperationTypeAndCreatedAtBetween(
            accountDto.getId(), operationType, starDate, endDate);
    List<StatementDto> statementDtos =
        accountStatements.stream().map(this::convertToDto).collect(Collectors.toList());
    return statementDtos;
  }

  @Override
  public void registerStatementOperation(StatementDto statementDto) {
    AccountStatement statement = new AccountStatement();
    statement.setOperationType(statementDto.getOperationType().getValue());
    statement.setAccount(statementDto.getAccount());
    statement.setAmount(statementDto.getAmount());
    statement.setDescription(statementDto.getDescription());
    statementRepository.save(statement);
  }
}
