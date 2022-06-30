package hu.adatb.controller;

import hu.adatb.dao.CategoryDao;
import hu.adatb.dao.CategoryDaoImpl;
import hu.adatb.model.Category;

import java.util.List;

public class CategoryController {
    private CategoryDao dao = new CategoryDaoImpl();
    private static CategoryController single_instance = null;


    public static CategoryController getInstance(){
        if(single_instance == null){
            single_instance = new CategoryController();
        }
        return single_instance;
    }

    public List<Category> getAll() {
        return dao.getAll();
    }
}
