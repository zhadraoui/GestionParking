package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Client;
import org.gi.groupe5.Models.Parking;
import org.gi.groupe5.Models.Place;

import java.util.List;

public interface ClientDao {
    void add(Client client);
    void update(Client client);
    void delete(Integer idclient);
    Client getclient(String key,String value);
    List<Client> clientList();
    ObservableList<Client> getObservableList();
    public Integer getNewID();
}
