package br.com.dock.financial.account.service.account.dto;

import br.com.dock.financial.account.entity.OperationTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDto {
    private Long id;
    private String cpf;
    private String bankBranch;
    private BigDecimal balance;
    private boolean active;
    private boolean blocked;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", bankBranch='" + bankBranch + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                ", blocked=" + blocked +
                ", createdAt=" + createdAt +
                '}';
    }
}
