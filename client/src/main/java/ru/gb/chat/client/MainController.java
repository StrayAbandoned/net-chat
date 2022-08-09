package ru.gb.chat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import ru.gb.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainController implements Initializable, Controller {


    private Stage stage, regStage, passStage, nickStage;
    private Network network;
    private RegController regController;
    private ChangePassController passController;
    private ChangeNickController nickController;
    private boolean isAuthenticated;
    private String nick;
    private CopyOnWriteArrayList<String> clients;


    @FXML
    private TextField textField, login;
    @FXML
    private HBox loginbox, msgpanel;
    @FXML
    private MenuItem changePassword, changeNick;
    @FXML
    private ListView<String> clientList;
    @FXML
    private PasswordField password;
    @FXML
    private TextArea textArea;

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            Aspect.mainController = this;
            network = new Network();
            stage = (Stage) textField.getScene().getWindow();
            stage.setOnCloseRequest(windowEvent -> {

                try {
                    isAuthenticated = false;
                    setAuthenticated(isAuthenticated);
                    if (nick != null) {
                        network.sendFiles(new RemoveClientRequest(nick));
                    }
                    network.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.exit();

            });
        });
        setAuthenticated(false);

    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
        loginbox.setVisible(!isAuthenticated);
        loginbox.setManaged(!isAuthenticated);
        changePassword.setDisable(!isAuthenticated);
        changeNick.setDisable(!isAuthenticated);
        msgpanel.setVisible(isAuthenticated);
        msgpanel.setManaged(isAuthenticated);
        clientList.setVisible(isAuthenticated);
        clientList.setManaged(isAuthenticated);
        if (isAuthenticated) {
            Platform.runLater(() -> {
                stage.setTitle(nick);
            });

        }

    }

    public void login(ActionEvent actionEvent) {
        String login = this.login.getText().trim();
        String password = this.password.getText().trim();
        network.sendFiles(new AuthRequest(login, password));
    }

    public void registration(ActionEvent actionEvent) {
        if (regStage == null) {
            createRegWindow();
        }
        regStage.show();
    }

    private void createRegWindow() {
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader(Launcher.class.getResource("registration.fxml"));
            Parent root = fxmlLoader1.load();
            regStage = new Stage();
            regStage.setTitle("Registration");
            regStage.setScene(new Scene(root, 300, 300));
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.initStyle(StageStyle.UTILITY);
            regController = fxmlLoader1.getController();
            regController.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendText(ActionEvent actionEvent) {
        if (clientList.getSelectionModel().isEmpty()) {
            network.sendFiles(new SendToEveryoneRequest(nick, textField.getText()));
        } else if (clientList.getSelectionModel().getSelectedItem().equals(nick)) {
            textField.clear();
            return;
        } else {
            network.sendFiles(new PrivateMessageRequest(textField.getText(), nick, clientList.getSelectionModel().getSelectedItem()));
        }

        textField.clear();

    }


    public void backToAuthentication(ActionEvent actionEvent) {
        network.sendFiles(new RemoveClientRequest(nick));
        setAuthenticated(false);
    }

    public void setNick(String nick) {
        this.nick = nick;


    }

    public void setClients(CopyOnWriteArrayList<String> clients) {
        this.clients = clients;
    }

    public void showClientList() {
        clientList.getItems().clear();
        for (String s : clients) {
            if(!s.equals(nick)){
                clientList.getItems().add(s);
            }

        }

    }

    public void sendRegistrationRequest(RegRequest regRequest) {
        network.sendFiles(regRequest);
    }

    public void clientClicked(MouseEvent mouseEvent) {
        textField.requestFocus();
    }

    public void sendChangePassword(ChangePasswordRequest changePasswordRequest) {
        network.sendFiles(changePasswordRequest);
    }

    public String getNick() {
        return nick;
    }

    public void changePassword(ActionEvent actionEvent) {
        if (passStage == null) {
            createPassWindow();
        }
        passStage.show();
    }
//
    private void createPassWindow() {
        try {
            FXMLLoader fxmlLoader2 = new FXMLLoader(Launcher.class.getResource("changepassword.fxml"));
            Parent root = fxmlLoader2.load();
            passStage = new Stage();
            passStage.setTitle("Change password");
            passStage.setScene(new Scene(root, 300, 300));
            passStage.initModality(Modality.APPLICATION_MODAL);
            passStage.initStyle(StageStyle.UTILITY);
            passController = fxmlLoader2.getController();
            passController.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeNick(ActionEvent actionEvent) {
        if (nickStage == null) {
            createNickWindow();
        }
        nickStage.show();
    }

    private void createNickWindow() {
        try {
            FXMLLoader fxmlLoader3 = new FXMLLoader(Launcher.class.getResource("changenick.fxml"));
            Parent root = fxmlLoader3.load();
            nickStage = new Stage();
            nickStage.setTitle("Change nickname");
            nickStage.setScene(new Scene(root, 300, 300));
            nickStage.initModality(Modality.APPLICATION_MODAL);
            nickStage.initStyle(StageStyle.UTILITY);
            nickController = fxmlLoader3.getController();
            nickController.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendChangeNick(ChangeNickRequest nickRequest){
        network.sendFiles(new RemoveClientRequest(nick));
        network.sendFiles(nickRequest);


    }
}