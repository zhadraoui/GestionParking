package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Occupation {
    @Nullable
    private Integer id_occupation;
    @Nullable
    private Timestamp date_debut;
    @Nullable
    private Timestamp date_fin;
    @Nullable
    private int id_place;
    @Nullable
    private int id_vehicule;

    public Occupation(@Nullable Integer id_occupation, @Nullable Timestamp date_debut, @Nullable Timestamp date_fin, @Nullable int id_place, @Nullable int id_vehicule) {
        this.id_occupation = id_occupation;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_place = id_place;
        this.id_vehicule = id_vehicule;

    }

    public Occupation(@Nullable Timestamp date_debut, @Nullable Timestamp date_fin, @Nullable int id_place, @Nullable int id_vehicule) {
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_place = id_place;
        this.id_vehicule = id_vehicule;

    }

    public Occupation() {
    }

    public Integer getId_occupation() {
        return id_occupation;
    }

    public void setId_occupation(Integer id_occupation) {
        this.id_occupation = id_occupation;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }

    public int getId_vehicule() {
        return id_vehicule;
    }

    public void setId_vehicule(int id_vehicule) {
        this.id_vehicule = id_vehicule;
    }


    @Override
    public String toString() {
        return "Occupation{" +
                "id_occupation=" + id_occupation +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", id_place=" + id_place +
                ", id_vehicule=" + id_vehicule +
                '}';
    }
}