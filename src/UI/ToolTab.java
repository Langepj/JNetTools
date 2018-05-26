package UI;

import NetTools.NetworkTool;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

class ToolTab extends Tab {
    private NetworkTool networkTool;

    ToolTab(NetworkTool networkTool) {
        this.networkTool = networkTool;
        TextField ipTextField = new TextField();
        Text displayField = new Text();


        ipTextField.setOnAction(e -> {
            displayField.setFill(Color.FIREBRICK);
            displayField.setText(networkTool.query(ipTextField.getText()));
        });


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(550, 700);
        scrollPane.setContent(displayField);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(new Label("IP/Domain:"), 0, 1);
        grid.add(ipTextField, 0, 2);
        grid.add(scrollPane, 0, 4);

        this.setContent(grid);
        this.setClosable(false);
        this.setText(networkTool.getTitle());
    }


}
