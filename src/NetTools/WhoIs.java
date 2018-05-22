package NetTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class WhoIs implements NetworkTool {

    public String getTitle() {
        return "Whois";
    }

    public String query(String address) {
        return whoIs(address);
    }

    private String whoIs(String address) {
        StringBuilder whois = new StringBuilder();
        try {
            final Socket server = new Socket("whois.markmonitor.com", 43);
            final PrintStream out = new PrintStream(server.getOutputStream());
            final BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String line;
            // Send the whois query
            out.println(address);

            while ((line = in.readLine()) != null) {
                whois.append(line).append("\n");
            }
            server.close();
        } catch (java.net.UnknownHostException e) {
            // Unknown whois server
            return "Whois: unknown host: " + e;

        } catch (IOException e) {
            // Could not connect to whois server
            return "Unable to connect: " + e;
        }
        return whois.toString();
    }
}