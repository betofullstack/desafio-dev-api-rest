package br.com.dock.financial.account.service.account.dto;

import java.math.BigDecimal;

public class WithDrawAccountDto {
    private String cpf;
    private BigDecimal amount;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal balance) {
        this.amount = balance;
    }

    @Override
    public String toString() {
        return "CreateAccountDto{" +
                "cpf='" + cpf + '\'' +
                ", amount=" + amount +
                '}';
    }
}
