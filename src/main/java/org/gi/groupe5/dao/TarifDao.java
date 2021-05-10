package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Parking;
import org.gi.groupe5.Models.Tarif;

import java.util.List;

public interface TarifDao {

    void add(Tarif tarif);
    void update(Tarif tarif);
    void delete(Integer idtarif);
    Tarif getTarif(String key,String value);
    List<Tarif> tarifList();
    ObservableList<Tarif> getObservableList();
    public Integer getNewID();

}
