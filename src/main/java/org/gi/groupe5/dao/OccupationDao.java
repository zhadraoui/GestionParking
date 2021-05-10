package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Occupation;

import java.sql.Timestamp;
import java.util.List;

public interface OccupationDao {
    public void add(Occupation occupation);

    public void update(Occupation occupation);

    public void update_date_sortie(String idoccupation, Timestamp date_sortie);

    public void delete(Integer idoccupation);

    public Occupation getOccupation(String key, String value);

    public Float getDuree(Integer idoccupation);

    public List<Occupation> occupationList();

    public ObservableList<Occupation> getObservableList();

    public Integer getNewID();
}
