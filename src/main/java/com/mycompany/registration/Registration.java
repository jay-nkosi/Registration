/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registration;

/**
 *
 * @author Student
 */
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class Registration {
    
    private static int sentCount = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("--- USER REGISTRATION ---");
        System.out.print("Enter Username: ");
        String username = input.nextLine();
        
        System.out.print("Enter Password: ");
        String password = input.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = input.nextLine();

        System.out.println("\n--- VALIDATION RESULTS ---");
        
        if (Login.checkUserName(username)) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted.");
        }

        if (Login.checkPasswordComplexity(password)) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted.");
        }

        if (Login.checkCellPhoneNumber(phone)) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number incorrectly formatted.");
        }

        String regResult = Login.registerUser(username, password, phone);
        System.out.println("\nFinal Status: " + regResult);

        // Validates alignment to match your exact success message criteria
        if (regResult.equals("User is successfully registered")) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Username: ");
            String loginUser = input.nextLine();
            System.out.print("Password: ");
            String loginPass = input.nextLine();
            
            String loginStatus = Login.returnLoginStatus(loginUser, loginPass);
            System.out.println(loginStatus);
            
            if (loginStatus.equals("A successful login")) {
                System.out.println("\nWelcome to QuickChat.");
                showMenu(input);
            }
        }

        input.close();
    }
    
    private static void showMenu(Scanner input) {
        int choice;
        do {
            System.out.println("\n=== QUICKCHAT MENU ===");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Logout");
            System.out.print("Choose option: ");
            choice = input.nextInt();
            input.nextLine(); // Clear scanner memory line buffer

            if (choice == 1) {
                processMessages(input);
            } else if (choice == 2) {
                System.out.println("Coming Soon...");
            }
        } while (choice != 3);
    }
    
    private static void processMessages(Scanner input) {
        sentCount = 0; // Initialize loop session metric counter safely
        
        System.out.print("\nHow many messages do you want to send? ");
        int count = input.nextInt();
        input.nextLine(); // Clear line buffer
        
        for (int i = 1; i <= count; i++) {
            System.out.println("\n--- Message " + i + " ---");
            System.out.print("Enter recipient(+27): ");
            String phone = input.nextLine();
            System.out.print("Enter message text: ");
            String text = input.nextLine();
            
            if (text == null) text = "";
            
            Message msg = new Message(i, phone, text);
            
            // Runs verification directly through your specific Login class parameters
            if (Login.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully captured.");
                System.out.println("\nSelect an action:");
                System.out.println("1. Send");
                System.out.println("2. Disregard");
                System.out.println("3. Store");
                System.out.print("Choice: ");
                
                int action = input.nextInt();
                input.nextLine(); // Clear line buffer
                
                if (action == 1) {
                    sentCount++;
                    System.out.println("Message Sent");
                    System.out.println("ID: " + msg.generateMessageId());
                    System.out.println("Hash: " + msg.createMessageHash());
                    System.out.println("Recipient: " + msg.checkRecipientCell());
                    System.out.println("Message: " + msg.checkMessageId());
                } else if (action == 2) {
                    System.out.println("Press 0 to delete the message.");
                } else if (action == 3) {
                    sentCount++;
                    saveJsonToFile(msg.storeMessage());
                }
            } else {
                System.out.println("Cell phone number incorrectly formatted.");
            }
        }
        System.out.println("\nTotal messages sent in this session: " + sentCount);
    }
    
    private static void saveJsonToFile(String jsonContent) {
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(jsonContent + "\n");
            System.out.println("Message successfully stored.");
        } catch (IOException e) {
            System.out.println("Error storing JSON: " + e.getMessage());
        }
    }
}