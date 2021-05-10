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
import org.gi.groupe5.Models.Vehicule;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.VehiculeDao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VehiculeController implements Initializable {
    @FXML
    private TableView<Vehicule> table_vehicule;
    @FXML
    private TableColumn<Vehicule, Integer> col_idvehicule;
    @FXML
    private TableColumn<Vehicule, String> col_matricule;
    @FXML
    private TextField filterField;

    private DaoFactory daofactory;
    private VehiculeDao vehiculemanager;
    private Vehicule vehicule;

    private Scene scene;
    Stage primaryStage;

    private final String fenetre = Windows.VEHICULECRUDVIEW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        vehiculemanager = daofactory.getVehiculeDao();
        ;
        vehicule = new Vehicule();
        primaryStage = new Stage();
        col_idvehicule.setCellValueFactory(new PropertyValueFactory<>("id_vehicule"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        refresh_onClick();

    }

    public void click(MouseEvent event) throws IOException {
        if(primaryStage.isShowing()){
            return;
        }
        if (event.getClickCount() == 2) {
            vehicule = new Vehicule();
            vehicule.setId_vehicule(table_vehicule.getSelectionModel().getSelectedItem().getId_vehicule());
            vehicule.setMatricule(table_vehicule.getSelectionModel().getSelectedItem().getMatricule());


            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fenetre + ".fxml"));
            scene = new Scene(fxmlLoader.load());

            VehiculeCrudController cr = fxmlLoader.getController();
            cr.putExtrat(vehicule);


            primaryStage.setScene(scene);
            primaryStage.setTitle("Vehicule");
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();

        }
    }

    public void refresh_onClick() {

        table_vehicule.setItems(vehiculemanager.getObservableList());
        ObservableList<Vehicule> dataList = FXCollections.observableArrayList(vehiculemanager.vehiculeList());


        FilteredList<Vehicule> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vehicule1 -> {
                // If filter text is empty, display all vehicules.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare id vehicule and matricule of every vehicule with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(vehicule1.getId_vehicule()).indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches id vehicule
                } else if (vehicule1.getMatricule().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches matricule.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Vehicule> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table_vehicule.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table_vehicule.setItems(sortedData);
    }
}



