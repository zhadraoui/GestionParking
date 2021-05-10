package org.gi.groupe5.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Paiement;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.PaiementDao;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementManager implements PaiementDao {

    private final String TABLE_NAME = "paiement";
    private final DaoFactory daoFactory;

    public PaiementManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void add(Paiement paiement) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_paiement, id_occupation, date_paiement, duree, montant) VALUES(?,?,?,?,?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setInt(2, paiement.getId_occupation());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setFloat(4, paiement.getDuree());
            preparedStatement.setFloat(5, paiement.getMontant());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Paiement ajouter", "Ajout", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(Paiement paiement) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET date_paiement=?, duree=?, montant=?  where id_paiement=? ;");
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setFloat(2, paiement.getDuree());
            preparedStatement.setFloat(3, paiement.getMontant());
            preparedStatement.setInt(4, paiement.getId_paiement());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Paiement modifier", "Modification", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void delete(Integer idpaiment) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_paiment=?");
            preparedStatement.setInt(1, idpaiment);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Paiement supprimer", "Suppression", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Paiement getPaiement(String key, String value) {

        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Paiement paiement = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer id_paiment = resultat.getInt("id_paiement");
                Integer id_occupation = resultat.getInt("id_occupation");
                Timestamp date_paiment = resultat.getTimestamp("date_paiement");
                Float duree = resultat.getFloat("duree");
                Float montant = resultat.getFloat("montant");


                paiement = new Paiement();
                paiement.setId_paiement(id_paiment);
                paiement.setId_occupation(id_occupation);
                paiement.setDate_paiement(date_paiment);
                paiement.setDuree(duree);
                paiement.setMontant(montant);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return paiement;
    }

    @Override
    public List<Paiement> paiementList() {
        List<Paiement> paimentlist = new ArrayList<Paiement>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                Integer id_paiment = resultat.getInt("id_paiement");
                Timestamp date_paiment = resultat.getTimestamp("date_paiement");
                Integer id_occupation = resultat.getInt("id_occupation");
                Float duree = resultat.getFloat("duree");
                Float montant = resultat.getFloat("montant");

                Paiement paiement = new Paiement();
                paiement.setId_paiement(id_paiment);
                paiement.setDate_paiement(date_paiment);
                paiement.setId_occupation(id_occupation);
                paiement.setDuree(duree);
                paiement.setMontant(montant);

                paimentlist.add(paiement);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return paimentlist;

    }

    @Override
    public ObservableList<Paiement> getObservableList() {
        ObservableList<Paiement> paiementlists = FXCollections.observableArrayList(paiementList());
        return paiementlists;
    }

    @Override
    public Integer getNewID() {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Integer id_paiment = 0;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT MAX(id_paiement)+1 id_paiement FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                id_paiment = resultat.getInt("id_paiement");
                if(id_paiment==0)
                    id_paiment++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return id_paiment;
    }
}
