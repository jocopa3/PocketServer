package pocketserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;
import pocketserver.packets.Packet;
import pocketserver.packets.Packet02PingListeners;
import pocketserver.packets.Packet05ConnectionRequest1;
import pocketserver.packets.Packet06ConnectionReply1;
import pocketserver.packets.Packet07ConnectionRequest2;
import pocketserver.packets.Packet08ConnectionReply2;
import pocketserver.packets.Packet1cPingConnections;
import pocketserver.packets.Packet84;
import pocketserver.packets.Packetc0;


public class PacketHandler implements Runnable {

    private static final Logger logger = Logger.getLogger("PocketServer");
    private DatagramSocket socket;
    private DatagramPacket packet;
    public Player player;
    private boolean running = true;
    private boolean splitted = false;
    
    public PacketHandler(DatagramSocket socket, DatagramPacket packet, Player player) {
        this.socket = socket;
        this.packet = packet;
        this.player = player;
    }

    public void run() {
        DatagramPacket p = packet;
        if (p != null) {
            process(p);
        } else {
            PocketServer.sleep(1); 
        }
    }

    public void process(DatagramPacket p) {
        ByteBuffer data = ByteBuffer.wrap(p.getData());
        int packetType = (data.get() & 0xFF);
        
        Packet response = null;
        switch (packetType) {
            case 0x02:
                response = new Packet02PingListeners(p);
                break;
            case 0x1c:
                response = new Packet1cPingConnections(p);
                break;
            case 0x05:
                response = new Packet05ConnectionRequest1(p);
                break;
            case 0x06:
                response = new Packet06ConnectionReply1(p);
                break;
            case 0x07:
                response = new Packet07ConnectionRequest2(p);
                break;
            case 0x08:
                response = new Packet08ConnectionReply2(p);
                break;
            case 0x84:
		response = new Packet84(p);
                break;
	    case 0xc0:
		response = new Packetc0(p);
		break;
            default:
                logger.warning((new StringBuilder()).append("Unknown packet: ").append(packetType).append(" From: ").append(player.getAddress()).append(" Port: ").append(packet.getPort()).append(" Size: ").append(packet.getLength()).toString());
                break;
        }
        if (response != null) {
            response.process(this);
        }
    }
	
    public void write(DatagramPacket response) {
        if (response != null) {
            try {
                response.setAddress(player.getAddress());
                response.setPort(player.getPort());
                socket.send(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
