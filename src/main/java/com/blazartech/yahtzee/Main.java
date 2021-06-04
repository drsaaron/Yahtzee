/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.yahtzee;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 *
 * @author aar1069
 */
@SpringBootApplication
public class Main {
    
    public static void main(String... args) {
        new SpringApplicationBuilder(Main.class).headless(false).run(args);
    }
}
