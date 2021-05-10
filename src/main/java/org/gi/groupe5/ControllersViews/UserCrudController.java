package org.gi.groupe5.ControllersViews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gi.groupe5.Models.User;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.UserDao;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UserCrudController implements Initializable {

    @FXML private TextField tb_iduser;
    @FXML private TextField tb_login;
    @FXML private TextField tb_firstincr;
    @FXML private TextField tb_lastconn;
    @FXML private TextField tb_email;
    @FXML private ComboBox<String> cb_role;
    @FXML private PasswordField tb_password;
    @FXML private Button btn_new;
    @FXML private Button btn_save;
    @FXML private Button btn_delete;

    private String lastID = "-1";
    private User user;
    private DaoFactory daofactory;
    private UserDao usermanager;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        daofactory = DaoFactory.getInstance();
        usermanager = daofactory.getUserDao();
        user = new User();
        ObservableList<String> listrole = FXCollections.observableArrayList();
        listrole.add("user");
        listrole.add("statistique");
        listrole.add("admin");

        cb_role.setItems(listrole);
        cb_role.setPromptText("Veuillez choisir une valeur");
    }

    public void btn_new(ActionEvent actionEvent) {
        lastID = usermanager.getNewID().toString();
        tb_iduser.setText(lastID);
        tb_login.clear();
        tb_firstincr.setText(new Timestamp(System.currentTimeMillis()).toString());
        tb_lastconn.clear();
        tb_email.clear();
        cb_role.getSelectionModel().selectFirst();
        visible(true);
    }

    public void btn_save(ActionEvent actionEvent) {
        Integer iduser = Integer.valueOf(tb_iduser.getText());
        String login = tb_login.getText();
        String password = tb_password.getText();
        String email = tb_email.getText();
        String role=cb_role.getSelectionModel().getSelectedItem();

        user.setId_user(iduser);
        user.setLogin(login);
        user.setPwd(password);
        user.setEmail(email);
        user.setRoles(role);

        if (lastID == "-1") {
            usermanager.update(user);
        } else {
            usermanager.add(user);
            lastID = "-1";
            visible(false);
        }
    }

    public void btn_delete(ActionEvent actionEvent) {
        if(lastID=="-1"){
            user.setId_user(Integer.parseInt(tb_iduser.getText()));
            usermanager.delete(user.getId_user());
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText(null);
            a.setContentText("objet supprimé avec succès");
            a.showAndWait();
        }
    }

    public void putExtrat(User user) {
        tb_iduser.setText(user.getId_user().toString());
        tb_login.setText(user.getLogin());
//        tb_password.setText(user.getPwd());
        tb_firstincr.setText(String.valueOf(user.getFirst_inscr()));
        tb_lastconn.setText(String.valueOf(user.getLast_conn()));
        tb_email.setText(user.getEmail());
        cb_role.getSelectionModel().select(String.valueOf(user));
    }

    public void visible(Boolean visible) {
        btn_delete.setDisable(visible);
        tb_password.setDisable(!visible);
    }

}