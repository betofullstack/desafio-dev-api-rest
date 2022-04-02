package br.com.dock.financial.account.service.statement;

import br.com.dock.financial.account.exception.ResourceNotFoundException;
import br.com.dock.financial.account.service.statement.dto.StatementDto;

import java.util.List;

public interface StatementService {

    List<StatementDto> getStatementByCpfAndPeriod(String cpf, String operationType, String initialDate, String finalDate) throws ResourceNotFoundException;

    void registerStatementOperation(StatementDto statementDto);
}
