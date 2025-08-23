package com.solid.principle.srp;


import com.solid.principle.srp.example1.good.TextPrinter;
import com.solid.principle.srp.example3.good.MailService;
import com.solid.principle.srp.example3.good.User;
import com.solid.principle.srp.example3.good.UserLogin;

public class SrpMain {

    public static void main() {

        System.out.println("Solid Principles - SRP");
        UserLogin userLogin = new UserLogin();
        // responsabilidad 1
        User user = userLogin.login("alan", "admin");
        System.out.println("User logged in: " + user);
        MailService mailService = new MailService();
        // responsabilidad 2
        mailService.sendEmail(user, "Inicio de sesión desde nuevo dispositivo.");

        System.out.println("Example 3");
        TextPrinter textPrinter = new TextPrinter("Hola, este es un texto de ejemplo.");
        textPrinter.printText();

    }
}
