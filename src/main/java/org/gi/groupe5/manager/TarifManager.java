package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Tarif;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.TarifDao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarifManager  implements TarifDao {

    private final String TABLE_NAME = "tarif";
    private final DaoFactory daoFactory;


    public TarifManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void add(Tarif tarif) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_Tarif,heure_debut, heure_fin, prix) VALUES(?, ?, ?,?);");
            if(this.getNewID()==0){
                preparedStatement.setInt(1, 1);
            }else{
                preparedStatement.setInt(1, this.getNewID());
            }
            preparedStatement.setInt(2, tarif.getHeure_debut());
            preparedStatement.setInt(3, tarif.getHeure_fin());
            preparedStatement.setFloat(4, tarif.getPrix());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tarification ajouter", "Ajout", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Tarif tarif) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET heure_debut=? , heure_fin=?, prix=? where id_Tarif=?");
            preparedStatement.setInt(1, tarif.getHeure_debut());
            preparedStatement.setInt(2, tarif.getHeure_fin());
            preparedStatement.setFloat(3, tarif.getPrix());
            preparedStatement.setInt(4, tarif.getId_tarif());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tarification modifier", "Modification", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idtarif) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_Tarif=?");
            preparedStatement.setInt(1, idtarif);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tarification supprimer", "Suppression", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tarif getTarif(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Tarif tarif = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            System.out.println(sql);
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer idtarif = resultat.getInt("id_Tarif");
                Integer heure_debut = resultat.getInt("heure_debut");
                Integer heure_fin = resultat.getInt("heure_fin");
                Float tarification = resultat.getFloat("prix");

                tarif = new Tarif();
                tarif.setId_tarif(idtarif);
                tarif.setHeure_debut(heure_debut);
                tarif.setHeure_fin(heure_fin);
                tarif.setPrix(tarification);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tarif;
    }

    @Override
    public List<Tarif> tarifList() {
        List<Tarif> tarifList = new ArrayList<Tarif>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Tarif tarif = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            statement = connexion.createStatement();
            resultat = statement.executeQuery(sql);

            while (resultat.next()) {

                Integer idtarif = resultat.getInt("id_Tarif");
                Integer heure_debut = resultat.getInt("heure_debut");
                Integer heure_fin = resultat.getInt("heure_fin");
                Float tarification = resultat.getFloat("prix");

                tarif = new Tarif();
                tarif.setId_tarif(idtarif);
                tarif.setHeure_debut(heure_debut);
                tarif.setHeure_fin(heure_fin);
                tarif.setPrix(tarification);

                // add place to placelist
                tarifList.add(tarif);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tarifList;
    }

    @Override
    public ObservableList<Tarif> getObservableList() {
        ObservableList<Tarif> tariflist = FXCollections.observableArrayList(tarifList());
        return tariflist;
    }

    @Override
    public Integer getNewID() {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        Integer idplace = 0;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT MAX(id_Tarif)+1 id_Tarif FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idplace = resultat.getInt("id_Tarif");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idplace;
    }
}