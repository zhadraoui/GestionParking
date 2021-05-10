package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

public class Vehicule {

    @Nullable
    private Integer id_vehicule;
    @Nullable
    private String matricule;

    public Vehicule() {
    }

    public Vehicule(@Nullable Integer id_vehicule, @Nullable String matricule) {
        this.id_vehicule = id_vehicule;
        this.matricule = matricule;
    }

    public Vehicule(@Nullable String matricule) {
        this.matricule = matricule;
    }

    public Integer getId_vehicule() {
        return id_vehicule;
    }

    public void setId_vehicule(Integer id_vehicule) {
        this.id_vehicule = id_vehicule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }



    @Override
    public String toString() {
        return this.id_vehicule +" : " + this.matricule;
    }

    public String Afficher() {
        return "Vehicule : " +
                "id_vehicule = " + id_vehicule +
                ", matricule = " + matricule;
    }


}
