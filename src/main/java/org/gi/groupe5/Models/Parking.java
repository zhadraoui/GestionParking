package org.gi.groupe5.Models;


import org.jetbrains.annotations.Nullable;

public class Parking {

    @Nullable
    private Integer id_parking;
    @Nullable
    private String adresse;
    @Nullable
    private Integer capacite;



    public Parking() {

    }

    public Parking(Integer id_parking, String adresse, Integer capacite) {
        this.id_parking = id_parking;
        this.adresse = adresse;
        this.capacite = capacite;
    }
    public Parking( String adresse, Integer capacite) {
        this.adresse = adresse;
        this.capacite = capacite;
    }

    public Integer getId_parking() {
        return id_parking;
    }

    public void setId_parking(Integer id_parking) {

        this.id_parking = id_parking;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }



    @Override
    public String toString() {
        return id_parking + " : " + adresse;
    }
    public String afficher() {
        return "Parking{" +
                "id_parking=" + id_parking +
                ", adresse='" + adresse + '\'' +
                ", capacite=" + capacite +
                '}';
    }

}
