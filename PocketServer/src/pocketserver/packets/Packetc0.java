package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;
import pocketserver.PacketHandler;

public class Packetc0 extends Packet {
    
    private int packetType;
    private byte[] count1 = new byte[3];
    private byte[] count2 = new byte[3];
    private byte multi;

    public Packetc0(DatagramPacket packet) {
        ByteBuffer bb = ByteBuffer.wrap(packet.getData());
        packetType = Hex.byteToInt((int)bb.get());
        if (packetType != 0xc0) { return; }
	multi = bb.get();
        bb.get(count1);
	if (multi == 0x00) {
	    bb.get(count2);
	}
    }
    
    public void process(PacketHandler handler) {
        handler.write(getPacket());  
    }
    
    public DatagramPacket getPacket() {
	ByteBuffer rData = ByteBuffer.allocate(7);
	rData.put((byte)0xc0);
	rData.putShort((short)1);
	rData.put(multi);
	rData.put(count1);
//	if (multi == 0x00) {
//	    rData.put(count2);
//	    return new DatagramPacket(rData.array(),10);
//	} else {
	    return new DatagramPacket(rData.array(),7);
//	}
    }
    
}
