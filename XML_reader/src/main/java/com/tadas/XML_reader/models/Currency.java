package com.tadas.XML_reader.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String code;
  private String nameLT;
  private String nameEN;
  private String number;
  private int minorUnits;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getNameLT() {
    return nameLT;
  }

  public void setNameLT(String nameLT) {
    this.nameLT = nameLT;
  }

  public String getNameEN() {
    return nameEN;
  }

  public void setNameEN(String nameEN) {
    this.nameEN = nameEN;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public int getMinorUnits() {
    return minorUnits;
  }

  public void setMinorUnits(int minorUnits) {
    this.minorUnits = minorUnits;
  }
}
