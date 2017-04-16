package Simultator;

import java.security.SecureRandom;

public class Utils {
	public final static byte BIT = 1;
	public final static byte BYTE = 2;
	public final static byte KILO_BYTE = 4;
	public final static byte MEGA_BYTE = 8;
	public final static byte GIGA_BYTE = 16;
	
	public Utils() {}
	
	public static long memory(double amount, byte measure) {
		long value = (long)Math.round(amount * 100) / 100; // arrondi à deux chiffres après la virgule
		
		if((measure & BYTE) != 0) {
			return value * 8;
		} if((measure & KILO_BYTE) != 0) {
			return value * 8 * 1000;
		} else if((measure & MEGA_BYTE) != 0) {
			return value * 8 * 1000 * 1000;
		} else if((measure & GIGA_BYTE) != 0) {
			return value * 8 * 1000 * 1000 * 1000;
		}
		
		return value;
	}
	
	public static int random(int min, int max) {		
		SecureRandom rand = new SecureRandom();
		return rand.nextInt(max - min + 1) + min;
	}
}
