package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Controller {
    public Button addButton;
    public VBox listElements;
    public HBox hBoxElement;

    public void addElement(ActionEvent actionEvent) {
        HBox newHBox = new HBox();
       // newHBox.setPadding(new Insets(10,25,10,25));
        Text text = new Text("new text");
        text.setWrappingWidth(380.0);


        Button deleteButton = new Button();
        deleteButton.setText("USUN");
        deleteButton.setTextFill(Color.RED);
        deleteButton.setPrefWidth(55.0);
        deleteButton.setPrefHeight(15.0);
        deleteButton.setFont(new Font(11.0));
       //deleteButton.
        newHBox.getChildren().add(text);
        newHBox.getChildren().add(deleteButton);
        HBox.setMargin(text,new Insets(10,0,5,25));
        HBox.setMargin(deleteButton,new Insets(10,0,5,25));

        Separator separator = new Separator();
        separator.setOpacity(0.5);

        listElements.getChildren().add(newHBox);
        listElements.getChildren().add(separator);
        VBox.setMargin(separator, new Insets(2,20,2,20));
    }
}
