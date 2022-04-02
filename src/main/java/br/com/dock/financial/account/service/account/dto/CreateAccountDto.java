package br.com.dock.financial.account.service.account.dto;

import br.com.dock.financial.account.entity.OperationTypeEnum;

import java.math.BigDecimal;

public class CreateAccountDto {
    private String cpf;
    private String bankBranch;
    private BigDecimal balance;

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

    @Override
    public String toString() {
        return "CreateAccountDto{" +
                "cpf='" + cpf + '\'' +
                ", bankBranch='" + bankBranch + '\'' +
                ", balance=" + balance +
                '}';
    }
}
