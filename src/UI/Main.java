package UI;

import NetTools.LocalDevices;
import NetTools.PortScanner;
import NetTools.WhoIs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        primaryStage.setTitle("JNetTools");
        primaryStage.setScene(new Scene(tabPane, 600, 750));

        tabPane.getTabs().add(new ToolTab(new WhoIs()));
        tabPane.getTabs().add(new ToolTab(new PortScanner()));
        tabPane.getTabs().add(new ToolTab(new LocalDevices()));

        primaryStage.show();
    }

}
