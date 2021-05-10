package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.gi.groupe5.App;
import org.gi.groupe5.Models.User;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.UserDao;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML private TextField tb_login = null;
    @FXML private TextField tb_password = null;
    @FXML private Button btn_login=null;
    @FXML private Button btn_cancel=null;


    private Parent root = null;
    private static Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_login.setDefaultButton(true);
        btn_cancel.setCancelButton(true);
    }

    @FXML
    public void Login_onClick(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        if (tb_login.getText() == "" || tb_password.getText() == "") {
            a.setTitle("Erreur de saisie");
            if (tb_login.getText() == "") {
                tb_login.requestFocus();
                a.setContentText("Veuillez saisir votre nom de utilisateur");
            } else {
                tb_password.requestFocus();
                a.setContentText("Veuillez saisir votre mot de passe");
            }
            a.showAndWait();
            return;
        }

        DaoFactory daofactory = DaoFactory.getInstance();
        UserDao usermanager = daofactory.getUserDao();
        User user;

        String login = tb_login.getText();
        String password = tb_password.getText();

        Boolean acces = usermanager.auth(login, password);
        if (acces) {
            usermanager.updateDateLastConnection(login);
            user = usermanager.getUser("login", login);
            try {
                //fermer interface login
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                //afficher dashboard
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.MAINVIEW + ".fxml"));
                scene = new Scene(fxmlLoader.load(),1400,900);
                MainControllers cr = fxmlLoader.getController();
                cr.putExtrat(user);
                stage = new Stage();
                stage.initStyle(StageStyle.UTILITY);
                stage.setScene(scene);
                //stage.setFullScreen(true);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            a.setTitle("Échec d'authentication");
            a.setContentText("Nom d'utilisateur ou Mot de passe incorrect ");
            a.showAndWait();
        }

    }

//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        return fxmlLoader.load();
//    }

    /**
     * Fermer Application
     *
     * @param actionEvent
     */
    public void cancel_onClick(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog ");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment quitter l'application");
        //alert.setGraphic(new ImageView(this.getClass().getResource("../resources/img/logout-icon.png").toString()));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }


    }
}
