package pocketserver.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import pocketserver.Hex;
import pocketserver.PacketHandler;

public class Packet84 extends Packet {

    private int packetType;
    private byte[] count = new byte[3];
    private byte[] buffer;
    private Queue customPackets = new LinkedList();
    private int packetLength;
    private int len;

    public Packet84(DatagramPacket packet) {
	ByteBuffer b = ByteBuffer.wrap(packet.getData());
	packetType = Hex.byteToInt((int) b.get());
	if (packetType != 0x84) {
	    return;
	}
	b.get(count);
	len = packet.getLength()-4;
	buffer = new byte[len];
	b.get(buffer);
    }

    @Override
    public DatagramPacket getPacket() {
	return null;
    }

    
    @Override
    public void process(PacketHandler handler) {
	splitPacket();
	Iterator it = customPackets.iterator();
	
	byte[] response = null;
	System.out.println("Packets todo: " + customPackets.size());
	ByteArrayOutputStream f = new ByteArrayOutputStream(); 
	f.write((byte)0x84);
	f.write(Hex.intToBytes(0, 3),0,3);
	while (it.hasNext()) {
	    DatagramPacket packet = (DatagramPacket)customPackets.poll();
	    DataPacket dp = new DataPacket(packet);
	    response = dp.getResponse(handler); 
	    if (response != null) {
		f.write(response,0,response.length);
	    }
	}
	
	if (f.size() > 4) {
	    System.out.println("Response: " + Hex.getHexString(f.toByteArray(), true));
	    System.out.println("Write!");
	    DatagramPacket p = new DatagramPacket(f.toByteArray(),f.size());
	    handler.write(p);
	}
	handler.process(getACK());
    }

    public DatagramPacket getACK() {
	ByteBuffer rData = ByteBuffer.allocate(5);
	rData.put((byte) 0xc0);
	rData.put((byte) 0x01);
	rData.put(count);
	return new DatagramPacket(rData.array(), 5);
    }

    public void splitPacket() {
	ByteBuffer data = ByteBuffer.wrap(buffer);
	int i = 0;
	int length = 0;
	while (i < buffer.length) {
	    if (buffer[i] == 0x00) {
		length = (data.getShort(i+1) / 8) + 3;
	    } else if (buffer[i] == 0x40) {
		length = (data.getShort(i+1) / 8) + 6;
	    } else if (buffer[i] == 0x60) {
		length = (data.getShort(i+1) / 8) + 10;
	    } 
	    data.position(i);
	    byte[] b = new byte[length];
	    data.get(b);
	    System.out.println("split: " + Hex.getHexString(b, true) + " Size: " + length);
	    DatagramPacket split = new DatagramPacket(b, b.length);
	    customPackets.add(split);
	    i += length;
	}
    }
}
