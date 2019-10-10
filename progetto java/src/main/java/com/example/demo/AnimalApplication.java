package com.example.demo;

import org.springframework.boot.SpringApplication;

import java.io.IOException;

public class AnimalApplication
{
    public static void main(String[] args)

    {
        SpringApplication.run(AnimalApplication.class, args);
        try {
            AnalisiDati Animal= new AnalisiDati();
            Animal.MarshallingJson(Animal.getDataset());
            System.out.println(Animal.LeggiJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}