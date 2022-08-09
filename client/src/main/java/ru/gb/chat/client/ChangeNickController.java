package ru.gb.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.gb.ChangeNickRequest;
import ru.gb.ChangePasswordRequest;

public class ChangeNickController {
    private MainController controller;

    @FXML
    private TextField newNick;


    public void setController(MainController controller) {
        this.controller = controller;
    }
    public void changeNick(ActionEvent actionEvent) {
        String nick = this.newNick.getText().trim();
        if(nick.isBlank()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill in all the fields!", ButtonType.OK);
            alert.showAndWait();
            return;
        } else {
            controller.sendChangeNick(new ChangeNickRequest(Aspect.mainController.getNick(), nick));
        }
    }
}
