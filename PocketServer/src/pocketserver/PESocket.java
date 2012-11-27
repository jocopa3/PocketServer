package pocketserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

class PESocket implements Runnable {
    
    private static final Logger logger = Logger.getLogger("PocketServer");
    private PocketServer server;
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
             
        players = new ArrayList<Player>();
        while(PocketServer.isServerRunning(server)) {
            read();
            Date date = new Date();
            for (Iterator<Player> it = players.iterator(); it.hasNext();) {
                Player iPlayer = it.next();
                if (date.getTime() - iPlayer.getLastRead() > 15L) {
                    iPlayer.increaseTimeout();
                    if (iPlayer.getTimeout() > 2) {
                        logger.info((new StringBuilder()).append("Client timeout: ").append(iPlayer.getAddress()).toString());
                        logger.info("Save user data!");
                        players.remove(iPlayer);
                        break;
                    }
                } else {
                    iPlayer.resetTimeout(0);
                }
            }
        }
    }

    private void read() {
        byte[] buffer = new byte[1536];
        packet = new DatagramPacket(buffer,1536);
        int bytesRead = 0;
        try {
            socket.setSoTimeout(5000);
            socket.receive(packet);
            socket.setSoTimeout(0);
            bytesRead = packet.getData().length;
        } catch (Exception e) {
            logger.fine("Nobody wants to play? :(");
        }
        
        if (buffer != null) {
            if (bytesRead > 0) {
                if(!isConnected()) { 
                    logger.info(((new StringBuilder()).append("New player connected IP: ").append(packet.getAddress()).append(" Port: ").append(packet.getPort()).toString()));
                    players.add(new Player(packet.getAddress(), packet.getPort())); 
                } else {
                    new Thread(new PacketHandler(socket,packet,currentPlayer())).start();
                }               
            }
        }
    }

    private boolean isConnected() {
        Date date = new Date();
        for (int i = 0; i<players.size(); i++) {
            Player connected = players.get(i);
            if (connected.getAddress().equals(packet.getAddress())) {
                connected.setLastRead(date.getTime());
                connected.setPort(packet.getPort());
                return true;
            }
        }
        return false;
    }

    private Player currentPlayer() {
        for (int i = 0; i<players.size(); i++) {
            Player connected = players.get(i);
            if (connected.getAddress().equals(packet.getAddress())) {
                return connected;
            }
        }
        return null;
    }
    
    
}
