/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketserver.packets;

import java.net.DatagramPacket;
import pocketserver.PacketHandler;

/**
 *
 * @author dev
 */
public abstract class Packet {
    public abstract DatagramPacket getPacket();
    public void process(PacketHandler handler) {
        
    }

    public void debug() {
        
    }
}
