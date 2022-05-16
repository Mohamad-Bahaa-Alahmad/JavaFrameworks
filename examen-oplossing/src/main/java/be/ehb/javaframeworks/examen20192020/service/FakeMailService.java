package be.ehb.javaframeworks.examen20192020.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeMailService implements MailService {

    private final List<String> emails = new ArrayList<>();

    @Override
    public void sendEmail(String message, String email) {
        emails.add(email);
    }

    public List<String> getEmails() {
        return emails;
    }
}
