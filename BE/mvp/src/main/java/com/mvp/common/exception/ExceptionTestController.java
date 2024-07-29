package com.mvp.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class ExceptionTestController {


    @GetMapping("/{var}")
    public String getProduct(@PathVariable(name = "var")String var){
        System.out.println("ExceptionTestController.getProduct");
        if(var.equals("error")){
            throw new NoSuchElementException();
        }

        return "OK";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}