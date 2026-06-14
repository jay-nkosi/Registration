/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

/**
 *
 * @author Student
 */
import java.util.ArrayList;

public class DataManager {

    // Parallel ArrayList structures to manage different message states
    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private final ArrayList<Message> storedMessages = new ArrayList<>();

    public DataManager() {}

    // Methods to add messages to their respective tracking folders
    public void addSentMessage(Message msg) {
        sentMessages.add(msg);
    }

    public void addDisregardedMessage(Message msg) {
        disregardedMessages.add(msg);
    }

    public void addStoredMessage(Message msg) {
        storedMessages.add(msg);
    }

    // Standard getters returning the full array structures
    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    /**
     * Requirement 1: View all unique contact cell numbers sitting in stored memory
     */
    public String getAllStoredRecipients() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        
        StringBuilder reportBuilder = new StringBuilder("--- Stored Messages Contact List ---\n");
        for (Message msg : storedMessages) {
            // Uses your remapped getRecCell() method
            reportBuilder.append("Recipient Number: ").append(msg.getRecCell()).append("\n"); 
        }
        return reportBuilder.toString();
    }

    /**
     * Requirement 2: Compare message text lengths and extract the longest string payload
     */
    public String findLongestStoredMessage() {
        ArrayList<Message> allActive = new ArrayList<>(sentMessages);
        for (Message m : storedMessages) {
            if (!allActive.contains(m)) {
                allActive.add(m);
            }
        }

        if (allActive.isEmpty()) {
            return "No messages available.";
        }
        
        Message longest = allActive.get(0);
        for (Message msg : allActive) {
            // Uses your remapped getTxt() method to check length boundaries
            if (msg.getTxt().length() > longest.getTxt().length()) { 
                longest = msg;
            }
        }
        return longest.getTxt();
    }

    /**
     * Requirement 3: Scan memory records to locate a message payload by its 10-digit ID
     */
    public String searchByMessageId(String id) {
        for (Message msg : sentMessages) {
            if (msg.getMessageId().equals(id)) return msg.getTxt();
        }
        for (Message msg : storedMessages) {
            if (msg.getMessageId().equals(id)) return msg.getTxt();
        }
        return "Message ID not found.";
    }

    /**
     * Requirement 4: Filter out and list all message text records tied to a target cell number
     */
    public String searchAllByRecipient(String recipient) {
        StringBuilder reportBuilder = new StringBuilder();
        ArrayList<Message> allMessages = new ArrayList<>(sentMessages);
        allMessages.addAll(storedMessages);

        for (Message msg : allMessages) {
            if (msg.getRecCell().equals(recipient)) {
                reportBuilder.append("- ").append(msg.getTxt()).append("\n");
            }
        }
        return reportBuilder.length() == 0 ? "No records found for this recipient." : reportBuilder.toString().trim();
    }

    /**
     * Requirement 5: Drop a specific message data record using its unique fingerprint hash key
     */
    public boolean deleteMessageByHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).createMessageHash().equalsIgnoreCase(hash)) {
                sentMessages.remove(i);
                return true;
            }
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).createMessageHash().equalsIgnoreCase(hash)) {
                storedMessages.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Requirement 6: Compile the ultimate full diagnostic system metrics summary
     */
    public String generateSystemReport() {
        if (sentMessages.isEmpty() && storedMessages.isEmpty()) {
            return "System report empty. No active data logged.";
        }
        
        StringBuilder reportBuilder = new StringBuilder("\n====================================\n SYSTEM DATA REPORT SUMMARY \n====================================\n");
        ArrayList<Message> combined = new ArrayList<>(sentMessages);
        combined.addAll(storedMessages);

        for (Message msg : combined) {
            reportBuilder.append("Hash Footprint: ").append(msg.createMessageHash()).append("\n")
                         .append("Recipient Cell: ").append(msg.getRecCell()).append("\n")
                         .append("Message Payload: ").append(msg.getTxt()).append("\n")
                         .append("------------------------------------\n");
        }
        return reportBuilder.toString();
    }
}