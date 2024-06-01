package com.learn.exchange.assets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.learn.exchange.enums.AssetEnum;

public class AssetServiceTest {
  static final Long DEBT = 1L;
  static final Long USER_A = 2000L;
  static final Long USER_B = 3000L;
  static final Long USER_C = 4000L;

  AssetService service;

  @BeforeEach
  public void setUp() {
    service = new AssetService();
    init();
  }

  @AfterEach
  public void tearDown() {
    verify();
  }

  /**
   * A: USD=12300, BTC=12
   * 
   * B: USD=45600
   * 
   * C: BTC=34
   */
  void init() {
    service.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_A, AssetEnum.USD, BigDecimal.valueOf(12300), false);
    service.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_A, AssetEnum.BTC, BigDecimal.valueOf(12),
        false);

    service.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_B, AssetEnum.USD, BigDecimal.valueOf(45600),
        false);

    service.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_C, AssetEnum.BTC, BigDecimal.valueOf(34),
        false);
    assertBDEquals(-57900, service.getAsset(DEBT, AssetEnum.USD).available);
    assertBDEquals(-46, service.getAsset(DEBT, AssetEnum.BTC).available);
  }

  void verify() {
    BigDecimal totalUSD = BigDecimal.ZERO;
    BigDecimal totalBTC = BigDecimal.ZERO;

    for (Long userId : service.userAssets.keySet()) {
      var assetUSD = service.getAsset(userId, AssetEnum.USD);
      if (assetUSD != null) {
        totalUSD = totalUSD.add(assetUSD.available).add(assetUSD.frozen);
      }
      var assetBTC = service.getAsset(userId, AssetEnum.BTC);
      if (assetBTC != null) {
        totalBTC = totalBTC.add(assetBTC.available).add(assetBTC.frozen);
      }
    }
    assertBDEquals(0, totalBTC);
    assertBDEquals(0, totalUSD);
  }

  void assertBDEquals(long value, BigDecimal bd) {
    assertBDEquals(String.valueOf(value), bd);
  }

  void assertBDEquals(String value, BigDecimal bd) {
    assertTrue(new BigDecimal(value).compareTo(bd) == 0,
        String.format("Expected %s but actual %s.", value, bd.toPlainString()));
  }
}
