package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.gi.groupe5.App;
import org.gi.groupe5.Models.User;
import org.gi.groupe5.Windows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainControllers implements Initializable {
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_parking;
    @FXML
    private Button btn_place;
    @FXML
    private Button btn_vehicule;
    @FXML
    private Button btn_occupation;
    @FXML
    private Button btn_tarif;
    @FXML
    private Button btn_detection;
    @FXML
    private Button btn_user;
    @FXML
    private Button Btn_password;
    @FXML
    private Button btn_Client;
    @FXML
    private Button btn_logout;
    @FXML
    private BorderPane borderpane;
    @FXML
    private Label name_view;
    private Parent parent;
    private Stage stage = null;
    private User user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = new User();
        stage = new Stage();
    }

    /**
     * Methode qui parmet d'appelée une View est
     * la mettre en centre de la Borderpane du dashboard
     * MainView
     * @param viewFXML
     */
    public void openView(String viewFXML){
        parent = null;
        borderpane.setCenter(null);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewFXML + ".fxml"));
            parent = fxmlLoader.load();
            borderpane.setCenter(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openParking(ActionEvent actionEvent) {
        openView(Windows.PARKINGVIEW);
        name_view.setText("Parking");

    }

    public void openOccupation(ActionEvent actionEvent) {
        openView(Windows.OCCUPATIONVIEW);
        name_view.setText("Resrvation");
    }

    public void openVehicule(ActionEvent actionEvent) {
        openView(Windows.VEHICULEVIEW);
        name_view.setText("Vehicule");
    }

    public void openUser(ActionEvent actionEvent) {
        openView(Windows.USERVIEW);
        name_view.setText("User");
    }

    public void openClient(ActionEvent actionEvent) {
        openView(Windows.CLIENTVIEW);
        name_view.setText("Client");
    }

    public void openPlace(ActionEvent actionEvent) {
        openView(Windows.PLACEVIEW);
        name_view.setText("Place");
    }

    public void openTarif(ActionEvent actionEvent) {
        openView(Windows.TARIFVIEW);
        name_view.setText("Tarif");
    }

    public void Open_Logout(ActionEvent event) throws IOException {
        //fermer cette interface (dashboard)
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        //afficher Interface du Login
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.LOGINVIEW + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }

    public void openDetecion(ActionEvent actionEvent) {
        openView(Windows.DETECTIONVIEW);
        name_view.setText("Detecion Matricule");
    }

    public void openUpdatePassword(ActionEvent actionEvent) throws IOException {
        //afficher dashboard
        if (stage.isShowing()) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.UPDATEPASSWORDVIEW + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        UpdatePasswordController cr = fxmlLoader.getController();
        cr.putExtrat(btn_login.getText());
        stage.setScene(scene);
        stage.setTitle("Change password");
        stage.setAlwaysOnTop(true);
        stage.show();

    }


    public void putExtrat(User user) {
        this.user = user;
        btn_login.setText(this.user.getLogin());
        String role = user.getRoles();

        if (role.toUpperCase().equals("admin".toUpperCase())) {
            btn_Client.setDisable(false);
            btn_user.setDisable(false);
        } else {
            btn_Client.setDisable(true);
            btn_user.setDisable(true);
        }
    }
}
