package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Place {
    @Nullable
    private Integer id_place;
    @Nullable
    private String descr;
    @Nullable
    private int id_parking;
    @Nullable
    private String etat;
    private List<Parking> parkingList;

    public Place(){};

    public Place(@Nullable Integer id_place, @Nullable String description,  @Nullable String etat,@Nullable int id_parking) {
        this.id_place = id_place;
        this.descr = description;
        this.etat = etat;
        this.id_parking = id_parking;
    }
    public Place( @Nullable String description,  @Nullable String etat,@Nullable int id_parking) {
        this.descr = description;
        this.etat = etat;
        this.id_parking = id_parking;
    }

    public Integer getId_place() {
        return id_place;
    }

    public void setId_place(Integer id_place) {
        this.id_place = id_place;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getId_parking() {
        return id_parking;
    }

    public void setId_parking(int id_parking) {
        this.id_parking = id_parking;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return  id_place + " : " + descr;
    }
    public String affiche() {
        return "Place{" +
                "id_place=" + id_place +
                ", descr='" + descr + '\'' +
                ", id_parking=" + id_parking +
                ", etat='" + etat + '\'' +
                '}';
    }
}
