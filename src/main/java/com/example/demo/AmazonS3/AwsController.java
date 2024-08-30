package com.example.demo.AmazonS3;

import com.example.demo.AmazonS3.Services.AwsService;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;

@RestController
public class AwsController {

    @Autowired
    private AwsService service;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // Endpoint to list files in a bucket
    @GetMapping("/list")
    public ResponseEntity<?> listFiles(
    ) {
        val body = service.listFiles(bucketName);
        return ResponseEntity.ok(body);
    }

    // Endpoint to upload a file to a bucket
    @SneakyThrows()
    public boolean uploadFile(byte[] file, String fileName) {
        if (file == null || file.length == 0) {
            return false;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
        service.uploadFile(bucketName, fileName, (long) file.length, "b64", inputStream);

        return true;
    }


    // Endpoint to download a file from a bucket
    @SneakyThrows()
    public String downloadFile(String fileName)  {
        val body = service.downloadFile(bucketName, fileName);
        return body.toString();
    }

    // Endpoint to delete a file from a bucket
    @DeleteMapping("/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable("fileName") String fileName
    ) {

        service.deleteFile(bucketName, fileName);
        return ResponseEntity.ok().build();
    }
}
