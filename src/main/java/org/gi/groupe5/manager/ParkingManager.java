package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Parking;
import org.gi.groupe5.Models.Place;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.ParkingDao;
import org.gi.groupe5.dao.PlaceDao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingManager implements ParkingDao {
    private final String TABLE_NAME = "parking";


    private DaoFactory daoFactory;

    public ParkingManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        PlaceDao placemanager = daoFactory.getPlaceDao();

    }

    @Override
    public void add(Parking parking) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_parking,adresse,capacite,nbr_dispo) VALUES(?,?,?,?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setString(2, parking.getAdresse());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "parc ajouter", "Ajout", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(Parking parking) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET adresse=? where id_parking=?");
            preparedStatement.setString(1, parking.getAdresse());
            preparedStatement.setInt(2, parking.getId_parking());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "prac modifier", "Modification", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void delete(Integer idparking) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_parking=?");
            preparedStatement.setInt(1, idparking);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "parc supprimer", "Suppression", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Parking getParking(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Parking parking = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                Integer idparking = resultat.getInt("id_parking");
                String adresse = resultat.getString("adresse");
                Integer capacite = resultat.getInt("capacite");

                parking = new Parking();
                parking.setId_parking(idparking);
                parking.setAdresse(adresse);
                parking.setCapacite(capacite);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return parking;
    }

    @Override
    public Integer getNewID() {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Integer idparking = 0;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT MAX(id_parking)+1 id_parking FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idparking = resultat.getInt("id_parking");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return idparking;
    }

    @Override
    public List<Parking> parkingList() {
        List<Parking> parkinglist = new ArrayList<Parking>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                Integer idparking = resultat.getInt("id_parking");
                String adresse = resultat.getString("adresse");
                Integer capacite = resultat.getInt("capacite");

                Parking parking = new Parking();
                parking.setId_parking(idparking);
                parking.setAdresse(adresse);
                parking.setCapacite(capacite);


                parkinglist.add(parking);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return parkinglist;
    }

    /**
     * Get  All item of  Parking
     *
     * @return ObservableList<Parking>
     */
    public ObservableList<Parking> getObservableList() {
        ObservableList<Parking> parkinglist = FXCollections.observableArrayList(parkingList());
        return parkinglist;
    }




}
