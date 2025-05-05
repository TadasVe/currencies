package com.tadas.XML_reader.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FX_RATE")
public class FxRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String type;
  private LocalDate date;

  @Column(name = "FROM_CURRENCY")
  private String fromCurrency;

  @Column(name = "FROM_AMOUNT")
  private Double fromAmount;

  @Column(name = "TO_CURRENCY")
  private String toCurrency;

  @Column(name = "TO_AMOUNT")
  private Double toAmount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

  public Double getFromAmount() {
    return fromAmount;
  }

  public void setFromAmount(Double fromAmount) {
    this.fromAmount = fromAmount;
  }

  public Double getToAmount() {
    return toAmount;
  }

  public void setToAmount(Double toAmount) {
    this.toAmount = toAmount;
  }

  public String getToCurrency() {
    return toCurrency;
  }

  public void setToCurrency(String toCurrency) {
    this.toCurrency = toCurrency;
  }

  @Override
  public String toString() {
    return "FxRate{" +
      "id=" + id +
      ", type='" + type + '\'' +
      ", date=" + date +
      ", fromCurrency='" + fromCurrency + '\'' +
      ", fromAmount=" + fromAmount +
      ", toCurrency='" + toCurrency + '\'' +
      ", toAmount=" + toAmount +
      '}';
  }
}
