package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.Hex;
import pocketserver.PacketHandler;

public class Packet08 extends Packet {
    private byte packetType;
    private byte[] magic;
    private byte[] cookie;
    private short mtuSize;
    private long serverID = 1L;
    private short clientPort;
    
    public Packet08(DatagramPacket packet) {
        ByteBuffer bb = ByteBuffer.wrap(packet.getData());
        packetType = bb.get();
        if (packetType != 0x08) { return; }
        magic = Hex.getMagicFromBuffer(bb);
        cookie = Hex.getCookieFromBuffer(bb);
        mtuSize = bb.getShort();
        clientPort = (short)packet.getPort();
    }

    public DatagramPacket getPacket() {
        ByteBuffer b = ByteBuffer.allocate(35);
        b.put((byte)0x08);
        b.put(magic);
        b.putLong(serverID);
        b.put(cookie);
        b.put((byte)0xcd);
        b.putShort(clientPort);
        b.putShort((short)mtuSize);
        b.put((byte)0x00);
        return new DatagramPacket(b.array(),35);
    }
    
    public void process(PacketHandler handler) {
        handler.write(getPacket());
    }
}
