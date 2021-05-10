package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.gi.groupe5.Models.Occupation;
import org.gi.groupe5.Models.Paiement;
import org.gi.groupe5.Models.Tarif;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.OccupationDao;
import org.gi.groupe5.dao.PaiementDao;
import org.gi.groupe5.dao.TarifDao;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class PaimentCrudController implements Initializable {

    @FXML private TextField tb_id_occupation;
    @FXML private TextField tb_id_paiement;
    @FXML private DatePicker tb_date_entree;
    @FXML private TextField tb_heure_entree;
    @FXML private TextField tb_min_entree;
    @FXML private DatePicker tb_date_sortie;
    @FXML private TextField tb_heure_sortie;
    @FXML private TextField tb_min_sortie;
    @FXML private TextField  tb_duree;
    @FXML private TextField tb_montant;
    @FXML private Button btn_save;
    @FXML private Button btn_close;

    private boolean paye = false;
    private DaoFactory daofactory;
    private OccupationDao occupationmanager;
    private PaiementDao paimentmanager;
    private TarifDao tarifmanager;
    private Paiement paiement;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daofactory = DaoFactory.getInstance();
        occupationmanager = daofactory.getOccupationDao();
        paimentmanager = daofactory.getPaimentDao();
        tarifmanager = daofactory.getTarifDao();

        //appliqué mask sur champs heure et minute
        mask_heure(tb_heure_sortie);
        mask_min(tb_min_sortie);


        if (tb_date_sortie.getValue() == null) {
            LocalDate date = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
            tb_date_sortie.setValue(date);
            tb_heure_sortie.setText("00");
            tb_min_sortie.setText("01");
        }


    }

    public void btn_save(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);

        //Convertion date d'entree
        Integer heure_entree = Integer.valueOf(tb_heure_entree.getText());
        Integer min_entree = Integer.valueOf(tb_min_entree.getText());
        String date_entree = tb_date_entree.getValue().toString() + " " + heure_entree + ":" + min_entree + ":00";
        Timestamp dateentree = Timestamp.valueOf(date_entree);

        //Convertion date sortie
        Integer heure_sortie = Integer.valueOf(tb_heure_sortie.getText());
        Integer min_sortie = Integer.valueOf(tb_min_sortie.getText());
        if (heure_sortie > 23 || min_sortie > 59) {
            a.setContentText("Veuillez saisir une date de sortie correcte");
            a.show();
            tb_heure_entree.requestFocus();
            return;
        }
        String date_sortie = tb_date_sortie.getValue().toString() + " " + heure_sortie + ":" + min_sortie + ":00";
        Timestamp datesortie = Timestamp.valueOf(date_sortie);

        if (dateentree.toLocalDateTime().isAfter(datesortie.toLocalDateTime())) {
            a.setContentText("Date de sortie doit etre superieure a la date d'entree");
            a.show();
            return;
        }

        Integer id_occup = Integer.valueOf(tb_id_occupation.getText());
        Integer id_paiement = Integer.valueOf(tb_id_paiement.getText());
        occupationmanager.update_date_sortie(id_occup.toString(), datesortie);

        float duree = occupationmanager.getDuree(id_occup);


        Float prix_unitaire = getTarif(duree);
        Float montant= prix_unitaire*(duree/60);

        paiement = new Paiement();
        paiement.setId_paiement(id_paiement);
        paiement.setId_occupation(id_occup);
        paiement.setDuree(duree);
        paiement.setMontant(montant);

        if (paye) {
            System.out.println("modification paiement");
            paimentmanager.update(paiement);
        } else {
            System.out.println("ajout paiement");
            paimentmanager.add( paiement);
        }
        tb_duree.setText(String.valueOf(paiement.getDuree()));
        tb_montant.setText(String.valueOf(paiement.getMontant()));
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    //formater textfiled heure
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

    //formater textfiled minutes
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


    public void putExtrat(Occupation occupation) {

        String idoccupation = occupation.getId_occupation().toString();
        tb_id_occupation.setText(occupation.getId_occupation().toString());

        Timestamp date_entree = occupation.getDate_debut();
        tb_date_entree.setValue(date_entree.toLocalDateTime().toLocalDate());
        tb_heure_entree.setText(String.valueOf(date_entree.getHours()));
        tb_min_entree.setText(String.valueOf(date_entree.getMinutes()));

        if (occupation.getDate_fin() != null) {
            Timestamp date_sortie = occupation.getDate_fin();
            tb_date_sortie.setValue(date_sortie.toLocalDateTime().toLocalDate());
            tb_heure_sortie.setText(String.valueOf(date_sortie.getHours()));
            tb_min_sortie.setText(String.valueOf(date_sortie.getMinutes()));
        }

        paiement = paimentmanager.getPaiement("id_occupation", idoccupation);
        Integer idpaiement = paimentmanager.getNewID();


        if (paiement == null && idpaiement == 1) {
            //occupation non payé et table paiement vide
            tb_id_paiement.setText("1");
            paye = false;

        } else if (paiement == null && idpaiement > 1) {
            //occupation non payé mais table paiement non vide
            tb_id_paiement.setText(idpaiement.toString());
            paye = false;
        } else {
            //Si occupation est payé alors recuperation du son ID
            tb_id_paiement.setText(paiement.getId_paiement().toString());
            tb_duree.setText(String.valueOf(paiement.getDuree()));
            tb_montant.setText(String.valueOf(paiement.getMontant()));
            paye = true;
        }

    }


    public float getTarif(Float duree) {
        List<Tarif> tariflist = tarifmanager.tarifList();
        for (Tarif t : tariflist) {
            if (duree >= t.getHeure_debut() && duree <= t.getHeure_fin()) {
                return t.getPrix();
            }
        }
        return 0;
    }

    public void btn_close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
