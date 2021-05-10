package org.gi.groupe5.ControllersViews;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.gi.groupe5.dao.ClientDao;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.VehiculeDao;
import org.gi.groupe5.manager.ClientManager;
import org.gi.groupe5.manager.VehiculeManager;
import org.gi.groupe5.Models.Client;
import org.gi.groupe5.Models.Vehicule;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCrudController implements Initializable {

    @FXML private TextField tb_idclient;
    @FXML private TextField tb_cin;
    @FXML private TextField tb_nom;
    @FXML private TextField tb_prenom;
    @FXML private TextField tb_gsm;
    @FXML private TextField tb_adresse;
    @FXML private TextField tb_email;
    @FXML private ComboBox<Vehicule> cb_idvehicule;
    @FXML private Button btn_delete;

    private String lastID = "-1";

    private Client client = null;
    private Vehicule vehicule=null;
    private DaoFactory daofactory ;
    private VehiculeDao vehiculemanager;
    private ClientDao clientmanager ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        daofactory=DaoFactory.getInstance();
        vehiculemanager=daofactory.getVehiculeDao();
        clientmanager=daofactory.getClientDao();

        vehicule = new Vehicule();

        tb_idclient.setDisable(true);



        ObservableList<Vehicule> vehiculelist = vehiculemanager.getObservableList();
        cb_idvehicule.setItems(vehiculelist);
        cb_idvehicule.setPromptText("Veuillez choisir une valeur");
    }

    public void btn_new(ActionEvent actionEvent) {
        lastID = clientmanager.getNewID().toString();
        tb_idclient.setText(lastID);
        tb_idclient.setText(lastID);
        tb_adresse.clear();
        tb_cin.clear();
        tb_nom.clear();
        tb_prenom.clear();
        tb_gsm.clear();
        tb_adresse.clear();
        tb_email.clear();
        cb_idvehicule.getSelectionModel().selectFirst();
        btn_delete.setDisable(true);

    }

    public void btn_save(ActionEvent actionEvent) {
        client= new Client();
        client.setId_client(Integer.parseInt( tb_idclient.getText()));
        client.setCin(tb_cin.getText());
        client.setNom(tb_nom.getText());
        client.setPrenom(tb_prenom.getText());
        client.setGsm(tb_gsm.getText());
        client.setAdresse(tb_adresse.getText());
        client.setEmail(tb_email.getText());
        client.setId_Vehicule(cb_idvehicule.getSelectionModel().getSelectedItem().getId_vehicule());

        if(lastID=="-1"){
            clientmanager.update(client);
        }else{
            clientmanager.add(client);
            lastID="-1";
            btn_delete.setDisable(false);
        }
    }

    public void btn_delete(ActionEvent actionEvent) {

        client= new Client();
        if(lastID=="-1"){
            client.setId_client(Integer.parseInt( tb_idclient.getText()));
            clientmanager.delete(client.getId_client());
        }
    }

    public void putExtrat(Client client) {
        tb_idclient.setText(client.getId_client().toString());
        tb_cin.setText(client.getCin());
        tb_nom.setText(client.getNom());
        tb_prenom.setText(client.getPrenom());
        tb_gsm.setText(client.getGsm());
        tb_adresse.setText(client.getAdresse());
        tb_email.setText(client.getEmail());
        String idvehicule = String.valueOf(client.getId_Vehicule());
        vehicule=vehiculemanager.getVehicule("id_Vehicule",idvehicule);
        cb_idvehicule.getSelectionModel().select(vehicule);
        System.out.println();


    }

    
}