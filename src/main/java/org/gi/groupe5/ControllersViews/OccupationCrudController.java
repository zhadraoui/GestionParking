package org.gi.groupe5.ControllersViews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.gi.groupe5.Models.Occupation;
import org.gi.groupe5.Models.Parking;
import org.gi.groupe5.Models.Place;
import org.gi.groupe5.Models.Vehicule;
import org.gi.groupe5.dao.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class OccupationCrudController implements Initializable {


    @FXML
    private Button btn_delete;
    @FXML
    private TextField tb_id_occupation;
    @FXML
    private DatePicker tb_date_debut;
    @FXML
    private TextField tb_heure_debut;
    @FXML
    private TextField tb_min_debut;
    @FXML
    private ComboBox<Parking> cb_parking;
    @FXML
    private ComboBox<Place> cb_place;
    @FXML
    private ComboBox<Vehicule> cb_vehicule;
    private Integer idplace_ancien = 0;


    private String lastID = "-1";
    private DaoFactory daofactory;
    private OccupationDao occupationManager;
    private Occupation occupation;

    private ParkingDao parkingmanager;
    private Parking parking;

    private PlaceDao placemanager;
    private Place place;

    private VehiculeDao vehiculemanager;
    private Vehicule vehicule;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        daofactory = DaoFactory.getInstance();

        parkingmanager = daofactory.getParkingDao();
        parking = new Parking();


        cb_parking.setItems(parkingmanager.getObservableList());
        cb_parking.getSelectionModel().selectFirst();
        parking = cb_parking.getSelectionModel().getSelectedItem();


        placemanager = daofactory.getPlaceDao();
        place = new Place();


        vehiculemanager = daofactory.getVehiculeDao();
        vehicule = new Vehicule();
        cb_vehicule.setItems(vehiculemanager.getObservableList());
        cb_vehicule.getSelectionModel().selectFirst();
        occupationManager = daofactory.getOccupationDao();

        // Formater heure et minute
        mask_heure(tb_heure_debut);
        mask_min(tb_min_debut);

        // remplir combobox place du parking selectionner
        Integer id_parc = cb_parking.getSelectionModel().getSelectedItem().getId_parking();
        get_places_idparking(id_parc);

    }

    @FXML
    void btn_new(ActionEvent event) {
        lastID = occupationManager.getNewID().toString();
        tb_id_occupation.setText(lastID);
        tb_date_debut.setValue(null);
        btn_delete.setDisable(true);
        cb_parking.getSelectionModel().selectFirst();
        cb_vehicule.getSelectionModel().selectFirst();
        LocalDate date = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
        tb_date_debut.setValue(date);
        tb_heure_debut.setText("00");
        tb_min_debut.setText("01");
    }

    @FXML
    void btn_save(ActionEvent actionEvent) {

        // verification de l'heure et les minutes c ils sont correct ou incorrect
        Integer heure = Integer.valueOf(tb_heure_debut.getText());
        Integer min = Integer.valueOf(tb_min_debut.getText());
        if (heure > 23 || min > 59) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("l'heure ou les minutes sont incorrect");
            a.show();
            tb_heure_debut.requestFocus();
            return;
        }


        occupation = new Occupation();
        Integer idoccupation = Integer.valueOf(tb_id_occupation.getText());
        Integer idplace_selected = cb_place.getSelectionModel().getSelectedItem().getId_place();
        Integer idvehicule_selected = cb_vehicule.getSelectionModel().getSelectedItem().getId_vehicule();

        //recuperer et converter dat_heure entrée vers Timestamp
        String date = tb_date_debut.getValue().toString() + " " + heure + ":" + min + ":00";
        Timestamp datedebut = Timestamp.valueOf(date);


        occupation.setId_occupation(idoccupation);
        occupation.setId_place(idplace_selected);
        occupation.setId_vehicule(idvehicule_selected);
        occupation.setDate_debut(datedebut);

        //test est un ajout ou une modification
        //-1 update
        // autre chiffre ajout

        if (lastID == "-1") {
            occupationManager.update(occupation);

            //mettre place est non dispo
            Place p = new Place();
            p.setId_place(idplace_selected);
            p.setEtat("NON");
            placemanager.update_etat(p);

            //mettre lancien place een cas changement place en disponible
            if (idplace_selected != idplace_ancien) {
                p.setId_place(idplace_ancien);
                p.setEtat("OUI");
                placemanager.update_etat(p);
                idplace_ancien = idplace_selected;
            }

        } else {
            occupationManager.add(occupation);
            Place p = new Place();
            p.setId_place(occupation.getId_place());
            p.setEtat("Non");
            placemanager.update_etat(p);
            lastID = "-1";
            btn_delete.setDisable(false);
        }

    }

    @FXML
    public void btn_delete(ActionEvent actionEvent) {

        Integer idoccup=Integer.parseInt(tb_id_occupation.getText());

        occupation.setId_occupation(idoccup);
        occupationManager.delete(occupation.getId_occupation());

//        Place p = new Place();
//        p.setId_place(occupation.getId_place());
//        p.setEtat("OUI");
//        placemanager.update_etat(p);

//        Node source = (Node) actionEvent.getSource();
//        Stage stage = (Stage) source.getScene().getWindow();
//        stage.close();

    }


    public void mask_heure(TextField textField) {
        /**
         * TextFormatter montant
         */
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {
                if (t.isReplaced()) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                    }
                }
                if (t.isAdded()) {

                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };
        final TextFormatter<String> urlFormatter = new TextFormatter(filter);
        textField.setTextFormatter(urlFormatter);
    }

    public void mask_min(TextField textField) {
        /**
         * TextFormatter montant
         */
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {
                if (t.isReplaced()) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                    }
                }
                if (t.isAdded()) {

                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };
        final TextFormatter<String> urlFormatter = new TextFormatter(filter);
        textField.setTextFormatter(urlFormatter);
    }

    //combobox vehicule choix un vehicule et recuperation des places
    public void parking_onClick(ActionEvent actionEvent) {
        Integer id = cb_parking.getSelectionModel().getSelectedItem().getId_parking();
        get_places_idparking(id);
    }

    public void get_places_idparking(int idparking) {

        //vider comboboxplace
        cb_place.setValue(null);
        ObservableList<Place> placeList = FXCollections.observableArrayList(placemanager.placeList(idparking, "OUI"));
        cb_place.setItems(placeList);
        cb_place.getSelectionModel().selectFirst();
    }


    public void putExtrat(Occupation occupation) {

        tb_id_occupation.setText(String.valueOf(occupation.getId_occupation()));

        String idplace = String.valueOf(occupation.getId_place());
        place = placemanager.getPlace("id_Place", idplace);
        String idparking = String.valueOf(place.getId_parking());
        parking = parkingmanager.getParking("id_parking", idparking);

        cb_parking.getSelectionModel().select(parking);
        cb_place.getSelectionModel().select(place);

        String idvehicule = String.valueOf(occupation.getId_vehicule());
        vehicule = vehiculemanager.getVehicule("id_Vehicule", idvehicule);
        cb_vehicule.getSelectionModel().select(vehicule);

        Timestamp date_debut = occupation.getDate_debut();
        Timestamp date_fin = occupation.getDate_fin();

        tb_date_debut.setValue(date_debut.toLocalDateTime().toLocalDate());
        tb_heure_debut.setText(String.valueOf(date_debut.getHours()));
        tb_min_debut.setText(String.valueOf(date_debut.getMinutes()));
        idplace_ancien = occupation.getId_place();

    }


}
