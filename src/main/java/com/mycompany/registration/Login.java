/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

/**
 *
 * @author Student
 */
public class Login {

    public Login() {
    }

    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() == 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        String capital = ".*[A-Z].*";
        String small = ".*[a-z].*";
        String special = ".*[!@#$%^&*(),.?\":{}|<>].*";
        String digit = ".*\\d.*";
        
        return password.length() >= 8 && password.matches(capital) 
               && password.matches(small) && password.matches(digit) 
               && password.matches(special);
    }

    public static boolean checkCellPhoneNumber(String phone) {
        if (phone.length() < 4) return false;
        String saCode = "+27";
        String firstThreeChars = phone.substring(0, 3);
        int fourthDigit = Character.getNumericValue(phone.charAt(3));
        
        return phone.length() <= 12 && firstThreeChars.equals(saCode) 
               && fourthDigit >= 6 && fourthDigit <= 8;
    }

    public static String registerUser(String username, String password, String phone) {
        if (checkCellPhoneNumber(phone) && checkUserName(username) && checkPasswordComplexity(password)) {
            return "User is successfully registered";
        } else {
            return "User registration failed!!!!!";
        }
    }

    public static String returnLoginStatus(String username, String password) {
        if (checkUserName(username) && checkPasswordComplexity(password)) {
            return "A successful login";
        } else {
            return "User or Password incorrect,please try again";
        }
    }
}
