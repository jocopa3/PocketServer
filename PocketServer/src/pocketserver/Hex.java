package pocketserver;

import java.nio.ByteBuffer;

public class Hex {

    public static byte[] getMagicFromBuffer(ByteBuffer bb) {
        byte[] data = new byte[16];
        bb.get(data);
        return data;
    }
    
    public static byte[] getCookieFromBuffer(ByteBuffer bb) {
        byte[] data = new byte[4];
        bb.get(data);
        return data;
    }
    
    public static String getHexString(byte b) {
		String hex = Integer.toHexString(0xFF & b);
		String hexString = "";
		if (hex.length() == 1) {
			hexString += "0";
		}
		hexString += hex;
		return hexString;
	}
	
    public static String getHexString(byte[] b) {
        String str = "";
        for (int i=0; i<b.length; i++) {
            //if (i != 0) { str += " "; }
            str += getHexString(b[i]);
        }
        return str;
    }
}
