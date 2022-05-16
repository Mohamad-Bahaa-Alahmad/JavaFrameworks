package be.ehb.javaframeworks.examen20192020.controller;

import be.ehb.javaframeworks.examen20192020.model.Person;
import be.ehb.javaframeworks.examen20192020.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    List<Person> all(@RequestParam(name = "search", required = false) String searchString) {
        if (searchString != null) {
            return personService.findAllWhereNameContains(searchString);
        }
        
        return personService.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Person> byId(@PathVariable("id") String id) {
        return ResponseEntity.of(personService.findById(id));
    }

    @PostMapping
    void create(@Valid @RequestBody Person person) throws SQLException {
        personService.save(person);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> validationErrorHandler(MethodArgumentNotValidException exception) {
        String validationMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(validationMessage, HttpStatus.BAD_REQUEST);
    }
}
