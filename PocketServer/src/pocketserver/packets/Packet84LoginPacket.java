package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;
import pocketserver.PacketHandler;

class Packet84LoginPacket {

    private short nameLength;
    private String name;
    private int unknown1;
    private int unknown2;
    
    Packet84LoginPacket(byte[] data) {
	ByteBuffer bb = ByteBuffer.wrap(data);
	nameLength = bb.getShort();
	name = Hex.bytesToString(bb,nameLength);
	bb.get(unknown1);
	bb.get(unknown2);
    }

    public DatagramPacket getPacket() {
    ByteBuffer b = ByteBuffer.allocate(10);
        b.put((byte)0x00);
	b.put((byte)0x03);
	//b.putLong(clientID);
        return new DatagramPacket(b.array(),10);
    }
    
    byte[] response(PacketHandler handler) {
	handler.player.setUsername(name);
	return null;	// getPacket().getData();
    }
    
}
