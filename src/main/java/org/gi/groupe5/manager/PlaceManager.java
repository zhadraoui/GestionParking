package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Place;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.PlaceDao;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceManager implements PlaceDao {

    private final String TABLE_NAME = "place";
    private DaoFactory daoFactory;



    public PlaceManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;


    }

    @Override
    public void add(Place place) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_Place, descr, etat, id_Parking) VALUES(?, ?, ?,?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setString(2, place.getDescr());
            preparedStatement.setString(3, "OUI");
            preparedStatement.setInt(4, place.getId_parking());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Place ajouter ", "Ajout", JOptionPane.OK_CANCEL_OPTION);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Place place) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET descr=? ,id_Parking=? where id_Place=?");
            preparedStatement.setString(1, place.getDescr());
            preparedStatement.setInt(2, place.getId_parking());
            preparedStatement.setInt(3, place.getId_place());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Place modifier ", "Modification", JOptionPane.OK_CANCEL_OPTION);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void update_etat(Place place) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET etat=? where id_Place=?");
            preparedStatement.setString(1, place.getEtat());
            preparedStatement.setInt(2, place.getId_place());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void delete(Integer idplace) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_Place=?");
            preparedStatement.setInt(1, idplace);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Place supprimer ", "suppression", JOptionPane.OK_CANCEL_OPTION);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public Place getPlace(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Place place = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer id_place = resultat.getInt("id_Place");
                String description = resultat.getString("descr");
                String etat = resultat.getString("etat");
                Integer idparking = resultat.getInt("id_parking");

                place = new Place();
                place.setId_place(id_place);
                place.setDescr(description);
                place.setEtat(etat);
                place.setId_parking(idparking);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return place;
    }

    @Override
    public List<Place> placeList() {
        List<Place> placelist = new ArrayList<Place>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Place place = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            statement = connexion.createStatement();
            resultat = statement.executeQuery(sql);

            while (resultat.next()) {

                Integer idplace = resultat.getInt("id_Place");
                String description = resultat.getString("descr");
                String etat = resultat.getString("etat");
                Integer idparking = resultat.getInt("id_parking");

                //set value in object place
                place = new Place();
                place.setId_place(idplace);
                place.setDescr(description);
                place.setEtat(etat);
                place.setId_parking(idparking);

                // add place to placelist
                placelist.add(place);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return placelist;
    }


    @Override
    public List<Place> placeList(@Nullable Integer idparking,@Nullable String Etat) {
        List<Place> placelist = new ArrayList<Place>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        Place place = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where id_Parking=? and etat=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setInt(1, idparking);
            preparedStatement.setString(2, Etat);
            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {

                Integer idplace = resultat.getInt("id_Place");
                String description = resultat.getString("descr");
                String etat = resultat.getString("etat");
                Integer id_parking = resultat.getInt("id_parking");

                //set value in object place
                place = new Place();
                place.setId_place(idplace);
                place.setDescr(description);
                place.setEtat(etat);
                place.setId_parking(id_parking);

                // add place to placelist
                placelist.add(place);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return placelist;
    }

    @Override
    public ObservableList<Place> getObservableList() {
        ObservableList<Place> parkinglist = FXCollections.observableArrayList(placeList());
        return parkinglist;

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
            resultat = statement.executeQuery("SELECT MAX(id_Place)+1 id_Place FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idplace = resultat.getInt("id_Place");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return idplace;

    }

}
