package org.zerotick;

import org.zeromq.ZMQ;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class TickPublisher {
    public TickPublisher(String endpoint) {
        _endpoint = endpoint;
    }

    public void start() {
        _context = ZMQ.context(1);
        _socket = _context.socket(ZMQ.PUB);
        _socket.bind(_endpoint);
    }

    public void publishTick(int tickerId, int field, double price) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        MessagePack msgpack = new MessagePack(); 
        if (_useMsgpack) {
            Packer packer = msgpack.createPacker(out);
            try {
                packer.writeArrayBegin(3);
                packer.write(tickerId);
                packer.write(field);
                packer.write(price);
                packer.writeArrayEnd();
            } catch (IOException e) {}
        } else {
            DataOutputStream data = new DataOutputStream(out);
            try {
                data.writeInt(tickerId);
                data.writeInt(field);
                data.writeDouble(price);
            } catch (IOException e) {};
        }
        _socket.send(out.toByteArray(), 0);
    }

    private String _endpoint;
    private ZMQ.Context _context;
    private ZMQ.Socket _socket;
    private boolean _useMsgpack = true;
}
