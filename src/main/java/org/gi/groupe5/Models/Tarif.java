package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

public  class Tarif  {
    @Nullable private Integer id_tarif;
    @Nullable private Integer heure_debut;
    @Nullable private Integer heure_fin;
    @Nullable private Float prix;

    public Tarif(){ }

    public Tarif(@Nullable Integer heure_debut, @Nullable Integer heure_fin, @Nullable Float prix) {
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.prix = prix;
    }

    public Tarif(@Nullable Integer id_tarif, @Nullable Integer heure_debut, @Nullable Integer heure_fin, @Nullable Float prix) {
        this.id_tarif = id_tarif;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.prix = prix;
    }

    public Integer getId_tarif() {
        return id_tarif;
    }

    public void setId_tarif(Integer id_tarif) {
        this.id_tarif = id_tarif;
    }

    public Integer getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Integer heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Integer getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Integer heure_fin) {
        this.heure_fin = heure_fin;
    }



    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return
                "de " + heure_debut +
                "à " + heure_fin +
                " Prix =" + prix ;
    }

    public String afficher() {
        return "Tarif{" +
                "id_tarif=" + id_tarif +
                ", heure_debut=" + heure_debut +
                ", heure_fin=" + heure_fin +
                ", prix=" + prix +
                '}';
    }
}
