package dk.lyngby.dao.impl;

import dk.lyngby.dao.Dao;
import dk.lyngby.model.Person;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PersonDao implements Dao<Person, Integer> {

    private static PersonDao instance;

    private static EntityManagerFactory emf;

    public static PersonDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonDao();
        }
        return instance;
    }

    @Override
    public List<Person> getAll() {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        }
    }

    @Override
    public Person get(Integer id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Person.class, id);
        }
    }

    @Override
    public Person create(Person p) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } catch (Exception e) {
            throw new IllegalArgumentException("Person already exists");
        }
    }

    @Override
    public Person update(Integer id, Person p) {
        Person merge;
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            person.setName(p.getName());
            person.setAge(p.getAge());
            merge = em.merge(person);
            em.getTransaction().commit();
        }
        return merge;
    }

    @Override
    public void delete(Integer id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (var em = emf.createEntityManager()) {
            var person = em.find(Person.class, integer);
            return person != null;
        }
    }
}
