package com.gabriel.firebase.example;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseAppConfig {

    @Value("${GOOGLE_APPLICATION_CREDENTIALS}")
    private String accountKey;

    @PostConstruct
    public void initialization() throws IOException {
        FileInputStream serviceAccount =
            new FileInputStream(accountKey);

            FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        FirebaseApp.initializeApp(options);
    }
}
