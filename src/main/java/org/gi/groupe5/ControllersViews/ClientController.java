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
import org.gi.groupe5.Models.Client;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.ClientDao;
import org.gi.groupe5.dao.DaoFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TableView<Client> table_client;
    @FXML
    private TableColumn<Client, Integer> col_idclient;
    @FXML
    private TableColumn<Client, String> col_cin;
    @FXML
    private TableColumn<Client, String> col_nom;
    @FXML
    private TableColumn<Client, String> col_prenom;
    @FXML
    private TableColumn<Client, String> col_gsm;
    @FXML
    private TableColumn<Client, String> col_adresse;
    @FXML
    private TableColumn<Client, String> col_email;
    @FXML
    private TableColumn<Client, Integer> col_idvehicule;
    @FXML
    private TextField filterField;
    private Client client;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DaoFactory daofactory;
    private ClientDao clientmanager;
    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        clientmanager = daofactory.getClientDao();
        client = new Client();
        primaryStage= new Stage();

        col_idclient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_gsm.setCellValueFactory(new PropertyValueFactory<>("gsm"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_idvehicule.setCellValueFactory(new PropertyValueFactory<>("id_Vehicule"));
        refresh();

    }

    public void click(MouseEvent event) throws IOException {
        if (primaryStage.isShowing()) {
            refresh();
            return;
        }
        if (event.getClickCount() == 2) {
            client = new Client();
            client.setId_client(table_client.getSelectionModel().getSelectedItem().getId_client());
            client.setCin(table_client.getSelectionModel().getSelectedItem().getCin());
            client.setNom(table_client.getSelectionModel().getSelectedItem().getNom());
            client.setPrenom(table_client.getSelectionModel().getSelectedItem().getPrenom());
            client.setGsm(table_client.getSelectionModel().getSelectedItem().getGsm());
            client.setAdresse(table_client.getSelectionModel().getSelectedItem().getAdresse());
            client.setEmail(table_client.getSelectionModel().getSelectedItem().getEmail());
            client.setId_Vehicule(table_client.getSelectionModel().getSelectedItem().getId_Vehicule());

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.CLIENTCRUDVIEW + ".fxml"));
            scene = new Scene(fxmlLoader.load());

            ClientCrudController cr = fxmlLoader.getController();
            System.out.println(client.afficher());
            cr.putExtrat(client);
            primaryStage = new Stage();
            primaryStage.setTitle("Client");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();

        }
    }

    private void refresh() {
        table_client.setItems(clientmanager.getObservableList());

        ObservableList<Client> dataList = FXCollections.observableArrayList(clientmanager.clientList());


        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Client> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client1 -> {
                // If filter text is empty, display all clients.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }


                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(client1.getId_client()).indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (client1.getCin().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (client1.getNom().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (client1.getPrenom().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (client1.getGsm().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (client1.getAdresse().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (client1.getEmail().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (String.valueOf(client1.getId_Vehicule()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table_client.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table_client.setItems(sortedData);
    }

}



