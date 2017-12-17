package me.tadej.gasparovic.data_converter;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class Converter {

	public static final int BINARY = 2;
	public static final int DECIMAL = 10;
	public static final int HEXADECIMAL = 16;
	public static final int OCTAL = 8;
	
	public static final int GRAY = 1;
	public static final int BCD = 4;
	public static final int XS3 = 3;
	
	private static String value;
	
	private static int precision = 5;
	
	private static int convert_from;
	private static int convert_to;
	
	public static void loadValue() throws HeadlessException, UnsupportedFlavorException, IOException{
		value = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	}
	
	public static void putValue(String value){
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
	}
	
	public static void convertFrom(int from){
		Converter.convert_from = from;
	}
	
	public static void convertTo(int to){
		Converter.convert_to = to;
	}
	
	public static String convert(String value, int from, int to, int precision) throws Exception {
		if(!value.contains(",")){
			return Long.toString(Integer.valueOf(value, from), to);
		}
		
		String[] parts = value.trim().split(",");
		
		double number = (double)Long.valueOf(parts[0] + parts[1], from) * Math.pow(from, (double)-parts[1].length());
		
		String s = Long.toString((int)Math.round(number / Math.pow(to, -precision)), to);

		return s.substring(0, s.length() - precision) + "," + s.substring(s.length() - precision);
	}
	
	public static int fromGray(String value){
		int num = Integer.valueOf(value, 2);
		int mask = num;
		
		while(mask != 0){
			mask = mask >> 1;
			num ^= mask;
		}
		
		return num;
	}
	
	public static String toGray(String value){
		int num = Integer.parseInt(value, 2);
		
		num ^= (num >> 1);
		
		String ret = Integer.toString(num, 2);
		
		int size = 4 - (ret.length() % 4) + ret.length();
		
		return StringUtils.leftPad(ret, size);
	}
	
	public static String toBCD(String value){
		
		value = Double.toString(Double.parseDouble(value.replace(',', '.'))).replace('.', ',');
		
		String[] parts = value.split(",");
		
		String result = "";
		
		int j = 0;
		
		for(String part : parts){
			
			char[] digits = part.toCharArray();
			
			for(char digit : digits){
				result += StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(new String(new char[]{digit}))), 4, '0');
			}
			
			if(parts.length > 1 && j == 0){
				result += ",";
			}
			
			j++;
		}
		
		return result;
	}
	
	public static String fromBCD(String value){
		String result = "";
		
		int size = 4 - (value.length() - ((value.contains(",") ? 1 : 0)) % 4) + value.length();
		
		value = StringUtils.leftPad(value, size, '0');
		
		String[] parts = value.split(",");
		
		int j = 0;
		
		for(String part : parts){
			
			for(int i = 0; i < part.length(); i += 4){
				result += Integer.toString(Integer.parseInt(part.substring(i, i + 4), 2));
			}
			
			if(parts.length > 1 && j == 0){
				result += ".";
			}
			
			j++;
		}
		
		return Double.toString(Double.parseDouble(result)).replace('.', ',');
	}
	
	public static String toXS3(String value){
		
		value = Double.toString(Double.parseDouble(value.replace(',', '.'))).replace('.', ',');
		
		String[] parts = value.split(",");
		
		String result = "";
		
		int j = 0;
		
		for(String part : parts){
			
			char[] digits = part.toCharArray();
			
			for(char digit : digits){
				result += StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(new String(new char[]{digit})) + 3), 4, '0');
			}
			
			if(parts.length > 1 && j == 0){
				result += ",";
			}
			
			j++;
		}
		
		return result;
	}
	
	public static String fromXS3(String value){
		String result = "";
		
		int size = 4 - (value.length() - ((value.contains(",") ? 1 : 0)) % 4) + value.length();
		
		value = StringUtils.leftPad(value, size, '0');
		
		String[] parts = value.split(",");
		
		int j = 0;
		
		for(String part : parts){
			
			for(int i = 0; i < part.length(); i += 4){
				result += Integer.toString(Integer.parseInt(part.substring(i, i + 4), 2) - 3);
			}
			
			if(parts.length > 1 && j == 0){
				result += ".";
			}
			
			j++;
		}
		
		return Double.toString(Double.parseDouble(result)).replace('.', ',');
	}
	
	public static void convertAndPut() {
		if(convert_from == GRAY){
			int bin = Converter.fromGray(value);
			Converter.putValue(Integer.toString(bin, convert_to));
			return;
		}else if(convert_to == GRAY){
			try {
				String bin = Converter.convert(value, convert_from, BINARY, 0);
				Converter.putValue(Converter.toGray(bin));
			} catch (Exception e) {
				Converter.putValue("Exception: " + e.getMessage());
			}
			return;
		}
		
		if(convert_from == BCD){
			String dec = Converter.fromBCD(value);
			try {
				Converter.putValue(Converter.convert(dec, DECIMAL, convert_to, precision));
			} catch (Exception e) {
				Converter.putValue("Exception: " + e.getMessage());
			}
			return;
		}else if(convert_to == BCD){
			try {
				String dec = Converter.convert(value, convert_from, DECIMAL, precision);
				Converter.putValue(Converter.toBCD(dec));
			} catch (Exception e) {
				Converter.putValue("Exception: " + e.getMessage());
			}
			return;
		}
		
		if(convert_from == XS3){
			String dec = Converter.fromXS3(value);
			try {
				Converter.putValue(Converter.convert(dec, DECIMAL, convert_to, precision));
			} catch (Exception e) {
				Converter.putValue("Exception: " + e.getMessage());
			}
			return;
		}else if(convert_to == XS3){
			try {
				String dec = Converter.convert(value, convert_from, DECIMAL, precision);
				Converter.putValue(Converter.toXS3(dec));
			} catch (Exception e) {
				Converter.putValue("Exception: " + e.getMessage());
			}
			return;
		}
		
		try{
			Converter.putValue(Converter.convert(value, convert_from, convert_to, precision));
		}catch(Exception e){
			Converter.putValue("Exception: " + e.getMessage());
		}
	}

	public static int getPrecision() {
		return precision;
	}

	public static void loadAndSetPrecision(){
		try {
			Converter.loadValue();
			Converter.setPrecision(Integer.parseInt(value));
		} catch (Exception e) {
			Converter.putValue("Exception: " + e.getMessage());
		}
	}
	
	public static void setPrecision(int precision) {
		Converter.precision = precision;
	}
}
