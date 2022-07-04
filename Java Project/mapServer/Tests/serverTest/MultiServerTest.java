package serverTest;

import org.junit.jupiter.api.Test;
import server.MultiServer;

import java.net.BindException;

import static org.junit.jupiter.api.Assertions.*;

class MultiServerTest {

    /**
     * Testa il caso nel quale la porta abbia valore null
     */
    @Test
    public void exceptionTest() {
        assertThrows(NullPointerException.class, () -> {
            Integer port = null;
            MultiServer ms = new MultiServer(port);
        });
    }

    /**
     * Testa il caso nel quale viene specificata una porta gia in uso
     */
    public void bindExceptionTest() {
        assertThrows(BindException.class, () -> {
            Integer port = null;
            MultiServer ms = new MultiServer(4444);
        });
    }
    
    
}