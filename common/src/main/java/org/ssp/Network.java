package org.ssp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    //todo вынести
    static public final int port = 54555;

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(String[].class);
        kryo.register(Command.class);
        kryo.register(Args.class);
    }

    public static class Args {
        public Command command;
        public String[] args;
    }

}
