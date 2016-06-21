package network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;


public class MalefizServer implements IDistribute{
    private Thread communicationThread;
    private String ipAddress = "";
    private ServerSocket serverSocket;
    private ArrayList<MalefizClientSocket> clientSockets;
    public MalefizServer() {
        this.ipAddress = setIpAddress();
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
                while(!Thread.currentThread().isInterrupted()) {
                    // Create a socket
                    try {
                        Socket socket = serverSocket.accept(null);
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        // Read to the next newline (\n) and display that text on labelMessage
                        String ret = buffer.readLine();
                        if(ret != null && !ret.isEmpty()) {
                            System.out.println("received first message:" + ret);
                            MessageObject obj = MessageObject.MessageToMessageObject(ret);
                            if(obj.getMessageType() == MessageTypeEnum.Connect){
                                MalefizClientSocket mcs = createMalefizClientSocket(obj.getNickName(), socket);
                                clientSockets.add(mcs);
                                // Read data from the socket into a BufferedReader
                                System.out.println("socket added");
                                ArrayList<String>clients = new ArrayList<String>(Arrays.asList(getConnectedPlayers().split("\n")));
                                obj.setInformation(clients);
                                obj.setNickName("server");
                                sendMessage(obj);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage() + "\n" + e.getStackTrace());
                    } catch(GdxRuntimeException ex) {
                        System.out.println(ex.getMessage() + "\n" + ex.getStackTrace());
                    }
                }
            }
        });
    }

    private MalefizClientSocket createMalefizClientSocket(String nickName, Socket socket) {
        return new MalefizClientSocket(nickName, socket, this);
    }
    private String setIpAddress() {
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
                        if(!address.getHostAddress().contains("127.0.0.1"))
                            addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
        return addresses.get(0);
        /*// Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        return ipAddress;*/
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void startWaitingForClients() {
        this.communicationThread.start();
        System.out.println("started listening");
    }

    public void stopWaitingForClients() {
        this.communicationThread.interrupt();
    }

    //sends a message to all clients
    public void sendMessage(MessageObject message) {
        for(MalefizClientSocket socket:this.clientSockets)
        {
            if(!socket.getNickName().equals(message.getNickName()))
                socket.sendMessage(message.getMessage());
        }
    }

    public String getConnectedPlayers() {
        String ret = "";
        for(MalefizClientSocket socket:this.clientSockets)
        {
            ret += socket.getNickName() + "\n";
        }
        if(!ret.isEmpty())
            ret = ret.substring(0, ret.length()-1);
        return ret;
    }

    public int getConnectedPlayersCount() {
        return this.clientSockets.size();
    }

    public void disconnect() {
        for(MalefizClientSocket socket:this.clientSockets) {
            socket.disconnect();
        }
        this.communicationThread.interrupt();
        this.serverSocket.dispose();



    }

    @Override
    public void distributeMessage(String message) {
        sendMessage(MessageObject.MessageToMessageObject(message));
    }
}
