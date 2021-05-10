package org.gi.groupe5.dao;

import org.gi.groupe5.manager.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private String url = "jdbc:mysql://localhost:3306/park";
    private String username = "root";
    private String password = "";

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    DaoFactory() {

    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }

        DaoFactory instance = new DaoFactory();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    public ParkingDao getParkingDao() { return new ParkingManager(this); }
    public PlaceDao getPlaceDao() {
        return new PlaceManager(this);
    }
    public VehiculeDao getVehiculeDao() {
        return new VehiculeManager(this);
    }
    public UserDao getUserDao() {
        return new UserManager(this);
    }
    public ClientDao getClientDao() {
        return new ClientManager(this);
    }
    public TarifDao getTarifDao() { return new TarifManager(this); }
    public OccupationDao getOccupationDao() { return new OccupationManager(this); }
    public PaiementDao getPaimentDao() { return new PaiementManager(this); }

}