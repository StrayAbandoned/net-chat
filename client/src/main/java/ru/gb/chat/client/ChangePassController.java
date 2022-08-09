package ru.gb.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.gb.ChangePasswordRequest;

public class ChangePassController implements  Controller {
    private MainController controller;
    @FXML
    private PasswordField oldPassword, newPassword, passConfirm;
    @FXML
    private TextField login;


    public void changePassword(ActionEvent actionEvent) {

        String oldPassword = this.oldPassword.getText().trim();
        String newPassword = this.newPassword.getText().trim();
        String passConfirm = this.passConfirm.getText().trim();
        if (oldPassword.isBlank()||passConfirm.isBlank()||newPassword.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill in all the fields!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (!oldPassword.isBlank()&&newPassword.equals(passConfirm)){
            controller.sendChangePassword(new ChangePasswordRequest(Aspect.mainController.getNick(), newPassword));
        } else if (!newPassword.equals(passConfirm)){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Your password must be the same!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}
