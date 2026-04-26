package com.example.demo;

import java.io.InputStream;
import java.util.Collections;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

public class DriveServiceUtil {

    private static final String APPLICATION_NAME = "RAG APP";

    public static Drive getDriveService() throws Exception {

        InputStream in = DriveServiceUtil.class
                .getClassLoader()
                .getResourceAsStream("credentials.json"); // your JSON file name

        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive.readonly"));

        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Drive.Builder(
                httpTransport,
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
