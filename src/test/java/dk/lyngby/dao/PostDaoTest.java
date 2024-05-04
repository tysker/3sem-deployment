package dk.lyngby.dao;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.impl.PostDao;
import dk.lyngby.model.Post;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // https://www.baeldung.com/junit-testinstance-annotation
class PostDaoTest {

    private static EntityManagerFactory emf;
    private static PostDao personDao;

    private final Post p1 = new Post("Post Header 1", "Post Content 1");
    private final Post p2 = new Post("Post Header 2", "Post Content 2");
    private final Post p3 = new Post("Post Header 3", "Post Content 3");


    @BeforeEach
    public void init() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Post").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        }
    }

    @BeforeAll
    public static void setUp() {
        emf = HibernateConfig.getEntityManagerFactory(true);
        personDao = PostDao.getInstance(emf);
    }

    @Test
    @DisplayName("Get all persons")
    void getAll() {
        // given
        int expected = 3;
        // when
        int actual = personDao.getAll().size();
        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get person by id")
    void get() {
        // given
        int id = p1.getId();
        // when
        Post actual = personDao.get(id);
        // then
        Assertions.assertEquals(p1, actual);

    }

    @Test
    @DisplayName("Create person")
    void create() {
        // given
        Post expected = new Post("Post Header 4", "Post Content 4");
        // when
        personDao.create(expected);
        // then
        Assertions.assertTrue(expected.getId() > 0);
    }

    @Test
    @DisplayName("Update Person 2 to Person 100")
    void update() {
        // given
        Post expected = new Post("Post Header 2", "Post Content 2");
        expected.setId(p2.getId());
        // when
        Post actual = personDao.update(p2.getId(), expected);
        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete Person 3")
    void delete() {
        // given
        int id = p3.getId();
        // when
        personDao.delete(id);
        // then
        Assertions.assertNull(personDao.get(id));

    }
}