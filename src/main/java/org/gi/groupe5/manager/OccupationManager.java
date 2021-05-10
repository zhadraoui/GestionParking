package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Occupation;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.OccupationDao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OccupationManager implements OccupationDao {

    private final String TABLE_NAME = "occupation";
    private final DaoFactory daoFactory;

    public OccupationManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void add(Occupation occupation) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_occupation, date_debut, id_place,id_vehicule) VALUES(?, ?, ?, ?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setTimestamp(2, occupation.getDate_debut());
            preparedStatement.setInt(3, occupation.getId_place());
            preparedStatement.setInt(4, occupation.getId_vehicule());
            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Place Réserver  ", "Occupation d'une place", JOptionPane.OK_CANCEL_OPTION);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(Occupation occupation) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET date_debut=? , id_place=?, id_vehicule=? where id_occupation=? ;");

            preparedStatement.setTimestamp(1, occupation.getDate_debut());
            preparedStatement.setInt(2, occupation.getId_place());
            preparedStatement.setInt(3, occupation.getId_vehicule());
            preparedStatement.setInt(4, occupation.getId_occupation());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Place N° " + occupation.getId_place() + " est bien reservée", "Occupation Modifier", JOptionPane.OK_CANCEL_OPTION);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void update_date_sortie(String idoccupation, Timestamp date_sortie) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET date_fin=? where id_occupation=? ;");
            preparedStatement.setTimestamp(1, date_sortie);
            preparedStatement.setString(2, idoccupation);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Date Sortie Modifier", "Date sortie", JOptionPane.OK_CANCEL_OPTION);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void delete(Integer idoccupation) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_occupation=?");
            preparedStatement.setInt(1, idoccupation);
            if (preparedStatement.executeUpdate() > 0)
                JOptionPane.showMessageDialog(null, "Occupation supprimer ", "Supression", JOptionPane.OK_CANCEL_OPTION);


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Occupation getOccupation(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Occupation occupation = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer idoccupation = resultat.getInt("id_occupation");
                Timestamp date_debut = resultat.getTimestamp("date_debut");
                Timestamp date_fin = resultat.getTimestamp("date_fin");
                Integer idplace = resultat.getInt("id_place");
                Integer idvehicule = resultat.getInt("id_vehicule");

                occupation = new Occupation();
                occupation.setId_occupation(idoccupation);
                occupation.setDate_debut(date_debut);
                occupation.setDate_fin(date_fin);
                occupation.setId_place(idplace);
                occupation.setId_vehicule(idvehicule);


            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return occupation;
    }


    @Override
    public Float getDuree(Integer idoccupation) {
        Float duree = null;
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Occupation occupation = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT TIMESTAMPDIFF(minute,occupation.date_debut,occupation.date_fin) duree from " + TABLE_NAME + "  where  id_occupation=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setInt(1, idoccupation);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                duree = resultat.getFloat("duree");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return duree;
    }

    @Override
    public List<Occupation> occupationList() {
        List<Occupation> occupationlist = new ArrayList<Occupation>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Occupation occupation = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            statement = connexion.createStatement();
            resultat = statement.executeQuery(sql);

            while (resultat.next()) {
                Integer id_occupation = resultat.getInt("id_occupation");
                Timestamp date_debut = resultat.getTimestamp("date_debut");
                Timestamp date_fin = resultat.getTimestamp("date_fin");
                Integer id_place = resultat.getInt("id_place");
                Integer id_vehicule = resultat.getInt("id_vehicule");

                occupation = new Occupation();
                occupation.setId_occupation(id_occupation);
                occupation.setDate_debut(date_debut);
                occupation.setDate_fin(date_fin);
                occupation.setId_place(id_place);
                occupation.setId_vehicule(id_vehicule);

                // add place to userlist
                occupationlist.add(occupation);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return occupationlist;
    }

    @Override
    public ObservableList<Occupation> getObservableList() {
        ObservableList<Occupation> occupationlists = FXCollections.observableArrayList(this.occupationList());
        return occupationlists;
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
            resultat = statement.executeQuery("SELECT MAX(id_occupation)+1 id_occupation FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idplace = resultat.getInt("id_occupation");
                if (idplace == 0) {
                    idplace = 1;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return idplace;
    }

//    public static void main(String[] args) {
//        DaoFactory daofactory = DaoFactory.getInstance();
//        OccupationDao occupationManager = daofactory.getOccupationDao();
//
//        occupationManager.delete(4);
//    }
}
