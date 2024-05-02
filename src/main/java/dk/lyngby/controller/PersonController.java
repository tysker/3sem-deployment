package dk.lyngby.controller;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.impl.PersonDao;
import dk.lyngby.dto.PersonDto;
import dk.lyngby.model.Person;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PersonController implements IController<Person, Integer> {

    private final PersonDao dao;

    public PersonController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory(false);
        this.dao = PersonDao.getInstance(emf);
    }
    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        Person person = dao.get(id);
        // dto
        PersonDto personDto = new PersonDto(person);
        // response
        ctx.res().setStatus(200);
        ctx.json(personDto, PersonDto.class);
    }

    @Override
    public void readAll(Context ctx) {
        // entity
        List<Person> hotels = dao.getAll();
        // dto
        List<PersonDto> personDtos = PersonDto.toPersonDtoList(hotels);
        // response
        ctx.res().setStatus(200);
        ctx.json(personDtos, PersonDto.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        Person jsonRequest = validateEntity(ctx);
        // entity
        Person person = dao.create(jsonRequest);
        // dto
        PersonDto hotelDto = new PersonDto(person);
        // response
        ctx.res().setStatus(201);
        ctx.json(hotelDto, PersonDto.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        Person update = dao.update(id, validateEntity(ctx));
        // dto
        PersonDto hotelDto = new PersonDto(update);
        // response
        ctx.res().setStatus(200);
        ctx.json(hotelDto, Person.class);
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
    public Person validateEntity(Context ctx) {
        return ctx.bodyValidator(Person.class)
                .check(person -> person.getName() != null && !person.getName().isEmpty(), "Name cannot be empty")
                .check(person -> person.getAge() > 0, "Age must be greater than 0")
                .get();

    }
}
