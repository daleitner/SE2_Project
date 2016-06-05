package network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MalefizServer {
    private Thread communicationThread;
    private Thread receivingThread;
    private String ipAddresses = "";
    private ServerSocket serverSocket;
    private ArrayList<MalefizClientSocket> clientSockets;
    public MalefizServer() {
        this.ipAddresses = setIpAddresses();
        this.clientSockets = new ArrayList<MalefizClientSocket>();

        ServerSocketHints serverSocketHint = new ServerSocketHints();
        // 0 means no timeout.  Probably not the greatest idea in production!
        serverSocketHint.acceptTimeout = 0;

        // Create the socket server using TCP protocol and listening on 9021
        // Only one app can listen to a port at a time, keep in mind many ports are reserved
        // especially in the lower numbers ( like 21, 80, etc )
        this.serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9021, serverSocketHint);
        // Now we create a thread that will listen for incoming socket connections
        this.communicationThread = new Thread(new Runnable(){

            @Override
            public void run() {
                // Loop forever
                while(true) {
                    // Create a socket
                    Socket socket = serverSocket.accept(null);
                    clientSockets.add(new MalefizClientSocket(socket));
                    // Read data from the socket into a BufferedReader
                    System.out.println("socket added");
                }
            }
        });
    }

    private String setIpAddresses() {
        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        return ipAddress;
    }

    public String getIpAddresses() {
        return this.ipAddresses;
    }

    public void startWaitingForClients() {
        this.communicationThread.start();
        System.out.println("started listening");
    }

    public void stopWaitingForClients() {
        this.communicationThread.interrupt();
    }

    //sends a message to all clients
    public void sendMessage(String message) {
        for(MalefizClientSocket socket:this.clientSockets)
        {
            socket.sendMessage(message);
        }
    }

    public String getReceivedMessage() {
        for(MalefizClientSocket socket:this.clientSockets)
        {
            String ret = socket.getReceivedMessage();
            if(!ret.isEmpty()) {
                socket.clearReceivedMessage();
                return ret;
            }
        }
        return "";
    }
}
