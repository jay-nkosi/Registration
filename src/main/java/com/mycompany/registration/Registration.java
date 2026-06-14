/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registration;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Registration {
    
    private static int sentCount = 0;
    // Instantiate Part 3 storage system tracking arrays
    private static final DataManager dataManager = new DataManager();

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
            System.out.println("3) Data Management & System Reports"); // Restored Part 3 hook
            System.out.println("4) Logout"); 
            System.out.print("Choose option: ");
            
            while (!input.hasNextInt()) {
                System.out.println("Please enter a numeric option choice.");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine(); 

            if (choice == 1) {
                processMessages(input);
            } else if (choice == 2) {
                System.out.println("Coming Soon...");
            } else if (choice == 3) {
                showDataManagementMenu(input); // Open Sub-menu
            }
        } while (choice != 4); // Fixed to loop until option 4 logout
    }
    
    private static void processMessages(Scanner input) {
        sentCount = 0; 
        
        System.out.print("\nHow many messages do you want to send? ");
        int count = input.nextInt();
        input.nextLine(); 
        
        for (int i = 1; i <= count; i++) {
            System.out.println("\n--- Message " + i + " ---");
            System.out.print("Enter recipient(+27): ");
            String phone = input.nextLine();
            System.out.print("Enter message text: ");
            String text = input.nextLine();
            
            if (text == null) text = "";
            
            Message msg = new Message(i, phone, text);
            
            // Step A: Character Length Constraint Validation Filter
            String lengthCheck = msg.checkMessageLength();
            if (lengthCheck.contains("exceeds")) {
                System.out.println(lengthCheck);
                continue; // Cancel current index sequence and move to next message
            }
            
            // Runs verification directly through your explicit Login parameters
            if (Login.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully captured.");
                System.out.println("\nSelect an action:");
                System.out.println("1. Send");
                System.out.println("2. Disregard");
                System.out.println("3. Store");
                System.out.print("Choice: ");
                
                int action = input.nextInt();
                input.nextLine(); 
                
                if (action == 1) {
                    sentCount++;
                    dataManager.addSentMessage(msg); // Push object reference directly into Tracking Arrays
                    System.out.println("Message Sent");
                    System.out.println("ID: " + msg.getMessageId());
                    System.out.println("Hash: " + msg.createMessageHash());
                    System.out.println("Recipient: " + msg.getRecCell()); // Fixed: outputs value, not validation text
                    System.out.println("Message: " + msg.getTxt());       // Fixed: outputs value, not validation text
                } else if (action == 2) {
                    dataManager.addDisregardedMessage(msg); // Push to tracking data arrays
                    System.out.println("Press 0 to delete the message.");
                    int confirmDelete = input.nextInt();
                    input.nextLine();
                    if (confirmDelete == 0) {
                        System.out.println("Message successfully cleared.");
                    }
                } else if (action == 3) {
                    sentCount++;
                    dataManager.addStoredMessage(msg); // Push to tracking data arrays
                    saveJsonToFile(msg.storeMessage());
                }
            } else {
                System.out.println("Cell phone number incorrectly formatted.");
            }
        }
        System.out.println("\nTotal messages sent in this session: " + sentCount);
    }
    
    private static void showDataManagementMenu(Scanner input) {
        int subChoice;
        do {
            System.out.println("\n=== SYSTEM DATA REPORT MANAGEMENT ===");
            System.out.println("1. View All Stored Recipients");
            System.out.println("2. Display Longest Message Text");
            System.out.println("3. Search Message Content by ID");
            System.out.println("4. Search All Messages by Recipient");
            System.out.println("5. Delete Message Using Footprint Hash");
            System.out.println("6. Run Full System Diagnostic Report");
            System.out.println("7. Return to QuickChat Menu");
            System.out.print("Enter Selection: ");
            
            subChoice = input.nextInt(); 
            input.nextLine();
            
            switch (subChoice) {
                case 1:
                    System.out.println(dataManager.getAllStoredRecipients());
                    break;
                case 2:
                    System.out.println("Longest Logged Message Content: \n\"" + dataManager.findLongestStoredMessage() + "\"");
                    break;
                case 3:
                    System.out.print("Enter Message ID to scan: ");
                    System.out.println("Content Result: " + dataManager.searchByMessageId(input.nextLine()));
                    break;
                case 4:
                    System.out.print("Enter Recipient Cell Number to query (+27...): ");
                    System.out.println("Logged Content matches:\n" + dataManager.searchAllByRecipient(input.nextLine()));
                    break;
                case 5:
                    System.out.print("Enter Message Hash to destroy: ");
                    if (dataManager.deleteMessageByHash(input.nextLine())) {
                        System.out.println("Entry matching footprint wiped from record storage data tables.");
                    } else {
                        System.out.println("Hash target key not found.");
                    }
                    break;
                case 6:
                    System.out.println(dataManager.generateSystemReport());
                    break;
            }
        } while (subChoice != 7);
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