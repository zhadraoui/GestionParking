package org.gi.groupe5.ControllersViews;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.gi.groupe5.App;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.ParkingDao;
import org.gi.groupe5.manager.ParkingManager;
import org.gi.groupe5.Models.Parking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParkingController implements Initializable {

    //View
    @FXML private TableView<Parking> table_parking;
    @FXML private TableColumn<Parking, Integer> col_id;
    @FXML private TableColumn<Parking, String> col_adresse;
    @FXML private TableColumn<Parking, Integer> col_capacite;
    @FXML private TextField filterField;

    //affichage
    private Stage stage;
    private Scene scene;
    private Parent root;
    Stage primaryStage;
    //Metier
    private DaoFactory daofactory;
    private ParkingDao parkingmanager;
    private Parking parking;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryStage = new Stage();
        daofactory=DaoFactory.getInstance();
        parkingmanager=daofactory.getParkingDao();
        parking = new Parking();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id_parking"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_capacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        ObservableList<Parking> dataList = FXCollections.observableArrayList(parkingmanager.parkingList());


        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Parking> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(parking1 -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (parking1.getAdresse().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(parking1.getCapacite()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Parking> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table_parking.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table_parking.setItems(sortedData);
    }

    public void onClick(MouseEvent event) throws IOException {

        if(primaryStage.isShowing()){
            return;
        }

        if (event.getClickCount() == 2) {
            parking = new Parking();
            parking.setId_parking(table_parking.getSelectionModel().getSelectedItem().getId_parking());
            parking.setAdresse(table_parking.getSelectionModel().getSelectedItem().getAdresse());
            parking.setCapacite(table_parking.getSelectionModel().getSelectedItem().getCapacite());

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.PARKINGCRUDVIEW + ".fxml"));
            scene = new Scene(fxmlLoader.load());

            ParkingCrudController cr = fxmlLoader.getController();
            cr.putExtrat(parking);

            primaryStage.setTitle("Gestion parking");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();


        }
    }
}
