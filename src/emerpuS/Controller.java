package emerpuS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;


/**
 * Created by Kenny on 4/17/2017.
 */
public class Controller {
    SupremeBot bot;
    ObservableList<String> listOfLinks = FXCollections
            .observableArrayList("jackets", "shirts", "tops_sweaters", "sweatshirts", "pants",
                    "t-shirts", "hats", "accessories", "skate", "shoes");

    ObservableList<String> listOfSizes = FXCollections
            .observableArrayList("Small", "Medium", "Large", "XLarge",
                    "7.5", "8", "8.5", "9", "9.5", "10", "10.5", "11");

    @FXML
    public ComboBox link;
    @FXML
    public TextField itemName;
    @FXML
    public TextField itemColor;
    @FXML
    public ComboBox size;
    @FXML
    public TextField name;
    @FXML
    public TextField email;
    @FXML
    public TextField phone;
    @FXML
    public TextField address;
    @FXML
    public TextField address2;
    @FXML
    public TextField zipCode;
    @FXML
    public TextField ccNumber;
    @FXML
    public TextField expMonth;
    @FXML
    public TextField expYear;
    @FXML
    public TextField cvv;

    @FXML
    public void initialize(){
        link.setItems(listOfLinks);
        size.setItems(listOfSizes);
    }

    public void startButtonClicked() throws InterruptedException, IOException {
        bot = new SupremeBot(link.getValue().toString(), name.getText(), email.getText(), phone.getText(),
                address.getText(), address2.getText(), zipCode.getText(), ccNumber.getText(), expMonth.getText(),
                expYear.getText(), cvv.getText(), itemName.getText(), itemColor.getText(), size.getValue().toString());
    }
}
