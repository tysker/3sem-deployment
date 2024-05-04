package dk.lyngby.dao.impl;

import dk.lyngby.dao.Dao;
import dk.lyngby.model.Post;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostDao implements Dao<Post, Integer> {

    private static PostDao instance;

    private static EntityManagerFactory emf;

    public static PostDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PostDao();
        }
        return instance;
    }

    @Override
    public List<Post> getAll() {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
        }
    }

    @Override
    public Post get(Integer id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Post.class, id);
        }
    }

    @Override
    public Post create(Post p) {
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
    public Post update(Integer id, Post p) {
        Post merge;
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Post post = em.find(Post.class, id);
            post.setHeader(p.getHeader());
            post.setContent(p.getContent());
            merge = em.merge(post);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalArgumentException("Person does not exist");
        }
        return merge;
    }

    @Override
    public void delete(Integer id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Post post = em.find(Post.class, id);
            em.remove(post);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (var em = emf.createEntityManager()) {
            var person = em.find(Post.class, integer);
            return person != null;
        }
    }
}
