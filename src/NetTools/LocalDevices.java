package NetTools;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LocalDevices implements NetworkTool {

    private static Future<String> hostReachable(final ExecutorService es, final String addr) {
        return es.submit(() -> {
                    try {
                        InetAddress address = InetAddress.getByName(addr);
                        if (address.isReachable(200)) {
                            return address + " " + address.getHostName();
                        } else
                            return "";
                    } catch (IOException e) {
                        return "";
                    }
                }
        );
    }

    public String getTitle() {
        return "Local Devices";
    }

    public String query(String address) {
        return getLocalDevices(address);
    }

    private String getLocalDevices(String address) {
        StringBuilder out = new StringBuilder();
        String mynetworkips = "";

        for (int i = address.length(); i > 0; --i)
            if (address.charAt(i - 1) == '.') {
                mynetworkips = address.substring(0, i);
                break;
            }

        out.append("My Device IP: ").append(address).append("\n").append("Search log: \n");

        final ExecutorService es = Executors.newFixedThreadPool(254 / Runtime.getRuntime().availableProcessors());
        final List<Future<String>> futures = new ArrayList<>();
        for (int i = 1; i <= 254; ++i) {
            futures.add(hostReachable(es, mynetworkips + Integer.toString(i)));
        }
        es.shutdown();

        for (Future<String> f : futures)
            try {
                if (!Objects.equals(f.get(), ""))
                    out.append(f.get()).append("\n");
            } catch (InterruptedException e) {
                return "Process Interrupted";
            } catch (ExecutionException e) {
                return "Execution failed";
            }
        return out.toString();
    }
}



