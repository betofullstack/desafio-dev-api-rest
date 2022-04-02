package br.com.dock.financial.account.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
    name = "ACCOUNT_HOLDER",
    indexes = {@Index(columnList = "id, created_at"), @Index(columnList = "cpf, created_at")})
public class AccountHolder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cpf", nullable = false, unique = true)
  private String cpf;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public AccountHolder() {
  }

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @PrePersist
  public void prePersist() {
    setCreatedAt(LocalDateTime.now());
  }

  @Override
  public String toString() {
    return "AccountHolder{"
        + "id="
        + id
        + ", cpf='"
        + cpf
        + '\''
        + ", name='"
        + name
        + '\''
        + ", createdAt="
        + createdAt
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AccountHolder that = (AccountHolder) o;
    return Objects.equals(id, that.id)
        && Objects.equals(cpf, that.cpf)
        && Objects.equals(name, that.name)
        && Objects.equals(createdAt, that.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cpf, name, createdAt);
  }
}
