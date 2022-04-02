package br.com.dock.financial.account.service.statement.dto;

import br.com.dock.financial.account.entity.Account;
import br.com.dock.financial.account.entity.OperationTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatementDto {

  private Long id;

  private Account account;

  private BigDecimal amount;

  private OperationTypeEnum operationType;

  private String description;

  private LocalDateTime createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public OperationTypeEnum getOperationType() {
    return operationType;
  }

  public void setOperationType(OperationTypeEnum operationType) {
    this.operationType = operationType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "StatemantDto{" +
            "id=" + id +
            ", account=" + account +
            ", amount=" + amount +
            ", operationType=" + operationType +
            ", description=" + description +
            ", createdAt=" + createdAt +
            '}';
  }
}
