package org.gi.groupe5.ControllersViews;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.VehiculeDao;
import org.gi.groupe5.manager.VehiculeManager;
import org.gi.groupe5.Models.Vehicule;
import java.net.URL;
import java.util.ResourceBundle;


public class VehiculeCrudController implements Initializable {

    @FXML private TextField tb_idvehicule;
    @FXML private TextField tb_matricule;
    @FXML private Button btn_delete;

    private Vehicule vehicule;
    private String lastID="-1";
    private DaoFactory daofactory;
    private VehiculeDao vehiculemanager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        daofactory = DaoFactory.getInstance();
        vehiculemanager= daofactory.getVehiculeDao();;
        vehicule = new Vehicule();
        tb_idvehicule.setDisable(true);

    }



    public void putExtrat(Vehicule vehicule) {
        tb_idvehicule.setText(vehicule.getId_vehicule().toString());
        tb_matricule.setText(vehicule.getMatricule());
    }

    public void btn_new(ActionEvent actionEvent) {
        lastID= vehiculemanager.getNewID().toString();
        tb_idvehicule.setText(lastID);
        tb_matricule.clear();
        btn_delete.setDisable(true);
    }

    public void btn_save(ActionEvent actionEvent) {
        Integer idvehicule= Integer.valueOf(tb_idvehicule.getText());
        String matricule=tb_matricule.getText();
        vehicule.setId_vehicule(idvehicule);
        vehicule.setMatricule(matricule);

        if(lastID=="-1"){
            vehiculemanager.update(vehicule);
        }else{
            vehiculemanager.add(vehicule);
            lastID="-1";
            btn_delete.setDisable(false);
        }
    }

    public void btn_delete(ActionEvent actionEvent) {
        if(lastID=="-1"){
            Integer idvehicule= Integer.valueOf(tb_idvehicule.getText());
            vehicule.setId_vehicule(idvehicule);
            vehiculemanager.delete(vehicule.getId_vehicule());
        }

    }
}