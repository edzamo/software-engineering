package com.solid.principle.srp.example3.good;

public class UserLogin {

    // Está haciendo dos cosas: sacar de base de datos y enviar un email
    @SuppressWarnings("unused")
    public User login(String userName, String password) {
        System.out.println("User trying to login: " + userName);
        // User user = db.findUserByUserNameAndPassword(userName, password);
        User user = User.builder()
                .email("example@example.com")
                .id(1L)
                .build();
 
        // login process..

        return user;
    }

}
