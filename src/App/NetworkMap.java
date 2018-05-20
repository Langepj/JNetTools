package App;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkMap extends AbstractTab {
    public static Tab createTab() {
        Tab whoIsTab = new Tab();
        GridPane grid = gridSetup();

        TextField ipTextField = new TextField();
        Text displayField = new Text();
        Button btn = new Button("Get Network Map");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btn);

        btn.setOnAction(e -> {
            displayField.setFill(Color.FIREBRICK);
            displayField.setText(NetworkMap.getNetworkMap(ipTextField.getText()));
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
        whoIsTab.setText("Network Map");

        return whoIsTab;
    }

    private static String getNetworkMap(String address) {
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
