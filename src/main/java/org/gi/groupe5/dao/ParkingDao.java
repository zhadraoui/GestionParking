package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Parking;

import java.util.List;

public interface ParkingDao {

    public void add(Parking parking);

    public void update(Parking parking);

    public void delete(Integer idparking);

    public Parking getParking(String key, String value);

    public List<Parking> parkingList();

    public ObservableList<Parking> getObservableList();

    public Integer getNewID();

}
