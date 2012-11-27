package pocketserver.packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.PacketHandler;
import pocketserver.*;

public class Packet02 extends Packet{

    private long pingID;
    private byte[] magic;

    private byte packetType;
    private Packet1c packet1c;


    public Packet02(DatagramPacket packet) {
        ByteBuffer bb = ByteBuffer.wrap(packet.getData());
        packetType = bb.get();
        if (packetType != 0x02) { return; }
        pingID = bb.getLong();
        magic = Hex.getMagicFromBuffer(bb);
    }

    public DatagramPacket getPacket() {
        ByteBuffer rData = ByteBuffer.allocate(25);
        rData.put((byte)0x1c);
        rData.putLong(pingID);
        rData.put(magic);
        return new DatagramPacket(rData.array(),25);
    }

    public void process(PacketHandler handler) {
        packet1c = new Packet1c(getPacket());
        packet1c.process(handler);
    }
}
