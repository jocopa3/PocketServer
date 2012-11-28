package pocketserver.packets;

import pocketserver.PacketHandler;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.*;

public class Packet02PingListeners extends Packet{

    private byte packetType;
    private long pingID;
    private byte[] magic;

    public Packet02PingListeners(DatagramPacket packet) {
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
        handler.process(getPacket());
    }
}
