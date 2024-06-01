package com.learn.exchange.assets;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Asset {

  BigDecimal available;

  BigDecimal frozen;

  public Asset() {
    this(BigDecimal.ZERO, BigDecimal.ZERO);
  }

  public Asset(BigDecimal available, BigDecimal frozen) {
    this.available = available;
    this.frozen = frozen;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public void setAvailable(BigDecimal available) {
    this.available = available;
  }

  public BigDecimal getFrozen() {
    return frozen;
  }

  public void setFrozen(BigDecimal frozen) {
    this.frozen = frozen;
  }

  @JsonIgnore
  public BigDecimal getTotal() {
    return available.add(frozen);
  }

  @Override
  public String toString() {
    return String.format("[available=%04.2f, frozen=%02.2f]", available, frozen);
  }
}
