package dk.lyngby.dto;

import dk.lyngby.model.Person;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PersonDto {

    private int id;
    private String name;
    private int age;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.age = person.getAge();
    }

    public static List<PersonDto> toPersonDtoList(List<Person> hotels) {
        return hotels.stream().map(PersonDto::new).collect(Collectors.toList());
    }
}
