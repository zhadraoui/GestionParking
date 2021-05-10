package org.gi.groupe5.dao;

import javafx.collections.ObservableList;
import org.gi.groupe5.Models.Place;
import org.gi.groupe5.Models.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void update(User user);
    void delete(Integer iduser);
    void updateDateLastConnection(String login);
    User getUser(String key, String value);
    List<User> userList();
    boolean auth(String login, String password);
    ObservableList<User> getObservableList();
    Integer getNewID();
    public boolean update_password(String login,String oldpassword,String newpassword);
}
