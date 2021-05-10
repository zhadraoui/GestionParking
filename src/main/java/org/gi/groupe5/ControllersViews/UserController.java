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
import org.gi.groupe5.Models.User;
import org.gi.groupe5.Windows;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.UserDao;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TableView<User> table_user;
    @FXML
    private TableColumn<User, Integer> col_id;
    @FXML
    private TableColumn<User, String> col_login;
    @FXML
    private TableColumn<User, String> col_password;
    @FXML
    private TableColumn<User, Timestamp> col_date_first_insc;
    @FXML
    private TableColumn<User, Timestamp> col_date_last_con;
    @FXML
    private TableColumn<User, String> col_email;
    @FXML
    private TableColumn<User, String> col_role;
    @FXML
    private TextField filterField;

    private Scene scene;
    private Stage primaryStage;

    private DaoFactory daoactory;
    private UserDao usermanager;
    ;
    private User user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daoactory = DaoFactory.getInstance();
        usermanager = daoactory.getUserDao();
        user = new User();
        primaryStage = new Stage();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        col_date_first_insc.setCellValueFactory(new PropertyValueFactory<>("first_inscr"));
        col_date_last_con.setCellValueFactory(new PropertyValueFactory<>("last_conn"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("roles"));
        table_user.setItems(usermanager.getObservableList());

        ObservableList<User> dataList = FXCollections.observableArrayList(usermanager.userList());


        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<User> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user1 -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (user1.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(user1.getId_user()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (user1.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(user1.getFirst_inscr()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(user1.getLast_conn()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (user1.getRoles().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<User> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table_user.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table_user.setItems(sortedData);
    }

    public void onClick(MouseEvent event) throws IOException {
        if (primaryStage.isShowing()) {
            return;
        }
        if (event.getClickCount() == 2) {
            user = new User();
            user.setId_user(table_user.getSelectionModel().getSelectedItem().getId_user());
            user.setLogin(table_user.getSelectionModel().getSelectedItem().getLogin());
            user.setPwd(table_user.getSelectionModel().getSelectedItem().getPwd());
            user.setFirst_inscr(table_user.getSelectionModel().getSelectedItem().getFirst_inscr());
            user.setLast_conn(table_user.getSelectionModel().getSelectedItem().getLast_conn());
            user.setEmail(table_user.getSelectionModel().getSelectedItem().getEmail());
            user.setRoles(table_user.getSelectionModel().getSelectedItem().getRoles());

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Windows.USERCRUDVIEW + ".fxml"));
            scene = new Scene(fxmlLoader.load());
            UserCrudController cr = fxmlLoader.getController();
            cr.putExtrat(user);

            primaryStage.setTitle("User");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();

        }

    }
}



