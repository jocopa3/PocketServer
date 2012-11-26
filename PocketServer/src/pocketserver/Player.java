/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketserver;

import java.net.InetAddress;

/**
 *
 * @author dev
 */
class Player {
    private InetAddress address;
    private int port;
    private long lastRead;
    private int timeout;

    Player(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    long getLastRead() {
        return this.lastRead;
    }

    void increaseTimeout() {
        this.timeout++;
    }

    int getTimeout() {
        return this.timeout;
    }

    InetAddress getAddress() {
        return this.address;
    }

    void resetTimeout() {
        this.timeout = 0;
    }

    void setLastRead(long time) {
        this.lastRead = time;
    }

    void setPort(int port) {
        this.port = port;
    }
    
}
