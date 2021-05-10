package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.ParkingDao;
import org.gi.groupe5.manager.ParkingManager;
import org.gi.groupe5.Models.Parking;

import java.net.URL;
import java.util.ResourceBundle;

public class ParkingCrudController implements Initializable {


    @FXML private TextField tb_id;
    @FXML private TextField tb_capacite;
    @FXML private TextField tb_place;
    @FXML private TextArea tb_adresse;
    @FXML private Button btn_add;
    @FXML private Button btn_update;
    @FXML private Button btn_delete;

    private DaoFactory daofactory;
    private ParkingDao parkingmanager;
    private Parking parking;

    private String lastID="-1";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory=DaoFactory.getInstance();
        parkingmanager=daofactory.getParkingDao();
        parking = new Parking();
    }

    public void btn_new(ActionEvent actionEvent) {
        lastID=parkingmanager.getNewID().toString();
        tb_id.setText(lastID);
        tb_adresse.clear();
        tb_capacite.setText("0");
        tb_place.setText("0");
        btn_delete.setDisable(true);
       // btn_delete.setVisible(false);
    }

    public void btn_save(ActionEvent actionEvent) {

        Integer idparc=Integer.parseInt( tb_id.getText());
        String adresse=tb_adresse.getText();

        parking.setId_parking(idparc);
        parking.setAdresse( adresse);

        if(lastID=="-1"){
            parkingmanager.update(parking);
        }else{
            parkingmanager.add(parking);
            lastID="-1";
            btn_delete.setDisable(false);
            //btn_delete.setVisible(true);
        }

    }

    public void btn_delete(ActionEvent actionEvent) {
        if(lastID=="-1"){
            Integer idparc=Integer.parseInt( tb_id.getText());
            parking.setId_parking(idparc);
            parkingmanager.delete(parking.getId_parking());
        }
    }

    public void putExtrat(Parking parking){
        tb_id.setText(parking.getId_parking().toString());
        tb_adresse.setText(parking.getAdresse());
        tb_capacite.setText(parking.getCapacite().toString());
    }


}
