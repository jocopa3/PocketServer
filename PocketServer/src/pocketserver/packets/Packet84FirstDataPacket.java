package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;
import pocketserver.PacketHandler;

public class Packet84FirstDataPacket {
    private long unknown;
    private long unknown1;
    private byte unknown2;
    private PacketHandler handle;

    Packet84FirstDataPacket(byte[] data) {
	ByteBuffer bb = ByteBuffer.wrap(data);
        unknown = bb.getLong();
	unknown1 = bb.getLong();
	unknown2 = bb.get();
    }

    public DatagramPacket getPacket(int port) {
	ByteBuffer b = ByteBuffer.allocate(106);
	b.put((byte)0x60);  // Encapsulation ID
	b.put((byte)0x03);b.put((byte)0x00); // size of packet
	b.put(Hex.intToBytes(0, 3)); // count
	b.put((byte)0x00); // MinecrafPE ID
	b.putInt(16);
	b.put((byte)0x04); b.put((byte)0x3f); b.put((byte)0x57); b.put((byte)0xfe); // Cookie needs to be saved in player class
	b.put((byte)0xcd);
	b.putShort((short)port);
	
	for(int i=0;i<10;i++) {
	    b.put(Hex.intToBytes(4, 3));
	    b.putInt(0xffffffff);
	}
	
	b.putShort((short)0);
	b.putLong(unknown1);
	b.putLong(1L);
        return new DatagramPacket(b.array(),106);
    }

    byte[] response(PacketHandler handler) {
	return getPacket(handler.player.getPort()).getData();
    }
}
