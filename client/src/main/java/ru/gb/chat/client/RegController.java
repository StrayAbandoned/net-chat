package ru.gb.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.gb.RegRequest;

public class RegController implements  Controller {
    private MainController controller;
    @FXML
    private PasswordField password;
    @FXML
    private TextField login;
    @FXML
    private TextField nick;
    @FXML
    private TextArea textArea;
    @FXML
    private PasswordField passConfirm;

    public void registration(ActionEvent actionEvent) {
        textArea.clear();
        String login = this.login.getText().trim();
        String password = this.password.getText().trim();
        String passConfirm = this.passConfirm.getText().trim();
        String nick = this.nick.getText().trim();
        if (login.isBlank()||password.isBlank()||passConfirm.isBlank()||nick.isBlank()) {
            textArea.appendText("Fill in all the fields!");
            return;
        }
        if (!login.isBlank()&&!nick.isBlank()&&password.equals(passConfirm)){
            controller.sendRegistrationRequest(new RegRequest(nick, login, password));
        } else if (!password.equals(passConfirm)){
            textArea.appendText("Your password must be the same!");
        }
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}
