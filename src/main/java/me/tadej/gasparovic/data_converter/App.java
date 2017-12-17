package me.tadej.gasparovic.data_converter;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

public class App {
	
    public static void main(String[] args) {
    	LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    	
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Ignore the exception; It's not fatal to the functionality of the software
		}
        
        try{
        	GlobalScreen.registerNativeHook();
        }catch(Exception e){
        	JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        
        KeyboardListener listener = new KeyboardListener();
        
        GlobalScreen.addNativeKeyListener(listener);
    }
    
}
