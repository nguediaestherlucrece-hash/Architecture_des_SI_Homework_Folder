package com.tp.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@WebService(serviceName = "CurrencyService")
public class CurrencySoapService {

   
    private static final Map<String, BigDecimal> RATES_TO_XAF = Map.of(
            "XAF", new BigDecimal("1"),
            "EUR", new BigDecimal("655.957"),
            "USD", new BigDecimal("600.000")
    );

    @WebMethod
    public String ping() {
        return "OK";
    }

    @WebMethod
    public List<String> getSupportedCurrencies() {
        return List.of("XAF", "EUR", "USD");
    }

    @WebMethod
    public BigDecimal convert(
            @WebParam(name = "amount") BigDecimal amount,
            @WebParam(name = "from") String from,
            @WebParam(name = "to") String to) {
        if (amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("from/to required");
        }

        from = from.trim().toUpperCase();
        to = to.trim().toUpperCase();

        BigDecimal fromRate = RATES_TO_XAF.get(from);
        BigDecimal toRate = RATES_TO_XAF.get(to);
        if (fromRate == null) {
            throw new IllegalArgumentException("Unsupported currency: " + from);
        }
        if (toRate == null) {
            throw new IllegalArgumentException("Unsupported currency: " + to);
        }

        // Convertir d'abord en XAF, puis vers la devise cible
        BigDecimal inXaf = amount.multiply(fromRate);
        return inXaf.divide(toRate, 2, RoundingMode.HALF_UP);
    }

    // Publication du service (petit serveur HTTP embarqué)
    public static void main(String[] args) {
        // 0.0.0.0 : accessible depuis un autre PC sur le réseau
        String url = "http://0.0.0.0:8080/currency";
        Endpoint.publish(url, new CurrencySoapService());
        System.out.println("SOAP service running at: " + url);
        System.out.println("WSDL available at: http://localhost:8080/currency?wsdl");
    }
}
