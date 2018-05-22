package NetTools;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkMapper implements NetworkTool {

    public String getTitle() {
        return "Network Mapper";
    }

    public String query(String address) {
        return getNetworkMap(address);
    }

    private String getNetworkMap(String address) {
        //TODO: Make Concurrent
        StringBuilder out = new StringBuilder();
        String mynetworkips = "";

        for (int i = address.length(); i > 0; --i)
            if (address.charAt(i - 1) == '.') {
                mynetworkips = address.substring(0, i);
                break;
            }
        out.append("My Device IP: ").append(address).append("\n").append("Search log: \n");

        for (int i = 1; i <= 254; ++i)
            try {
                InetAddress addr = InetAddress.getByName(mynetworkips + Integer.toString(i));
                if (addr.isReachable(200)) {
                    out.append(addr).append(" ").append(addr.getHostName()).append("\n");
                }
            } catch (IOException ioex) {
                return "IO Exception";
            }
        return out.toString();
    }
}



