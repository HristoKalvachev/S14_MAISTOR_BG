package com.s14_maistorbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class S14MaistorBgApplication {

    public static void main(String[] args) {
        SpringApplication.run(S14MaistorBgApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
