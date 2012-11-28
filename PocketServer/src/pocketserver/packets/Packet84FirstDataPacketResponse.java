package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;
import pocketserver.PacketHandler;

public class Packet84FirstDataPacketResponse {
    private long clientID;
    private byte[] unknown = new byte[85];

    Packet84FirstDataPacketResponse(byte[] data) {
	ByteBuffer bb = ByteBuffer.wrap(data);
	bb.get(unknown);
	clientID = bb.getLong();
    }

    public DatagramPacket getPacket() {
    ByteBuffer b = ByteBuffer.allocate(10);
        b.put((byte)0x00);
	b.put((byte)0x03);
	b.putLong(clientID);
        return new DatagramPacket(b.array(),10);
    }
    
    byte[] response() {
	return getPacket().getData();
    }
    
}
