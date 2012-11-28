package pocketserver.packets;

import pocketserver.PacketHandler;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;

public class Packet07ConnectionRequest2 extends Packet {
    
    private byte packetType;
    private byte[] magic;
    private byte[] cookie;
    private short mtuSize;
    private long clientID;
    
    
    public Packet07ConnectionRequest2(DatagramPacket packet) {
        ByteBuffer bb = ByteBuffer.wrap(packet.getData());
        packetType = bb.get();
        if (packetType != 0x07) { return; }
        magic = Hex.getMagicFromBuffer(bb);
        cookie = Hex.getCookieFromBuffer(bb);
        bb.get();
        mtuSize = bb.getShort();
        clientID = bb.getLong();
    }

    public DatagramPacket getPacket() {
        ByteBuffer b = ByteBuffer.allocate(23);
        b.put((byte)0x08);
        b.put(magic);
        b.put(cookie);
        b.putShort((short)mtuSize);
        return new DatagramPacket(b.array(),23);
    }

    public void process(PacketHandler handler) {
        handler.process(getPacket());
    }
}
