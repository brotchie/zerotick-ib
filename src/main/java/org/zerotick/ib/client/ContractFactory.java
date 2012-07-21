package org.zerotick.ib.client;

import com.ib.client.Contract;

public class ContractFactory {
    public static Contract Stock(String symbol, String exchange, String currency) {
        Contract contract = new Contract();
        contract.m_symbol = symbol;
        contract.m_exchange = exchange;
        contract.m_currency = currency;
        contract.m_secType = "STK";

        return contract;
    }

    public static Contract Future(String symbol, String expiry, String exchange, String currency) {
        Contract contract = new Contract();
        contract.m_symbol = symbol;
        contract.m_expiry = expiry;
        contract.m_exchange = exchange;
        contract.m_currency = currency;
        contract.m_secType = "FUT";

        return contract;
    }

    public static Contract Option(String symbol, String expiry, String right, double strike, String exchange, String currency) {
        Contract contract = new Contract();
        contract.m_symbol = symbol;
        contract.m_expiry = expiry;
        contract.m_exchange = exchange;
        contract.m_currency = currency;
        contract.m_secType = "OPT";
        contract.m_right = right;
        contract.m_strike = strike;

        return contract;
    }
}
