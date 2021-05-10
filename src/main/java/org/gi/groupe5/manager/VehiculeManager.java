package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Vehicule;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.VehiculeDao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeManager implements VehiculeDao {

    private final String TABLE_NAME = "vehicule";
    private final DaoFactory daoFactory;


    public VehiculeManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        
    }
    @Override
    public void add(Vehicule vehicule) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_Vehicule,matricule) VALUES(?,?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setString(2, vehicule.getMatricule());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehicule ajouter", "Ajout", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void update(Vehicule vehicule) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET matricule=? where id_Vehicule=?");
            preparedStatement.setString(1, vehicule.getMatricule());
            preparedStatement.setInt(2, vehicule.getId_vehicule());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehicule Modifier", "Modification", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(Integer idvehicule) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_Vehicule=?");
            preparedStatement.setInt(1, idvehicule);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehicule supprimer", "suppression", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Vehicule getVehicule(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Vehicule vehicule = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";

            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer idvehicule = resultat.getInt("id_Vehicule");
                String matricule = resultat.getString("matricule");

                vehicule = new Vehicule();
                vehicule.setId_vehicule(idvehicule);
                vehicule.setMatricule(matricule);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return vehicule;
    }

    @Override
    public List<Vehicule> vehiculeList() {
        List<Vehicule> vehiculeList = new ArrayList<Vehicule>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Vehicule vehicule = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " order by id_Vehicule;";
            statement = connexion.createStatement();
            resultat = statement.executeQuery(sql);

            while (resultat.next()) {

                Integer idvehicule = resultat.getInt("id_Vehicule");
                String matricule = resultat.getString("matricule");


                //set value in object vehicule
                vehicule = new Vehicule();
                vehicule.setId_vehicule(idvehicule);
                vehicule.setMatricule(matricule);

                // add place to placelist
                vehiculeList.add(vehicule);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return vehiculeList;
    }

    @Override
    public ObservableList<Vehicule> getObservableList() {
        ObservableList<Vehicule> vehiculelists = FXCollections.observableArrayList(vehiculeList());
        return vehiculelists;
    }

    @Override
    public Integer getNewID() {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        Integer idvehicule = 0;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT MAX(id_Vehicule)+1 id_Vehicule FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idvehicule = resultat.getInt("id_Vehicule");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return idvehicule;

    }

}