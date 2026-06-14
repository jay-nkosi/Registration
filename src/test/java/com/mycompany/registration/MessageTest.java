/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    public MessageTest() {
    }
 
    @Test
    public void testRecipientFormatSuccess() {
        Message msg = new Message(0, "+27718693002", "Test");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFormatFailure() {
        Message msg = new Message(0, "0718693002", "Test");
        assertTrue(msg.checkRecipientCell().contains("incorrectly formatted"));
    }
    
    @Test
    public void testMessageLengthSuccess() {
        // Testing a valid message length
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // Creating a message text that is exactly 251 characters long
        String overLimitText = "a".repeat(255);
        Message msg = new Message(0, "+27718693002", overLimitText);
        
        String expectedErrorMessage = "Message exceeds 250 characters by 5, please reduce the size.";
        assertEquals(expectedErrorMessage, msg.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        
        Message msg = new Message(0, "+27718693002", "Hi Tonight");
        String hash = msg.createMessageHash();
        assertTrue(hash.endsWith(":0:HITONIGHT"));
    }

    @Test
    public void testCheckMessageLength() {
        System.out.println("Testing: checkMessageLength");
        
        
        Message safeMessage = new Message(1, "+27834557896", "Did you get the cake?");
        String safeResult = safeMessage.checkMessageLength();
        assertEquals("Message ready to send.", safeResult);
        
        
        String longText = "This text is deliberately repeated to overflow the character buffer limit "
                        + "established by the assignment rubric constraints. It needs to go way past "
                        + "two hundred and fifty total characters to prove that our validation engine "
                        + "accurately flags long text strings. Let's keep adding more words to make "
                        + "absolutely sure it breaks the barrier!";
        
        Message longMessage = new Message(2, "+27838884567", longText);
        String excessResult = longMessage.checkMessageLength();
        
        
        assertTrue(excessResult.contains("exceeds"));
    }

   
    @Test
    public void testCheckMessageId() {
        System.out.println("Testing: checkMessageId");
        Message msg = new Message(1, "+27834557896", "Hello");
        
        
        assertTrue(msg.checkMessageId());
        assertNotNull(msg.getMessageId());
        assertEquals(10, msg.getMessageId().length());
    }

  
    @Test
    public void testCreateMessageHash() {
        System.out.println("Testing: createMessageHash");
        
      
        Message validMsg = new Message(1, "+27834557896", "Did you get the cake?");
        String hash = validMsg.createMessageHash();
        String firstTwoId = validMsg.getMessageId().substring(0, 2);
        
        
        String expectedHash = firstTwoId + ":1:DIDCAKE?";
        assertEquals(expectedHash, hash);

        
        Message emptyMsg = new Message(3, "+27834484567", "   ");
        String emptyHash = emptyMsg.createMessageHash();
        String expectedEmptyHash = emptyMsg.getMessageId().substring(0, 2) + ":3:EMPTY";
        assertEquals(expectedEmptyHash, emptyHash);
    }

    
    @Test
    public void testCheckRecipientCell() {
        System.out.println("Testing: checkRecipientCell");
        
        Message validPhoneMsg = new Message(1, "+27834557896", "Valid format test.");
        String passingResult = validPhoneMsg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", passingResult);
        
        Message invalidPhoneMsg = new Message(4, "038884567", "Invalid format test.");
        String failingResult = invalidPhoneMsg.checkRecipientCell();
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", failingResult);
    }
}
    

