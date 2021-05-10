package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Paiement;

import java.util.List;

public interface PaiementDao {
    public void add(Paiement paiement);

    public void update(Paiement paiement);

    public void delete(Integer idpaiment);

    public Paiement getPaiement(String key, String value);

    public List<Paiement> paiementList();

    public ObservableList<Paiement> getObservableList();

    public Integer getNewID();
}
