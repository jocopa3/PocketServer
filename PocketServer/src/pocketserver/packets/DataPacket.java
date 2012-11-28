package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;
import pocketserver.PacketHandler;

class DataPacket {
    private int encapsulationID;
    private short length;
    private byte[] data;
    private byte[] count = new byte[3];
    private int mcpeID;
    private byte[] unknown = new byte[4];

    public DataPacket(DatagramPacket p) {
	ByteBuffer b = ByteBuffer.wrap(p.getData());
	encapsulationID = (b.get() & 0xFF);
	if (encapsulationID == 0x00) {
	    length = b.getShort();  
	    data = new byte[length/8];
	    b.get(data);
	} else if (encapsulationID == 0x40) {
	    length = b.getShort();  
	    count = Hex.getCountFromBuffer(b);
	    mcpeID = (b.get()& 0xFF);
	    data = new byte[(length/8)-1];
	    b.get(data);
	} else if (encapsulationID == 0x60) {
	    length = b.getShort();  
	    count = Hex.getCountFromBuffer(b);
	    mcpeID = (b.get()& 0xFF);
	    b.get(unknown);
	    data = new byte[(length/8)-1];
	    b.get(data);
	}
    }

    byte[] getResponse(PacketHandler handler) {
	byte[] b = null;
	if (encapsulationID == 0x40) {
	    if (mcpeID == 0x09) {
		System.out.println("FirstDataPacket");
		Packet84FirstDataPacket fdp = new Packet84FirstDataPacket(data);
		b = fdp.response(handler);
	    } else if (mcpeID == 0x82) {
		System.out.println("LoginPacket");
		Packet84LoginPacket lp = new Packet84LoginPacket(data);
		b = lp.response(handler);
	    }
	} else if (encapsulationID == 0x60) {
	    if (mcpeID == 0x00) {
		System.out.println("FirstDataPacketResponse");
		Packet84FirstDataPacketResponse fdpr = new Packet84FirstDataPacketResponse(data);
		b = fdpr.response();
	    }
	} else {
	    System.out.println("Packet missing in DataPacket.java! " + Integer.toHexString(encapsulationID));
	}
	
	return b;
    }
    
}
