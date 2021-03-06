package org.zerotick.ib.client;

import com.ib.client.EClientSocket;
import com.ib.client.Contract;

import java.lang.InterruptedException;

import org.zerotick.TickPublisher;
import java.lang.Thread;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        Wrapper wrapper = new Wrapper();
        EClientSocket client = new EClientSocket(wrapper);

        client.eConnect("localhost", 4001, 0);
        if (client.isConnected()) {
            client.reqMktData(3, ContractFactory.Stock("AAPL", "SMART", "USD"), "", false);
            wrapper.registerMnemonic(3, "AAPL SPY SMART USD");

            client.reqMktData(4, ContractFactory.Option("AAPL", "201208", "CALL", 610.0, "SMART", "USD"), "", false);
            wrapper.registerMnemonic(4, "AAPL 20120818 C 610.00 SMART USD");

            client.reqMktData(5, ContractFactory.Option("AAPL", "201208", "PUT", 610.0, "SMART", "USD"), "", false);
            wrapper.registerMnemonic(5, "AAPL 20120818 C 610.00 SMART USD");

            client.reader().join();
        }
    }
}
