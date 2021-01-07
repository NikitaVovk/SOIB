package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.math3.distribution.NormalDistribution;


import java.util.ArrayList;
import java.util.List;

public class AddElementController {
    public Button addButton2;
    public TextField amount;
    public TextField odchStand;
    public TextField elementTlumienie;
    public TextField elementName;
    public double probability;
    private List<Tor> torList;
    @FXML
    public VBox listElements;
    @FXML
    public Text wynik;

    void updateList(VBox listElements, List<Tor> torList, Text wynik, double probability){
        this.listElements= listElements;
        this.torList = torList;
        this.wynik = wynik;
        this.probability = probability;
    }

    public void addElement(ActionEvent actionEvent) {
        if (torList==null)
            torList = new ArrayList<>();


        Tor tor = new Tor(new Double(elementTlumienie.getText()),
                new Double(odchStand.getText()),
                new Integer(amount.getText()),
                elementName.getText());

        System.out.println(tor);

        torList.add(0,tor);

        Stage stage = (Stage) addButton2.getScene().getWindow();
        stage.close();

        HBox newHBox = new HBox();
        // newHBox.setPadding(new Insets(10,25,10,25));
        Text text = new Text("Nazwa: "+tor.getElementName()+"\nTłumienie: "+ tor.getExpectedDamping()+
                ", Odch. standardowe: "+ tor.getVariation()+", Liczba el.: "+ tor.getAmount());
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
        calculate();
    }

    void calculate(){
        double expected = 0;
        double variation = 0;
        for(Tor t : torList){
            expected += t.getExpectedDamping() * t.getAmount();
            variation += t.getVariation() * t.getVariation() * t.getAmount();
        }
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


//        text_result_avg.setText(""+expected + "dB");
//        text_result_min.setText(""+normalDistribution.inverseCumulativeProbability(lowerBound) +" dB");
//        text_result_max.setText(""+normalDistribution.inverseCumulativeProbability(upperBound)+ " dB");

    }

}
