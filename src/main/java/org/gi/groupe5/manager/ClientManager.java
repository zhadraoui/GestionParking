package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Client;
import org.gi.groupe5.dao.ClientDao;
import org.gi.groupe5.dao.DaoFactory;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements ClientDao {

    private final DaoFactory daoFactory;
    private final String TABLE_NAME = "client";

    public ClientManager(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void add(Client client) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_client,cin, nom, prenom,gsm,adresse,email,id_Vehicule) VALUES(?,?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setString(2, client.getCin());
            preparedStatement.setString(3, client.getNom());
            preparedStatement.setString(4, client.getPrenom());
            preparedStatement.setString(5, client.getGsm());
            preparedStatement.setString(6, client.getAdresse());
            preparedStatement.setString(7, client.getEmail());
            preparedStatement.setInt(8, client.getId_Vehicule());
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Client Ajouter", "Ajouter", JOptionPane.OK_CANCEL_OPTION);


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(Client client) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET cin=? , nom=?, prenom=?, gsm=?, adresse=? , email=?, id_Vehicule=?  where id_client=? ;");
            preparedStatement.setString(1, client.getCin());
            preparedStatement.setString(2, client.getNom());
            preparedStatement.setString(3, client.getPrenom());
            preparedStatement.setString(4, client.getGsm());
            preparedStatement.setString(5, client.getAdresse());
            preparedStatement.setString(6, client.getEmail());
            preparedStatement.setInt(7, client.getId_Vehicule());
            preparedStatement.setInt(8, client.getId_client());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "CLient modifier ", "Modification", JOptionPane.OK_CANCEL_OPTION);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(Integer idclient) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where id_client=?");
            preparedStatement.setInt(1, idclient);
            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "CLient supprimer ", "Supression", JOptionPane.OK_CANCEL_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "CLient non supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Client getclient(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Client client = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer idclient = resultat.getInt("id_client");
                String cin = resultat.getString("cin");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String gsm = resultat.getString("gsm");
                String adresse = resultat.getString("adresse");
                String email = resultat.getString("email");
                Integer idvehicule = resultat.getInt("id_Vehicule");

                client = new Client();
                client.setId_client(idclient);
                client.setCin(cin);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setGsm(gsm);
                client.setAdresse(adresse);
                client.setEmail(email);
                client.setId_Vehicule(idvehicule);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return client;
    }

    @Override
    public List<Client> clientList() {
        List<Client> clientlist = new ArrayList<Client>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        Client client = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            statement = connexion.createStatement();
            resultat = statement.executeQuery(sql);

            while (resultat.next()) {
                Integer idclient = resultat.getInt("id_client");
                String cin = resultat.getString("cin");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String gsm = resultat.getString("gsm");
                String adresse = resultat.getString("adresse");
                String email = resultat.getString("email");
                Integer idvehicule = resultat.getInt("id_vehicule");

                //creat enw client
                client = new Client();
                client.setId_client(idclient);
                client.setCin(cin);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setGsm(gsm);
                client.setAdresse(adresse);
                client.setEmail(email);
                client.setId_Vehicule(idvehicule);

                // add place to userlist
                clientlist.add(client);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return clientlist;
    }

    @Override
    public ObservableList<Client> getObservableList() {
        ObservableList<Client> clientlist = FXCollections.observableArrayList(this.clientList());
        return clientlist;
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
            resultat = statement.executeQuery("SELECT MAX(id_client)+1 id_client FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idplace = resultat.getInt("id_client");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return idplace;
    }


//    public static void main(String[] args) {
//        DaoFactory daofactory = DaoFactory.getInstance();
//        ClientDao clientmanager = daofactory.getClientDao();
//        System.out.println(clientmanager.getNewID());
//    }
}

