package org.ssp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    //todo вынести
    static public final int port = 54555;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
    }

}
