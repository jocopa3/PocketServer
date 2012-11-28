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
    
    public static byte[] getCountFromBuffer(ByteBuffer bb) {
        byte[] data = new byte[3];
        bb.get(data);
        return data;
    }
    
    public static byte[] getBytesFromBuffer(ByteBuffer bb,int i) {
        byte[] data = new byte[i];
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
	
    public static String getHexString(byte[] b, boolean w) {
        String str = "";
        for (int i=0; i<b.length; i++) {
            if (i != 0 && w) str += " ";
            str += getHexString(b[i]);
        }
        return str;
    }
    
    public static byte[] intToBytes(int x, int n) {
        byte[] bytes = new byte[n];
        for (int i = 0; i < n; i++, x >>>=8) {
            bytes[i] = (byte) (x &0xFF);
        }    
        return bytes;
    }

    public static int bytesToInt(byte[] data) {
        data = new byte[3];
        int value = 0;
        for (int i = 0; i < data.length; i++) {
            value += ((long) data[i] & 0xffL) << (8 * i);
        }
        return value;
    }
    
    public static int byteToInt(int lb) {
        byte hb = 0;
        int e = ((int)hb<<8)|(lb&0xFF);
        return e;
    }
    
    public static byte[] concatByteArray(byte[] a, byte[] b) {
	byte[] result = new byte[a.length + b.length];
	System.arraycopy(a, 0, result, 0, a.length);
	System.arraycopy(b, 0, result, a.length, b.length);
	return result;
    }

    public static String bytesToString(ByteBuffer bb, short nameLength) {
	byte[] b = new byte[nameLength];
	bb.get(b);
	return new String(b);
    }
}
