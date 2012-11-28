package pocketserver.packets;

import pocketserver.PacketHandler;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import pocketserver.*;

public class Packet06ConnectionReply1 extends Packet{
    private byte[] magic;
    private long serverID = 1L;
    private short mtuSize;
    private byte packetType;

    public Packet06ConnectionReply1(DatagramPacket packet) {
        ByteBuffer bb = ByteBuffer.wrap(packet.getData());
        packetType = bb.get();
        if (packetType != 0x06) { return; }
        magic = Hex.getMagicFromBuffer(bb);
        mtuSize = bb.getShort();
    }

    public DatagramPacket getPacket() {
        ByteBuffer rData = ByteBuffer.allocate(28);
        rData.put((byte)0x06);
        rData.put(magic);
        rData.putLong(serverID);
        rData.put((byte)0x00);
        rData.putShort(mtuSize);
        return new DatagramPacket(rData.array(),28);
    }

    public void process(PacketHandler handler) {
        handler.write(getPacket());
    }
}
