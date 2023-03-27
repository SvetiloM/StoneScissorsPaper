package org.ssp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    static public final int port = 54555;

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(char[].class);
        kryo.register(Command.class);
        kryo.register(Step.class);
        kryo.register(Authorisation.class);
        kryo.register(AuthorisationToken.class);
        kryo.register(Registration.class);
        kryo.register(ResultValue.class);
        kryo.register(Result.class);
        kryo.register(Time.class);
        kryo.register(int.class);
    }

    public static class Step {
        public Command command;
        public String token;
    }

    public static class Authorisation {
        public String login;
        public char[] password;
    }

    public static class AuthorisationToken {
        public String token;
    }

    public static class Registration {
        public String login;
        public char[] password;
    }

    public static class Result {
        public ResultValue result;
    }

    public static class Time {
        public int sec;
    }

}
