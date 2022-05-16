package be.ehb.javaframeworks.examen20192020.repository;

import be.ehb.javaframeworks.examen20192020.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, String> {

    List<Person> findAllByNameContaining(String name);
}
