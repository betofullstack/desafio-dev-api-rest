package br.com.dock.financial.account.entity;

public enum OperationTypeEnum {
  DEBIT("DEBIT"),
  CREDIT("CREDIT"),
  CANCEL("CANCEL"),
  REVERSAL("REVERSAL");

  private final String value;

  OperationTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
