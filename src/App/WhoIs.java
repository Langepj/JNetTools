package App;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class WhoIs extends AbstractTab {

    public static Tab createTab() {
        Tab whoIsTab = new Tab();
        GridPane grid = gridSetup();

        TextField ipTextField = new TextField();
        Text displayField = new Text();
        Button btn = new Button("Whois");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btn);

        btn.setOnAction(e -> {
            displayField.setFill(Color.FIREBRICK);
            displayField.setText(WhoIs.getWhois(ipTextField.getText()));
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
        whoIsTab.setText("WhoIs");

        return whoIsTab;
    }

    public static String getWhois(String domain) {
        String whois = "";
        try {
            final Socket server = new Socket("whois.markmonitor.com", 43);
            final PrintStream out = new PrintStream(server.getOutputStream());
            final BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String line = "";
            // Send the whois query
            out.println(domain);

            while ((line = in.readLine()) != null) {
                whois += line + "\n";
            }
            server.close();
        } catch (java.net.UnknownHostException e) {
            // Unknown whois server
            return "Whois: unknown host: " + e;

        } catch (IOException e) {
            // Could not connect to whois server
            return "Unable to connect: " + e;
        }
        return whois;
    }

}
