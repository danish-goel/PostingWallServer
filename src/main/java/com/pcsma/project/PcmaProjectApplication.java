package com.pcsma.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pcsma.project.classes.Location;
import com.pcsma.project.repository.LocationRepository;
import com.pcsma.project.repository.PostRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {PostRepository.class, LocationRepository.class})
public class PcmaProjectApplication 
{

    public static void main(String[] args) 
    {
        SpringApplication.run(PcmaProjectApplication.class, args);
    }
}
