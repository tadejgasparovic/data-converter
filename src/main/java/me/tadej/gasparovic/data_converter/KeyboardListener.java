package me.tadej.gasparovic.data_converter;

import java.util.HashMap;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListener implements NativeKeyListener {

	private HashMap<Integer, Boolean> keyStatus;
	
	private boolean converter_open = false;
	private boolean from_selected = false;
	
	public KeyboardListener(){
		keyStatus = new HashMap<Integer, Boolean>();
	}
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_ESCAPE){
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
		
		if(keyStatus.containsKey(e.getKeyCode()) && keyStatus.get(e.getKeyCode())){
			return;
		}
		
		keyStatus.put(e.getKeyCode(), true);
		
		if(converter_open){
			switch(e.getKeyCode()){
				case NativeKeyEvent.VC_B:
					if(from_selected){
						Converter.convertTo(Converter.BINARY);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.BINARY);
						from_selected = true;
					}
					break;
					
				case NativeKeyEvent.VC_D:
					if(from_selected){
						Converter.convertTo(Converter.DECIMAL);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.DECIMAL);
						from_selected = true;
					}
					break;
					
				case NativeKeyEvent.VC_H:
					if(from_selected){
						Converter.convertTo(Converter.HEXADECIMAL);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.HEXADECIMAL);
						from_selected = true;
					}
					break;
	
				case NativeKeyEvent.VC_O:
					if(from_selected){
						Converter.convertTo(Converter.OCTAL);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.OCTAL);
						from_selected = true;
					}
					break;
					
				case NativeKeyEvent.VC_G:
					if(from_selected){
						Converter.convertTo(Converter.GRAY);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.GRAY);
						from_selected = true;
					}
					break;
					
				case NativeKeyEvent.VC_C:
					if(from_selected){
						Converter.convertTo(Converter.BCD);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.BCD);
						from_selected = true;
					}
					break;
					
				case NativeKeyEvent.VC_I:
					if(from_selected){
						Converter.convertTo(Converter.XS3);
						Converter.convertAndPut();
						converter_open = false;
					}else{
						Converter.convertFrom(Converter.XS3);
						from_selected = true;
					}
					break;
					
				case NativeKeyEvent.VC_P:
					Converter.loadAndSetPrecision();
					Converter.putValue("Precision set to: " + Converter.getPrecision());
					converter_open = false;
					break;
					
				default:
					Converter.putValue("Error: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
					converter_open = false;
					break;
			}
		}
		
		if(e.getKeyCode() == NativeKeyEvent.VC_ALT){ // If Alt is pressed activate the converter app
			from_selected = false;
			
			try {
				Converter.loadValue();
				converter_open = true;
			} catch (Exception e1) {
				Converter.putValue("Exception: " + e1.getMessage());
			}
		}
		
		System.out.println("Converter Open: " + converter_open);
		System.out.println("From Selected: " + from_selected);
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		keyStatus.put(e.getKeyCode(), false);
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}
	
	public boolean isKeyDown(int key){
		return keyStatus.containsKey(key) ? keyStatus.get(key) : false;
	}

}
