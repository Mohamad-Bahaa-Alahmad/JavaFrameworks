package be.ehb.javaframeworks.examen20192020.repository;

import be.ehb.javaframeworks.examen20192020.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@PropertySource("application.properties")
public class PersonDao {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public void save(Person person) throws SQLException {
        try (Connection c = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = c.prepareStatement("INSERT into persons(auction_person_number, name, email_address) VALUES (?,?,?)")) {

            ps.setString(1, person.getAuctionPersonNumber());
            ps.setString(2, person.getName());
            ps.setString(3, person.getEmailAddress());

            ps.executeUpdate();
        }
    }
}
