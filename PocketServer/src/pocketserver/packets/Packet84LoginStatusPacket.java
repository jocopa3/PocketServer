package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.PacketHandler;

public class Packet84LoginStatusPacket {
    
    private int unknown;
    
    Packet84LoginStatusPacket(byte[] data) {
	ByteBuffer bb = ByteBuffer.wrap(data);
	bb.get(unknown);
    }

    public DatagramPacket getPacket() {
        return null;
    }
    
    byte[] response(PacketHandler handler) {
	return null;	// getPacket().getData();
    }
}
