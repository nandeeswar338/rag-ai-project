package com.example.demo;

import com.google.api.services.drive.Drive;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class DriveDownloader {

    public static String downloadFile(String fileId) throws Exception {

        Drive service = DriveServiceUtil.getDriveService();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);

        String filePath = "downloaded.pdf";

        FileOutputStream file = new FileOutputStream(filePath);
        file.write(outputStream.toByteArray());
        file.close();

        return filePath;
    }
}