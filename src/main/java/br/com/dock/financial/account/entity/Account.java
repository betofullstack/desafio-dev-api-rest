package br.com.dock.financial.account.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "account_holder_id", referencedColumnName = "id")
  private AccountHolder accountHolder;

  @Column(name = "balance", nullable = false, precision = 12, scale = 4)
  private BigDecimal balance;

  @Column(name = "bank_branch", nullable = false)
  private String bankBranch;

  @Column(name = "blocked", nullable = false)
  private boolean blocked;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Account() {}

  @PrePersist
  public void prePersist() {
    setCreatedAt(LocalDateTime.now());
  }

  @PreUpdate
  public void preUpdate() {
    setUpdatedAt(LocalDateTime.now());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AccountHolder getAccountHolder() {
    return accountHolder;
  }

  public void setAccountHolder(AccountHolder accountHolder) {
    this.accountHolder = accountHolder;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getBankBranch() {
    return this.bankBranch;
  }

  public void setBankBranch(String bankBranch) {
    this.bankBranch = bankBranch;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return blocked == account.blocked
        && Objects.equals(id, account.id)
        && Objects.equals(accountHolder, account.accountHolder)
        && Objects.equals(balance, account.balance)
        && Objects.equals(bankBranch, account.bankBranch)
        && Objects.equals(createdAt, account.createdAt)
        && Objects.equals(updatedAt, account.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountHolder, balance, bankBranch, blocked, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return "Account{"
        + "id="
        + id
        + ", accountHolder="
        + accountHolder
        + ", balance="
        + balance
        + ", banckBranch='"
        + bankBranch
        + '\''
        + ", blocked="
        + blocked
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + '}';
  }
}
