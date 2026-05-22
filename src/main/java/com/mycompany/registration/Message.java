/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

/**
 *
 * @author Student
 */
import java.util.regex.Pattern;
import java.util.Random;

public class Message {
    
    private int messageNumber;
    private String messageId;
    private String recCell; // Remapped property signature
    private String txt;     // Remapped property signature
    
    // Constructor matching the updated naming mappings
    public Message(int messageNumber, String recCell, String txt) {
        this.messageNumber = messageNumber;
        this.recCell = recCell;
        this.txt = txt;
        this.messageId = generateMessageId();
    }
    
    String generateMessageId() {
        Random rand = new Random(); 
        long id = (long) (rand.nextDouble() * 10000000000L);
        return String.format("%010d", id);
    }
    
    public boolean checkMessageId() {
        return messageId != null && messageId.length() == 10;
    }
    
    public String createMessageHash() {
        if (txt == null || txt.trim().isEmpty()) {
            return messageId.substring(0, 2) + ":" + messageNumber + ":EMPTY";
        }
        
        String firstTwoId = messageId.substring(0, 2);
        String[] words = txt.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        
        return firstTwoId + ":" + messageNumber + ":" + firstWord + lastWord;
    }
    
    public String checkRecipientCell() {
        if (recCell != null && Pattern.matches("^\\+27[0-9]{9}$", recCell)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number incorrectly formatted or does not contain international code.";
    }
    
    public String storeMessage() {
        return "{\n" +
               "  \"messageId\": \"" + messageId + "\",\n" +
               "  \"recipient\": \"" + recCell + "\",\n" +
               "  \"messageText\": \"" + txt + "\",\n" +
               "  \"messageHash\": \"" + createMessageHash() + "\"\n" +
               "}";
    }
    
    // Remapped public getter methods
    public int getMessageNumber() { return messageNumber; }
    public String getMessageId() { return messageId; }
    public String getRecCell() { return recCell; }
    public String getTxt() { return txt; }
}
