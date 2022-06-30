package hu.adatb.controller;

import hu.adatb.dao.UserDao;
import hu.adatb.dao.UserDaoImpl;
import hu.adatb.model.User;

import java.util.List;

public class UserController {

    private UserDao dao = new UserDaoImpl();
    private static UserController single_instance = null;


    public static UserController getInstance(){
        if(single_instance == null){
            single_instance = new UserController();
        }
        return single_instance;
    }

    public boolean add(User user) {
        return dao.add(user);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<User> getAll() {
        return dao.getAll();
    }
}
