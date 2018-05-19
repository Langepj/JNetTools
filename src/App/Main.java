package App;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = gridSetup();
        primaryStage.setTitle("JWhois");
        primaryStage.setScene(new Scene(grid, 600, 750));

        TextField ipTextField = new TextField();
        Text displayField = new Text();
        Button btn = buttonSetup(displayField, ipTextField);
        ipTextField.setOnAction(e -> btn.fire());


        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(550, 700);
        s1.setContent(displayField);

        grid.add(new Label("IP/Domain:"), 0, 1);
        grid.add(ipTextField, 0, 2);
        grid.add(btn, 0, 3);
        grid.add(s1, 0, 4);

        primaryStage.show();
    }

    private String getWhois(String domain) {
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

    private Button buttonSetup(Text actionTarget, TextField in) {
        Button btn = new Button("Whois");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btn);

        btn.setOnAction(e -> {
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setText(getWhois(in.getText()));
        });
        return btn;
    }

    private GridPane gridSetup() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }
}
