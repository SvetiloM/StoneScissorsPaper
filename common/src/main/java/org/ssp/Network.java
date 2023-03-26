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
        kryo.register(Authorisation.class);
        kryo.register(AuthorisationToken.class);
        kryo.register(Registration.class);
        kryo.register(ResultValues.class);
        kryo.register(Result.class);
    }

    public static class Args {
        public Command command;
        public String login;
        public String[] args;
    }

    public static class Authorisation {
        public String login;
        public String password;
    }

    public static class AuthorisationToken {
        public String token;
    }

    public static class Registration {
        public String login;
        public String password;
    }

    public static class Result {
        public ResultValues result;
    }

}
