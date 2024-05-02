package dk.lyngby.dao;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public interface Dao<T, D> {
    public List<T> getAll();
    public T get(D id);
    public T create(T t);
    public T update(D id, T t);
    public void delete(D id);
    boolean validatePrimaryKey(D d);
}
