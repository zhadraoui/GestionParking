package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.gi.groupe5.Models.Tarif;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.TarifDao;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class TarifCrudController implements Initializable {

    @FXML
    private TextField tb_id;
    @FXML
    private TextField tb_heure_debut;
    @FXML
    private TextField tb_heure_fin;
    @FXML
    private TextField tb_prix;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_delete;

    private DaoFactory daofactory;
    private TarifDao tarifmanager;
    private Tarif tarif;

    private String lastID = "-1";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        daofactory = DaoFactory.getInstance();
        tarifmanager = daofactory.getTarifDao();
        tarif = new Tarif();
        mask_montant(tb_prix);
        mask_heure(tb_heure_debut);
        mask_heure(tb_heure_fin);

    }

    public void btn_new(ActionEvent actionEvent) {

        lastID = tarifmanager.getNewID().toString();
        if (lastID == "0") {
            lastID = "1";
        }
        tb_id.setText(lastID);
        tb_heure_debut.clear();
        tb_heure_fin.clear();
        tb_prix.clear();
        btn_delete.setDisable(true);
    }

    public void btn_save(ActionEvent actionEvent) {

        if (verification_textfield()==false ) {
            return;
        }
        Integer idtarif = Integer.parseInt(tb_id.getText());
        Integer heure_debut = Integer.parseInt(tb_heure_debut.getText());
        Integer heure_fin = Integer.parseInt(tb_heure_fin.getText());
        Float prix = Float.parseFloat(tb_prix.getText());
        tarif.setId_tarif(idtarif);
        tarif.setHeure_debut(heure_debut);
        tarif.setHeure_fin(heure_fin);
        tarif.setPrix(prix);

        if (lastID == "-1" && tarifmanager.getNewID()>0) {
            tarifmanager.update(tarif);
        } else {
            tarifmanager.add(tarif);
            lastID = "-1";
            btn_delete.setDisable(false);
        }
    }

    public void btn_delete(ActionEvent actionEvent) {
        if(lastID=="-1"){
            tarif.setId_tarif(Integer.parseInt(tb_id.getText()));
            tarifmanager.delete(tarif.getId_tarif());
        }
    }

    public void putExtrat(Tarif tarif) {


        Integer idtarif = tarif.getId_tarif();
        Integer heure_debut = tarif.getHeure_debut();
        Integer heure_fin = tarif.getHeure_fin();
        Float prix = tarif.getPrix();

        if (idtarif == null) {
            idtarif = 1;
        }
        if (heure_debut == null) {
            heure_debut = 0;
        }
        if (heure_fin == null) {
            heure_fin = 0;
        }
        if (prix == null) {
            prix = 0.0f;
        }

        tb_id.setText(String.valueOf(idtarif));
        tb_heure_debut.setText(String.valueOf(heure_debut));
        tb_heure_fin.setText(String.valueOf(heure_fin));
        tb_prix.setText(String.valueOf(prix));
    }

 /**
     * TextFormatter montant
     */
    public void mask_montant(TextField textField) {

        UnaryOperator<TextFormatter.Change> filter = t -> {
            if (t.isReplaced())
                if (t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        };
        final TextFormatter<String> urlFormatter = new TextFormatter(filter);
        textField.setTextFormatter(urlFormatter);
    }

    public void mask_heure(TextField textField) {

        UnaryOperator<TextFormatter.Change> filter = t -> {
            if (t.isReplaced())
                if (t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
            if (t.isAdded()) {
                if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            }
            return t;
        };
        final TextFormatter<String> urlFormatter = new TextFormatter(filter);
        textField.setTextFormatter(urlFormatter);
    }

    public boolean verification_textfield() {
        if (tb_heure_debut.getText() == "" || tb_heure_fin.getText() == "" || tb_prix.getText() == "") {
            JOptionPane.showMessageDialog(null, "Svp veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}