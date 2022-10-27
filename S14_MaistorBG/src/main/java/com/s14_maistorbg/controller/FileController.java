package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileController extends ExceptionController {

    @GetMapping("/images/{pathFile}")
    @ResponseStatus(code = HttpStatus.OK)
    public void getImage(@PathVariable String pathFile, HttpServletResponse response) {
        File file = new File("images" + File.separator + pathFile);
        if (!file.exists()) {
            throw new NotFoundException("File does not exist!");
        }
        try {
            response.setContentType(Files.probeContentType(file.toPath()));
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException e) {
            throw new BadRequestException("Invalid content type!");
        }
    }
}
