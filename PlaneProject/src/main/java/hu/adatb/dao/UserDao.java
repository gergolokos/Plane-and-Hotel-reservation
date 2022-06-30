package hu.adatb.dao;

import hu.adatb.model.User;

import java.util.List;

public interface UserDao {

    public boolean add (User user);

    public boolean delete (int id);

    public List<User> getAll();
}
