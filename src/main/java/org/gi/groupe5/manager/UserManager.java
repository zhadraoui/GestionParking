package org.gi.groupe5.manager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.gi.groupe5.Models.User;
import org.gi.groupe5.dao.DaoFactory;
import org.gi.groupe5.dao.UserDao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements UserDao {

    private final String TABLE_NAME = "user";
    private final DaoFactory daoFactory;
    private Alert alert = null;


    public UserManager(DaoFactory daoFactory) {

        this.daoFactory = daoFactory;

    }


    @Override
    public void add(User user) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + TABLE_NAME + "(id_user, login, pwd, first_inscr,email,roles) VALUES(?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, this.getNewID());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, User.getMd5(user.getPwd()));
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getRoles());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "utilisateur ajouter", "Ajout", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(User user) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET login=? , email=?,roles=? where id_user=? ;");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getRoles());
            preparedStatement.setInt(4, user.getId_user());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "utilisateur modifier", "modification", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void delete(Integer iduser) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM " + TABLE_NAME + " where Id_user=?");
            preparedStatement.setInt(1, iduser);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "utilisateur supprimer", "suppression", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public User getUser(String key, String value) {
        Connection connexion = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        User user = new User();

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where " + key + "=? ;";
            preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, value);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Integer iduser = resultat.getInt("id_user");
                String login = resultat.getString("login");
                String password = resultat.getString("pwd");
                Timestamp date_first_inscription = resultat.getTimestamp("first_inscr");
                Timestamp date_last_connexion = resultat.getTimestamp("last_conn");
                String email = resultat.getString("email");
                String roles = resultat.getString("roles");

                user = new User();
                user.setId_user(iduser);
                user.setLogin(login);
                user.setPwd(password);
                user.setEmail(email);
                user.setRoles(roles);
                user.setFirst_inscr(date_first_inscription);
                user.setLast_conn(date_last_connexion);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }

    @Override
    public List<User> userList() {
        List<User> userlist = new ArrayList<User>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        User user = null;

        try {
            connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            statement = connexion.createStatement();
            resultat = statement.executeQuery(sql);

            while (resultat.next()) {

                Integer iduser = resultat.getInt("id_user");
                String login = resultat.getString("login");
                String password = resultat.getString("pwd");
                Timestamp date_first_inscription = resultat.getTimestamp("first_inscr");
                Timestamp date_last_connexion = resultat.getTimestamp("last_conn");
                String email = resultat.getString("email");
                String roles = resultat.getString("roles");

                user = new User();
                user.setId_user(iduser);
                user.setLogin(login);
                user.setPwd(password);
                user.setEmail(email);
                user.setRoles(roles);
                user.setFirst_inscr(date_first_inscription);
                user.setLast_conn(date_last_connexion);

                // add place to userlist
                userlist.add(user);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return userlist;
    }

    @Override
    public boolean auth(String login, String password) {
        List<User> listuser = this.userList();
        for (User user : listuser) {
            if (user.getLogin().equals(login) && user.getPwd().equals(User.getMd5(password))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean update_password(String login, String oldpassword, String newpassword) {
        if (this.auth(login, oldpassword)) {

            Connection connexion = null;
            PreparedStatement preparedStatement = null;
            try {
                connexion = daoFactory.getConnection();
                preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET pwd=?  where login=? ;");
                preparedStatement.setString(1, User.getMd5(newpassword));
                preparedStatement.setString(2, login);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Mot de passe modifier ", "Modification du password", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ancien mot de passe inccorect", "Update Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    @Override
    public ObservableList<User> getObservableList() {
        ObservableList<User> userlist = FXCollections.observableArrayList(userList());
        return userlist;
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
            resultat = statement.executeQuery("SELECT MAX(id_user)+1 id_user FROM " + TABLE_NAME + ";");
            while (resultat.next()) {
                idplace = resultat.getInt("id_user");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return idplace;
    }

    @Override
    public void updateDateLastConnection(String login) {
        try {
            Connection connexion = null;
            PreparedStatement preparedStatement = null;

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE  " + TABLE_NAME + " SET last_conn=? where login=? ;");

            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static void main(String[] args) {
//        DaoFactory daofactory = DaoFactory.getInstance();
//        UserDao usermanager = daofactory.getUserDao();
//       List<User> l = usermanager.userList();
//       l.forEach(user -> System.out.println(user.affiche()));
//    }
}