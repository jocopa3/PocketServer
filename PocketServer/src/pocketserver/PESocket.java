/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dev
 */
class PESocket implements Runnable {
    
    private static final Logger logger = Logger.getLogger("PocketServer");
    private final PocketServer server;
    private Thread myThread;
    private static ArrayList<Player> players;
    private DatagramSocket socket;
    public static DatagramPacket packet;
    private int portNumber = 19132;
    
    PESocket(PocketServer server) {
        this.server = server;
        try {
            socket = new DatagramSocket(portNumber);
            socket.setBroadcast(true);
        } catch (Exception e) {
            logger.warning("Address already in use!");
            socket.close();
        }
    }

    void start() {
        myThread = new Thread(this);
        myThread.start();
    }

    @Override
    public void run() {
        players = new ArrayList<>();
        while(PocketServer.isServerRunning(server)) {
            read();
            Date date = new Date();
            for (Player player : players) {
                if (date.getTime() - player.getLastRead() > 15L) {
                    player.increaseTimeout();
                    if (player.getTimeout() > 2) {
                        logger.info((new StringBuilder()).append("Client timeout: ").append(player.getAddress()).toString());
                        logger.info("Save user data!");
                        players.remove(player);
                        break;
                    }
                } else {
                    player.resetTimeout();
                }
            }
        }
    }

    private void read() {
        byte[] buffer = new byte[1536];
        packet = new DatagramPacket(buffer,1536);
        
        try {
            socket.setSoTimeout(5000);
            socket.receive(packet);
            socket.setSoTimeout(0);
        } catch (Exception e) {
            logger.fine("Nobody wants to play? :(");
            buffer = null;
        }
        
        if (buffer != null) {
            if (packet.getData().length > 0) {
                if(!isConnected()) { 
                    logger.info("New player connected");
                    players.add(new Player(packet.getAddress(), packet.getPort())); 
                }
                logger.info("Handle packet in new Thread");
            }
        }
    }

    private boolean isConnected() {
        Date date = new Date();
        for (int i = 0; i<players.size(); i++) {
            Player player = players.get(i);
            if (player.getAddress().equals(packet.getAddress())) {
                player.setLastRead(date.getTime());
                player.setPort(packet.getPort());
                return true;
            }
        }
        return false;
    }
}
