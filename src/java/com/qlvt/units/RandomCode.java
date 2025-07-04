/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.units;

import java.util.Random;

/**
 *
 * @author D E L L
 */
public class RandomCode {
    
    private static final Random RANDOM = new Random();
    
     public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();


        for(int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(characters.length()); // vị trí ngẫu nhiên
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String randomStr = generateRandomString(8);
        System.out.println("Random String: " + randomStr);
    }
}
