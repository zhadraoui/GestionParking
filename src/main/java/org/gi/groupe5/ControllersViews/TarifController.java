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
import org.gi.groupe5.Models.Tarif;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.TarifDao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TarifController implements Initializable {
    @FXML
    private TableView<Tarif> table_tarif;
    @FXML
    private TableColumn<Tarif, Integer> col_id;
    @FXML
    private TableColumn<Tarif, Float> col_heure_debut;
    @FXML
    private TableColumn<Tarif, Float> col_heure_fin;
    @FXML
    private TableColumn<Tarif, Float> col_prix;
    @FXML
    private TextField filterField;

    private Scene scene;
    Stage primaryStage;

    private DaoFactory daofactory;
    private TarifDao tarifmanager;
    private Tarif tarif;


    private final String fenetre = Windows.TARIFCRUDVIEW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        tarifmanager = daofactory.getTarifDao();
        tarif = new Tarif();
        primaryStage = new Stage();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id_tarif"));
        col_heure_debut.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        col_heure_fin.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        refresh_onClick();
    }

    public void onClick(MouseEvent event) throws IOException {
        if (primaryStage.isShowing()) {
            return;
        }
        if (event.getClickCount() == 2) {
            tarif = new Tarif();
            if (table_tarif.getItems().size() > 0) {

                Integer idtarif = table_tarif.getSelectionModel().getSelectedItem().getId_tarif();
                Integer heure_debut = table_tarif.getSelectionModel().getSelectedItem().getHeure_debut();
                Integer heure_fin = table_tarif.getSelectionModel().getSelectedItem().getHeure_fin();
                Float prix = table_tarif.getSelectionModel().getSelectedItem().getPrix();

                tarif.setId_tarif(idtarif);
                tarif.setHeure_debut(heure_debut);
                tarif.setHeure_fin(heure_fin);
                tarif.setPrix(prix);
            }

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fenetre + ".fxml"));
            scene = new Scene(fxmlLoader.load());
            TarifCrudController cr = fxmlLoader.getController();
            cr.putExtrat(tarif);

            primaryStage.setTitle("Table Tarif");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
        }

    }

    public void refresh_onClick() {
        ObservableList<Tarif> dataList = FXCollections.observableArrayList(tarifmanager.getObservableList());
        table_tarif.setItems(dataList);

        FilteredList<Tarif> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tarif1 -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(tarif1.getId_tarif()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(tarif1.getPrix()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(tarif1.getHeure_debut()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(tarif1.getHeure_fin()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        SortedList<Tarif> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_tarif.comparatorProperty());

        table_tarif.setItems(sortedData);
    }
}