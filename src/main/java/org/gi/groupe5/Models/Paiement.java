package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;

public class Paiement {

    @Nullable
    private Integer id_paiement;
    @Nullable
    private Integer id_occupation;
    @Nullable
    private Timestamp date_paiement;
    @Nullable
    private Float duree;
    @Nullable
    private Float montant;

    public Paiement() {
    }

    public Paiement(@Nullable Integer id_occupation) {
        this.id_occupation = id_occupation;
    }

    public Paiement(@Nullable Integer id_paiement, @Nullable Integer id_occupation, @Nullable Timestamp date_paiement, @Nullable Float duree, @Nullable Float montant) {
        this.id_paiement = id_paiement;
        this.id_occupation = id_occupation;
        this.date_paiement = date_paiement;
        this.duree = duree;
        this.montant = montant;
    }

    public Integer getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(Integer id_paiement) {
        this.id_paiement = id_paiement;
    }

    public Integer getId_occupation() {
        return id_occupation;
    }

    public void setId_occupation(Integer id_occupation) {
        this.id_occupation = id_occupation;
    }

    public Timestamp getDate_paiement() {
        return date_paiement;
    }

    public void setDate_paiement(Timestamp date_paiement) {
        this.date_paiement = date_paiement;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float duree) {
        this.duree = duree;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id_paiement=" + id_paiement +
                ", id_occupation=" + id_occupation +
                ", date_paiement=" + date_paiement +
                ", duree=" + duree +
                ", montant=" + montant +
                '}';
    }

    public String afficher() {
        return "Paiement{" +
                "id_paiement=" + id_paiement +
                ", id_occupation=" + id_occupation +
                ", date_paiement=" + date_paiement +
                ", duree=" + duree +
                ", montant=" + montant +
                '}';
    }
}
