package NetTools;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortScanner implements NetworkTool {

    private static Future<Integer> portIsOpen(final ExecutorService es, final String ip, final int port) {
        return es.submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 200);
                socket.close();
                return port;
            } catch (Exception ex) {
                return -1;
            }
        });


    }

    public String getTitle() {
        return "Port Scan";
    }

    public String query(String address) {
        return portScan(address);
    }

    private String portScan(String address) {
        StringBuilder out = new StringBuilder();
        final ExecutorService es = Executors.newFixedThreadPool(20);
        final List<Future<Integer>> futures = new ArrayList<>();
        for (int port = 1; port <= 1024; port++) {
            futures.add(portIsOpen(es, address, port));
        }
        es.shutdown();
        try {
            int openPorts = 0;
            for (final Future<Integer> f : futures) {

                if (f.get() != -1) {
                    openPorts++;
                }
            }
            out.append("Found ").append(openPorts).append(" ports open on ").append(address).append("\n");
            for (final Future<Integer> f : futures) {
                if (f.get() != -1) {
                    out.append(f.get()).append("\n");
                }
            }

        } catch (InterruptedException e) {
            return "Proccess Interupted";
        } catch (ExecutionException e) {
            return "Execution failed";
        }
        return out.toString();
    }
}