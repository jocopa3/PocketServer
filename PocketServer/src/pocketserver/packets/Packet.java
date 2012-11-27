package pocketserver.packets;

import java.net.DatagramPacket;
import pocketserver.PacketHandler;

public abstract class Packet {
    public abstract DatagramPacket getPacket();
    public void process(PacketHandler handler) {
        
    }

    public void debug() {
        
    }
}
