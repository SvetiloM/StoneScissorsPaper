package org.ssp.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ssp.Network;

@Slf4j
@Component
public class SspServer {

    private final Server server = new Ssp();

    @SneakyThrows
    public SspServer(SspServerListener listener) {
        Network.register(server);
        server.addListener(listener);
        server.bind(Network.port);
    }

    @PostConstruct
    void postConstruct() {
        server.start();
        log.info("server started");
    }

    @PreDestroy
    void preDestroy() {
        server.stop();
    }

    static class Ssp extends Server {
        static class SspConnection extends Connection {
        }
        protected Connection newConnection() {
            return new SspConnection();
        }
    }

}
