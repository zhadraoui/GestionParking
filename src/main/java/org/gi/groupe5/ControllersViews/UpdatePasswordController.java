package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.UserDao;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePasswordController implements Initializable {

    @FXML
    private TextField tb_login;
    @FXML
    private PasswordField tb_old_pwd;
    @FXML
    private PasswordField tb_new_pwd;
    @FXML
    private PasswordField tb_confirm_pwd;
    @FXML
    Label lb_valide;
    @FXML
    Button btn_save;

    private DaoFactory daofactory;
    private UserDao usermanager;

    private String login;
    private String password_old;
    private String password_new;
    private String password_confirm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        usermanager = daofactory.getUserDao();
        btn_save.setDisable(true);
        getTextFromControl();
        tb_confirm_pwd.textProperty().addListener((observable, oldValue, newValue) -> {
            getTextFromControl();
            testConfirmationPassword();

        });
        tb_new_pwd.textProperty().addListener((observable, oldValue, newValue) -> {
            getTextFromControl();
            testConfirmationPassword();
        });
    }

    public void btn_close(ActionEvent actionEvent) {
        //fermer interface login
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void save_onClick(ActionEvent actionEvent) {

        getTextFromControl();

        if (password_old.isBlank()) {
            Alert.AlertType alertAlertType;
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Veuillez saisir votre ancien mot de posse");
            a.show();
            tb_old_pwd.requestFocus();
            return;
        }
        usermanager.update_password(login, password_old, password_new);

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.toFront();
    }

    public void getTextFromControl() {
        login = tb_login.getText();
        password_old = tb_old_pwd.getText();
        password_new = tb_new_pwd.getText();
        password_confirm = tb_confirm_pwd.getText();
    }

    public boolean testConfirmationPassword() {
        if (password_new.equals(password_confirm)) {
            lb_valide.setText("Valide");
            lb_valide.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            btn_save.setDisable(false);
        } else {
            lb_valide.setText("Non identique");
            lb_valide.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
            btn_save.setDisable(true);
        }

        return false;
    }


    public void putExtrat(String login) {
        tb_login.setText(login);
    }
}
