package App;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import java.net.InetAddress;

public class AbstractTab implements TabInterface {


    public static Tab createTab() {
        return new Tab();
    }

    protected static GridPane gridSetup() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

    protected static Boolean isReachable(String ipAddress) {
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            reachable = address.isReachable(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reachable;
    }

}
