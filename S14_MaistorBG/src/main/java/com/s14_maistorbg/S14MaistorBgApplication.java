package com.s14_maistorbg;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
import org.modelmapper.ModelMapper;
>>>>>>> c656494f7f99415c848c1cf7c34090261321a502
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
<<<<<<< HEAD
=======


>>>>>>> c656494f7f99415c848c1cf7c34090261321a502
}
