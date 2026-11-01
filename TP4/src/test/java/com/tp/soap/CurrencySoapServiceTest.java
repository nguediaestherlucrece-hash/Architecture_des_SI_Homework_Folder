package com.tp.soap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencySoapServiceTest {

    private CurrencySoapService service;

    @BeforeEach
    void setUp() {
        service = new CurrencySoapService();
    }

    @Test
    void pingReturnsOk() {
        assertEquals("OK", service.ping());
    }

    @Test
    void getSupportedCurrenciesReturnsXafEurUsd() {
        List<String> currencies = service.getSupportedCurrencies();
        assertEquals(List.of("XAF", "EUR", "USD"), currencies);
    }

    @Test
    void convertTenEurToXaf() {
        BigDecimal result = service.convert(new BigDecimal("10"), "EUR", "XAF");
        assertEquals(new BigDecimal("6559.57"), result);
    }

    @Test
    void convertTenUsdToXaf() {
        BigDecimal result = service.convert(new BigDecimal("10"), "USD", "XAF");
        assertEquals(new BigDecimal("6000.00"), result);
    }

    @Test
    void convertTenEurToUsd() {
        BigDecimal result = service.convert(new BigDecimal("10"), "EUR", "USD");
        assertEquals(new BigDecimal("10.93"), result);
    }

    @Test
    void convertZeroIsAllowed() {
        BigDecimal result = service.convert(BigDecimal.ZERO, "EUR", "XAF");
        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    void convertRejectsNegativeAmount() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.convert(new BigDecimal("-1"), "EUR", "XAF")
        );
        assertTrue(ex.getMessage().contains("amount must be >= 0"));
    }

    @Test
    void convertRejectsUnknownCurrencyGbp() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.convert(new BigDecimal("10"), "GBP", "XAF")
        );
        assertTrue(ex.getMessage().contains("Unsupported currency: GBP"));
    }

    @Test
    void convertRejectsNullAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.convert(null, "EUR", "XAF")
        );
    }
}
