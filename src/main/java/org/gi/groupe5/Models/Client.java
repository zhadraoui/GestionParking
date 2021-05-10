package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

public class Client {

    @Nullable private Integer id_client;
    @Nullable private String cin;
    @Nullable private String nom;
    @Nullable private String prenom;
    @Nullable private String gsm;
    @Nullable private String adresse;
    @Nullable private String email;
    @Nullable private int id_Vehicule;
    
     public Client() {

     }

    public Client(@Nullable Integer id_client, @Nullable String cin, @Nullable String nom, @Nullable String prenom, @Nullable String gsm, @Nullable String adresse, @Nullable String email, @Nullable int id_Vehicule) {
        this.id_client = id_client;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.gsm = gsm;
        this.adresse = adresse;
        this.email = email;
        this.id_Vehicule = id_Vehicule;
    }
    public Client( @Nullable String cin, @Nullable String nom, @Nullable String prenom, @Nullable String gsm, @Nullable String adresse, @Nullable String email, @Nullable int id_Vehicule) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.gsm = gsm;
        this.adresse = adresse;
        this.email = email;
        this.id_Vehicule = id_Vehicule;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_Vehicule() {
        return id_Vehicule;
    }

    public void setId_Vehicule(int id_Vehicule) {
        this.id_Vehicule = id_Vehicule;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + id_client +
                ", cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", gsm='" + gsm + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", id_Vehicule=" + id_Vehicule +
                '}';
    }
    public String afficher() {
        return "Client{" +
                "id_client=" + id_client +
                ", cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", gsm='" + gsm + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", id_Vehicule=" + id_Vehicule +
                '}';
    }
}
