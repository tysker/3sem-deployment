package dk.lyngby.controller.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.PostDao;
import dk.lyngby.dto.PostDto;
import dk.lyngby.model.Post;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PostController implements IController<Post, Integer> {

    private final PostDao dao;

    public PostController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory(false);
        this.dao = PostDao.getInstance(emf);
    }
    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        Post post = dao.get(id);
        // dto
        PostDto postDto = new PostDto(post);
        // response
        ctx.res().setStatus(200);
        ctx.json(postDto, PostDto.class);
    }

    @Override
    public void readAll(Context ctx) {
        // entity
        List<Post> posts = dao.getAll();
        // dto
        List<PostDto> postDtos = PostDto.toPostDtoList(posts);
        // response
        ctx.res().setStatus(200);
        ctx.json(postDtos, PostDto.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        Post jsonRequest = validateEntity(ctx);
        // entity
        Post post = dao.create(jsonRequest);
        // dto
        PostDto postDto = new PostDto(post);
        // response
        ctx.res().setStatus(201);
        ctx.json(postDto, PostDto.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        Post update = dao.update(id, validateEntity(ctx));
        // dto
        PostDto postDto = new PostDto(update);
        // response
        ctx.res().setStatus(200);
        ctx.json(postDto, Post.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public Post validateEntity(Context ctx) {
        return ctx.bodyValidator(Post.class)
                .check(person -> person.getHeader() != null && !person.getHeader().isEmpty(), "Name cannot be empty")
                .check(person -> person.getContent() != null && !person.getContent().isEmpty(), "Comment cannot be empty")
                .get();

    }
}
