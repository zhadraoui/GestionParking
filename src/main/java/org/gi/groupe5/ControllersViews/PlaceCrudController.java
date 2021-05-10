package org.gi.groupe5.ControllersViews;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gi.groupe5.Models.Parking;
import org.gi.groupe5.Models.Place;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.ParkingDao;
import org.gi.groupe5.dao.PlaceDao;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaceCrudController implements Initializable {

    @FXML
    private TextField tb_id_palce;
    @FXML
    private TextField tb_description;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_delete;
    @FXML
    private ComboBox<Parking> cb_parking;
    @FXML
    private RadioButton rb_etat_oui;
    @FXML
    private RadioButton rb_etat_non;
    @FXML
    private ToggleGroup etat;

    private Place place = null;
    private Parking parking;

    DaoFactory daofactory;
    PlaceDao placemanager;
    ParkingDao parkingmanger;

    private String lastID = "-1";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        placemanager = daofactory.getPlaceDao();
        parkingmanger = daofactory.getParkingDao();
        parking = new Parking();

        ObservableList<Parking> parkinglist = parkingmanger.getObservableList();
        cb_parking.setItems(parkinglist);
        cb_parking.getSelectionModel().selectFirst();


    }
    
    @FXML
    void btn_new(ActionEvent event) {

        lastID = placemanager.getNewID().toString();

        tb_id_palce.setText(lastID);
        tb_description.clear();
        rb_etat_oui.setSelected(true);
        cb_parking.getSelectionModel().selectFirst();
        btn_delete.setDisable(true);

    }

    @FXML
    void btn_save(ActionEvent event) {

        place = new Place();
        Integer idplace = Integer.parseInt(tb_id_palce.getText());
        String description = tb_description.getText();
        String etat = getEtat();
        Integer idparking = cb_parking.getSelectionModel().getSelectedItem().getId_parking();

        place.setId_place(idplace);
        place.setDescr(description);
        place.setEtat(etat);
        place.setId_parking(idparking);

        if (lastID == "-1") {
            placemanager.update(place);
        } else {
            placemanager.add(place);
            lastID = "-1";
            btn_delete.setDisable(false);
        }
    }
    
    @FXML
    void btn_delete(ActionEvent event) {
        if (lastID == "-1") {
            Integer idplace = Integer.parseInt(tb_id_palce.getText());
            placemanager.delete(idplace);
        }
    }
    public void putExtrat(Place place) {
        tb_id_palce.setText(place.getId_place().toString());
        tb_description.setText(place.getDescr());
        Integer idparking = place.getId_parking();
        Parking parking=parkingmanger.getParking("id_Parking",idparking.toString());

        cb_parking.getSelectionModel().select(parking);
        if (place.getEtat().equals("OUI")) {
            rb_etat_oui.setSelected(true);
        } else if (place.getEtat().toUpperCase().equals("NON".toUpperCase())) {
            rb_etat_non.setSelected(true);
        }
    }

    
public String getEtat(){
        String Etat=null;
        if(rb_etat_oui.isSelected()){
            Etat= "OUI";
        }else if(rb_etat_non.isSelected()){
            Etat=  "NON";
        }
        return Etat;
}

public void updateCapaciteParking(int idparking){

}

    
}
