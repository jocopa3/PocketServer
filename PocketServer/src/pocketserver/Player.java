package pocketserver;

import java.net.InetAddress;

public class Player {
    private InetAddress address;
    private int port;
    private long lastRead;
    private int timeout;

    public Player(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public long getLastRead() {
        return this.lastRead;
    }

    public void increaseTimeout() {
        this.timeout++;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public void setLastRead(long time) {
        this.lastRead = time;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    void resetTimeout(int i) {
        this.timeout = i;
    }

    void setAddress(InetAddress address) {
        this.address = address;
    }
}
