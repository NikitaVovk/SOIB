package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    public Button addButton;
    @FXML
    public VBox listElements;

    @FXML
    public Text wynik;

    public TextField probability;

    private ArrayList<Tor> torList;



    public void openAddElement(ActionEvent actionEvent) {



        System.out.println(  actionEvent.getEventType().getName());
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("addElement.fxml"));
//
//            Scene scene = new Scene(fxmlLoader.load(), 500, 300);
//            Stage stage = new Stage();
//            stage.setTitle("Dodaj element");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            FXMLLoader loader = new 		  FXMLLoader(getClass().getResource("addElement.fxml"));
            Parent root = loader.load();

            AddElementController controller2 = loader.getController();
            double prob;
            if ( probability.getText().isEmpty())
                prob=0.0;
            else
                prob = new Double(probability.getText());
            if (torList==null)
                torList = new ArrayList<>();

            controller2.updateList(listElements,torList,wynik,prob);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("DODAJ ELEMENT");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
