package org.gi.groupe5.ControllersViews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.gi.groupe5.App;
import org.gi.groupe5.Models.Place;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.PlaceDao;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceController implements Initializable {
    @FXML private TableView<Place> table_place;
    @FXML private TableColumn<Place, Integer> col_id_Place;
    @FXML private TableColumn<Place, String> col_descr;
    @FXML private TableColumn<Place, String> col_etat;
    @FXML private TableColumn<Place, Integer> col_Parking;
    @FXML private TextField filterField;
    private Scene scene;

    private DaoFactory daofactory;
    private PlaceDao placemanager;
    private Place place;
    private Stage primaryStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        placemanager = daofactory.getPlaceDao();
        place = new Place();
        primaryStage= new Stage();

        col_id_Place.setCellValueFactory(new PropertyValueFactory<>("id_place"));
        col_descr.setCellValueFactory(new PropertyValueFactory<>("descr"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_Parking.setCellValueFactory(new PropertyValueFactory<>("id_parking"));
        table_place.setItems(placemanager.getObservableList());

        ObservableList<Place> dataList = FXCollections.observableArrayList(placemanager.placeList());


        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Place> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(place1 -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (place1.getDescr().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(place1.getId_place()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else if (place1.getEtat().indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (String.valueOf(place1.getId_parking()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Place> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table_place.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table_place.setItems(sortedData);
    }

    public void onClick(MouseEvent event) throws IOException {
        if(primaryStage.isShowing()){
            return;
        }
        if(event.getClickCount()==2){
            place= new Place();
            place.setId_place(table_place.getSelectionModel().getSelectedItem().getId_place());
            place.setDescr(table_place.getSelectionModel().getSelectedItem().getDescr());
            place.setEtat(table_place.getSelectionModel().getSelectedItem().getEtat());
            place.setId_parking(table_place.getSelectionModel().getSelectedItem().getId_parking());

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.PLACECRUDVIEW + ".fxml"));
            scene = new Scene(fxmlLoader.load());
            PlaceCrudController cr=fxmlLoader.getController();
            cr.putExtrat(place);

            primaryStage.setTitle("Place");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();

        }

    }


}
