package App;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortScan extends AbstractTab {

    public static Tab createTab() {
        Tab whoIsTab = new Tab();
        GridPane grid = gridSetup();

        TextField ipTextField = new TextField();
        Text displayField = new Text();
        Button btn = new Button("Port Scan");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btn);

        btn.setOnAction(e -> {
            displayField.setFill(Color.FIREBRICK);
            displayField.setText(PortScan.getPortScan(ipTextField.getText()));
        });

        ipTextField.setOnAction(e -> btn.fire());


        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(550, 700);
        s1.setContent(displayField);

        grid.add(new Label("IP/Domain:"), 0, 1);
        grid.add(ipTextField, 0, 2);
        grid.add(btn, 0, 3);
        grid.add(s1, 0, 4);

        whoIsTab.setContent(grid);
        whoIsTab.setClosable(false);
        whoIsTab.setText("Port Scan");

        return whoIsTab;
    }

    public static String getPortScan(String address) {
        String out = "";
        final ExecutorService es = Executors.newFixedThreadPool(20);
        final int timeout = 200;
        final List<Future<Integer>> futures = new ArrayList<>();
        for (int port = 1; port <= 1024; port++) {
            futures.add(portIsOpen(es, address, port, timeout));
        }
        es.shutdown();
        try {
            int openPorts = 0;
            for (final Future<Integer> f : futures) {

                if (f.get() != -1) {
                    openPorts++;
                }
            }
            out += "Found " + openPorts + " ports open on " + address + "\n";
            for (final Future<Integer> f : futures) {
                if (f.get() != -1) {
                    out += f.get() + "\n";
                }
            }

        } catch (InterruptedException e) {
            return "Proccess Interupted";
        } catch (ExecutionException e) {
            return "Execution failed";
        }
        return out;
    }

    public static Future<Integer> portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
        return es.submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), timeout);
                socket.close();
                return port;
            } catch (Exception ex) {
                return -1;
            }
        });


    }

}
