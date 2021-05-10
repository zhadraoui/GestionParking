package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Place;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlaceDao {

    public void add(Place place);

    public void update(Place place);

    void update_etat(Place place);

    public void delete(Integer idplace);

    public Place getPlace(String key, String value);

    public List<Place> placeList();

    public List<Place> placeList(@Nullable Integer idparking,@Nullable String etat);

    public ObservableList<Place> getObservableList();

    public Integer getNewID();
}
