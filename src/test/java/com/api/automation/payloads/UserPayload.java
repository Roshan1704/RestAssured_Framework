package com.api.automation.payloads;

import com.api.automation.models.User;
import com.github.javafaker.Faker;

/**
 * Builder class for creating User payloads with test data
 */
public class UserPayload {
    
    private static final Faker faker = new Faker();
    
    /**
     * Create a user with random data
     */
    public static User createRandomUser() {
        return User.builder()
                .name(faker.name().fullName())
                .job(faker.job().title())
                .email(faker.internet().emailAddress())
                .build();
    }
    
    /**
     * Create a user with specific data
     */
    public static User createUser(String name, String job) {
        return User.builder()
                .name(name)
                .job(job)
                .build();
    }
    
    /**
     * Create a user with email
     */
    public static User createUserWithEmail(String name, String job, String email) {
        return User.builder()
                .name(name)
                .job(job)
                .email(email)
                .build();
    }
    
    /**
     * Update user data
     */
    public static User updateUser(String name, String job) {
        return User.builder()
                .name(name)
                .job(job)
                .build();
    }
}
