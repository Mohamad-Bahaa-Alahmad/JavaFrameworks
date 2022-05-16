package be.ehb.javaframeworks.examen20192020.service;

import be.ehb.javaframeworks.examen20192020.model.Person;
import be.ehb.javaframeworks.examen20192020.repository.PersonDao;
import be.ehb.javaframeworks.examen20192020.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonDao personDao;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonDao personDao) {
        this.personRepository = personRepository;
        this.personDao = personDao;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(String id) {
        return personRepository.findById(id);
    }

    public List<Person> findAllWhereNameContains(String searchString) {
        return personRepository.findAllByNameContaining(searchString);
    }

    public void save(Person person) throws SQLException {
        personDao.save(person);
    }
}
