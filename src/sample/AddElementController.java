package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.controlsfx.control.Notifications;


import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;

public class AddElementController {
    public Button addButton2;
    public TextField amount;
    public TextField odchStand;
    public TextField elementTlumienie;
    public TextField elementName;
    public double probability;
    private ArrayList<Tor> torList;
    @FXML
    public VBox listElements;
    @FXML
    public Text wynik;

    void updateList(VBox listElements, ArrayList<Tor> torList, Text wynik, double probability ){
        this.listElements= listElements;
        this.torList = torList;
        this.wynik = wynik;
        this.probability = probability;

    }

    boolean checkElementName(String name){
    for(Tor t : torList) {
        if (t.getElementName().equals(name))
            return true;
    }
        return false;
    }

    public void addElement(ActionEvent actionEvent) throws Exception {



        Tor tor = new Tor(new Double(elementTlumienie.getText()),
                new Double(odchStand.getText()),
                new Integer(amount.getText()),
                elementName.getText());


if (checkElementName(tor.getElementName()))
{        Notifications notificationBuilder = Notifications.create()
        .title("Error")
        .text("Już istnieje element o takiej nazwie").position(Pos.CENTER);
    notificationBuilder.showError();
    throw new Exception("Name is already used");
}

        torList.add(tor);





        Stage stage = (Stage) addButton2.getScene().getWindow();
        stage.close();

        HBox newHBox = new HBox();
        // newHBox.setPadding(new Insets(10,25,10,25));
        Text text = new Text("Nazwa: "+tor.getElementName()+"\nTłumienie: "+ tor.getExpectedDamping()+
                ", Odch. standardowe: "+ tor.getVariation()+", Liczba el.: "+ tor.getAmount());
        text.setWrappingWidth(380.0);


        Button deleteButton = new Button();
       // deleteButton.setId(torList.size()+"");
        deleteButton.setId(tor.getElementName());
        System.out.println("torList size : "+ torList.size());

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

        AnchorPane anchorPane = new AnchorPane();
        VBox vBox = new VBox();
        vBox.getChildren().add(newHBox);
        vBox.getChildren().add(separator);
        VBox.setMargin(separator, new Insets(2,20,2,20));

        anchorPane.getChildren().add(vBox);
      //  anchorPane.getChildren().add(separator);


        listElements.getChildren().add(anchorPane);

//        listElements.getChildren().add(newHBox);
//        listElements.getChildren().add(separator);

     //   VBox.setMargin(separator, new Insets(2,20,2,20));

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0 ; i<torList.size();i++){
//                    System.out.println("NAME LIST : "+torList.get(i).getElementName()+
//                            "deletebutton id : "+ deleteButton.getId());
                    if (torList.get(i).getElementName().equals(deleteButton.getId())){
                        listElements.getChildren().remove(i+2);
                        torList.remove(i);
                        break;
                    }

                }
                calculate();
               // System.out.println("Integer.parseInt(deleteButton.getId()): "+ Integer.parseInt(deleteButton.getId()));
               // listElements.getChildren().remove(Integer.parseInt(deleteButton.getId())+1);
            }
        });
        calculate();
    }

    void calculate(){
        double expected = 0;
        double variation = 0;
        if (!torList.isEmpty()){
        for(Tor t : torList){
            System.out.println(t);
            expected += t.getExpectedDamping() * t.getAmount();
            variation += t.getVariation() * t.getVariation() * t.getAmount();
        }
        System.out.println("\n");
        variation = Math.cbrt(variation);
        NormalDistribution normalDistribution = new NormalDistribution(expected, variation);
        double lowerBound = 0.50;
        double upperBound = 0.50;
        if(probability != 0){
            lowerBound = probability / 2;
            upperBound = 1 - lowerBound;
        }
        wynik.setText(normalDistribution.inverseCumulativeProbability(lowerBound) + " dB "+" < "+expected + " dB "+
                " < "+normalDistribution.inverseCumulativeProbability(upperBound) + " dB "    );

        }else {
            wynik.setText("Brak elementów");
        }

    }

}
