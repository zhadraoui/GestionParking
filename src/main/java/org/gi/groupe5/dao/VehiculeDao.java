package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Vehicule;

import java.util.List;

public interface VehiculeDao {
    void add(Vehicule vehicule);
    void update(Vehicule vehicule);
    void delete(Integer idvehicule);
    Vehicule getVehicule(String key, String value);
    List<Vehicule> vehiculeList();
    ObservableList<Vehicule> getObservableList();
    Integer getNewID();
}
