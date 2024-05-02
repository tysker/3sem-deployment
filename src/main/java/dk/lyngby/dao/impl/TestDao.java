package dk.lyngby.dao.impl;

import dk.lyngby.dao.CrudDao;
import dk.lyngby.model.Person;

public class TestDao extends CrudDao<Person, Integer> {

    public static TestDao instance;

    private TestDao(boolean isTest) {
        super(isTest);
    }

    public static TestDao getInstance(boolean isTest) {
        if (instance == null) {
            instance = new TestDao(isTest);
        }
        return instance;
    }

}
