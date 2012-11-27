package pocketserver;

import pocketserver.packets.Packet02;
import pocketserver.packets.Packet;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;


public class PacketHandler implements Runnable {

    private static final Logger logger = Logger.getLogger("PocketServer");
    private DatagramSocket socket;
    private DatagramPacket packet;
    private Player player;
    private boolean running = true;
    
	
    public PacketHandler(DatagramSocket socket, DatagramPacket packet, Player player) {
        this.socket = socket;
        this.packet = packet;
        this.player = player;
    }

    @Override
    public void run() {
        DatagramPacket p = packet;
        if (p != null) {
//            player.setPort(packet.getPort());
//            player.setAddress(packet.getAddress());
            process(p);
        } else {
            PocketServer.sleep(1); 
        }
    }

    public void process(DatagramPacket pPacket) {
        ByteBuffer data = ByteBuffer.wrap(pPacket.getData());
        int packetType = (data.get() & 0xFF);
        String clientIP = pPacket.getAddress().toString().replaceAll("/", "");
        
        Packet response = null;
            //byte[] responseData = null;
        switch (packetType) {
            case 0x02:
                response = new Packet02(pPacket);
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
                running = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
