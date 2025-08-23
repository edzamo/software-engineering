package com.solid.principle.srp.example3.good;

public class MailService {

    public void sendEmail(User user, String msg) {
        System.out.println("Sending email to: " + user.getEmail());
        // sending email to user
        String email = user.getEmail();
        // ....
    }
}
