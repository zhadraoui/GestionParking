package org.gi.groupe5.Models;

import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Locale;

public class User {

    @Nullable private Integer id_user;
    @Nullable private String login;
    @Nullable private String pwd;
    @Nullable private Timestamp first_inscr;
    @Nullable private Timestamp last_conn;
    @Nullable private String email;
    @Nullable private String roles;

    public User() {
    }

    public User( Integer id_user, String login, String pwd, Timestamp first_inscr, Timestamp last_conn, String email, String roles) {

        this.id_user = id_user;
        this.login = login;
        this.pwd=pwd;
        this.first_inscr = first_inscr;
        this.last_conn = last_conn;
        this.email = email;
        this.roles = roles;
    }
    public User( String login, String pwd, Timestamp first_inscr, Timestamp last_conn, String email, String roles) {

        this.login = login;
        this.pwd=pwd;
        this.first_inscr = first_inscr;
        this.last_conn = last_conn;
        this.email = email;
        this.roles = roles;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Timestamp getFirst_inscr() {
        return first_inscr;
    }

    public void setFirst_inscr(Timestamp first_inscr) {
        this.first_inscr = first_inscr;
    }


    public Timestamp getLast_conn() {
        return last_conn;
    }

    public void setLast_conn(Timestamp last_conn) {
        this.last_conn = last_conn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return roles.toUpperCase(Locale.ROOT) ;
    }

    public String afficher() {
        return "User{" +
                "id_user=" + id_user +
                ", login='" + login + '\'' +
                ", pwd='" + pwd + '\'' +
                ", date premiere inscription=" + first_inscr +
                ", Date derniere connexion=" + last_conn +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
