package App;

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
        primaryStage.setTitle("JNetTool");
        primaryStage.setScene(new Scene(tabPane, 600, 750));

        tabPane.getTabs().add(WhoIs.createTab());
        tabPane.getTabs().add(PortScan.createTab());

        primaryStage.show();
    }

}
