package org.zerotick.ib.client;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

import java.util.HashMap;

import com.ib.client.*;

import org.zeromq.ZMQ;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;

public class Wrapper implements EWrapper {
    public Wrapper(){
        _mnemonics = new HashMap();
        _context = ZMQ.context(1);
        _socket = _context.socket(ZMQ.PUB);
        _socket.bind("ipc:///var/tmp/ib");
    }
    /* AnyWrapper Implementation */
    public void error( Exception e){};
    public void error( String str){};
    public void error(int id, int errorCode, String errorMsg){};
    public void connectionClosed(){};
    
    /* EWrapper Implementation */
    public void tickPrice( int tickerId, int field, double price, int canAutoExecute){
        System.out.println(String.format("%d %d %f", tickerId, field, price));
        MessagePack msgpack = new MessagePack(); 
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Packer packer = msgpack.createPacker(out);
        try {
            packer.writeArrayBegin(3);
            packer.write(lookupMnemonic(tickerId));
            packer.write(field);
            packer.write(price);
            packer.writeArrayEnd();
        } catch (IOException e) {}

        _socket.send(out.toByteArray(), 0);
    }
    public void tickSize( int tickerId, int field, int size){};
    public void tickOptionComputation( int tickerId, int field, double impliedVol,
    		double delta, double optPrice, double pvDividend,
    		double gamma, double vega, double theta, double undPrice){};
	public void tickGeneric(int tickerId, int tickType, double value){};
	public void tickString(int tickerId, int tickType, String value){};
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry){};
    public void orderStatus( int orderId, String status, int filled, int remaining,
            double avgFillPrice, int permId, int parentId, double lastFillPrice,
            int clientId, String whyHeld){};
    public void openOrder( int orderId, Contract contract, Order order, OrderState orderState){};
    public void openOrderEnd(){};
    public void updateAccountValue(String key, String value, String currency, String accountName){};
    public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue,
            double averageCost, double unrealizedPNL, double realizedPNL, String accountName){};
    public void updateAccountTime(String timeStamp){};
    public void accountDownloadEnd(String accountName){};
    public void nextValidId( int orderId){};
    public void contractDetails(int reqId, ContractDetails details){
        Contract c = details.m_summary;
        System.out.println(String.format("%s %s %s %s %s %s", c.m_symbol, c.m_currency, c.m_expiry, c.m_exchange, details.m_tradingHours, details.m_liquidHours));
    };
    public void bondContractDetails(int reqId, ContractDetails contractDetails){};
    public void contractDetailsEnd(int reqId){
        System.out.println("Contract details done."); 
    };
    public void execDetails( int reqId, Contract contract, Execution execution){};
    public void execDetailsEnd( int reqId){};
    public void updateMktDepth( int tickerId, int position, int operation, int side, double price, int size){};
    public void updateMktDepthL2( int tickerId, int position, String marketMaker, int operation,
    		int side, double price, int size){};
    public void updateNewsBulletin( int msgId, int msgType, String message, String origExchange){};
    public void managedAccounts( String accountsList){};
    public void receiveFA(int faDataType, String xml){};
    public void historicalData(int reqId, String date, double open, double high, double low,
                      double close, int volume, int count, double WAP, boolean hasGaps){};
    public void scannerParameters(String xml){};
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance,
    		String benchmark, String projection, String legsStr){};
    public void scannerDataEnd(int reqId){};
    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count){};
    public void currentTime(long time){};
    public void fundamentalData(int reqId, String data){};
    public void deltaNeutralValidation(int reqId, UnderComp underComp){};
    public void tickSnapshotEnd(int reqId){};
    public void marketDataType(int reqId, int marketDataType){};
    public void commissionReport(CommissionReport commissionReport){};

    public void registerMnemonic(int id, String mnemonic){
        _mnemonics.put(id, mnemonic);
    }

    public String lookupMnemonic(int id){
        return _mnemonics.get(id);
    }

    private ZMQ.Context _context;
    private ZMQ.Socket _socket;
    private HashMap<Integer, String> _mnemonics;
}
