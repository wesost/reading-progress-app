package com.example.grclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class GrcloneApp {
    public static void main(String[] args) {
        SpringApplication.run(GrcloneApp.class, args);
    }
}
