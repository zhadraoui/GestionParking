package org.gi.groupe5.ControllersViews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import org.gi.groupe5.Models.Occupation;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.OccupationDao;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.Timer;

public class OccupationController implements Initializable {

    @FXML
    private TableView<Occupation> table_occupation;
    @FXML
    private TableColumn<Occupation, Integer> col_id;
    @FXML
    private TableColumn<Occupation, Timestamp> col_date_debut;
    @FXML
    private TableColumn<Occupation, Timestamp> col_date_fin;
    @FXML
    private TableColumn<Occupation, Integer> col_place;
    @FXML
    private TableColumn<Occupation, Integer> col_vehicule;

    @FXML
    private TextField filterField;
    private Scene scene;

    private DaoFactory daofactory;
    private OccupationDao occupationManager;
    private Occupation occupation;
    private Stage primaryStage = null;
    private Timer timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        occupationManager = daofactory.getOccupationDao();
        occupation = new Occupation();
        primaryStage = new Stage();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id_occupation"));
        col_date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        col_date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        col_place.setCellValueFactory(new PropertyValueFactory<>("id_place"));
        col_vehicule.setCellValueFactory(new PropertyValueFactory<>("id_vehicule"));

        refresh();
    }

    public void tableOccupation_onClick(MouseEvent event) throws IOException {

        if (primaryStage.isShowing()) {
            refresh();
            return;
        }
        if (event.getClickCount() == 2) {

            occupation = table_occupation.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.OCCUPATIONCRUDVIEW + ".fxml"));
            scene = new Scene(fxmlLoader.load());
            OccupationCrudController cr = fxmlLoader.getController();
            cr.putExtrat(occupation);
            primaryStage.close();
            primaryStage.toFront();
            primaryStage.setTitle("Occupation");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();


        }


    }


    public void refresh() {

        table_occupation.setItems(null);

        table_occupation.setItems(occupationManager.getObservableList());

        ObservableList<Occupation> dataList = FXCollections.observableArrayList(occupationManager.occupationList());

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Occupation> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(occupation1 -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(occupation1.getId_occupation()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(occupation1.getId_place()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(occupation1.getId_vehicule()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(occupation1.getDate_debut()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(occupation1.getDate_fin()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Occupation> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table_occupation.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table_occupation.setItems(sortedData);
    }

    public void refresh_onCLick(ActionEvent actionEvent) {
        refresh();
    }

    public void paiment_onCLick(ActionEvent actionEvent) throws IOException {
        if (primaryStage.isShowing()) {
            primaryStage.toFront();
            refresh();
            return;
        }
        occupation = table_occupation.getSelectionModel().getSelectedItem();
        if (occupation != null) {
            occupation = table_occupation.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.PAIEMENTCRUDVIEW + ".fxml"));

            scene = new Scene(fxmlLoader.load());

            //Get Controller and put objet occupation
            PaimentCrudController cr = fxmlLoader.getController();
            cr.putExtrat(occupation);
            primaryStage.toFront();
            primaryStage.setTitle("paiment");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
