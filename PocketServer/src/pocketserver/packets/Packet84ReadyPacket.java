package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class Packet84ReadyPacket {
    
    private byte unknown;
    
     Packet84ReadyPacket(byte[] data) {
	ByteBuffer bb = ByteBuffer.wrap(data);
	unknown = bb.get();
    }

    public DatagramPacket getPacket() {
	return null;
    }
    
    byte[] response() {
	return null;	// getPacket().getData();
    }  
}
