package network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MalefizClient {
    private String ipAddress;
    private Socket socket;
    private Thread receivingThread;
    private String receivedMessage = "";
    public MalefizClient(String ipAddress) {
        this.ipAddress = ipAddress;
        SocketHints socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        socketHints.connectTimeout = 4000;
        //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
        this.socket = Gdx.net.newClientSocket(Net.Protocol.TCP, this.ipAddress, 9021, socketHints);
        this.receivingThread = new Thread(new Runnable(){

            @Override
            public void run() {
                // Loop forever
                while(true){
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    try {
                        // Read to the next newline (\n) and display that text on labelMessage
                        String ret = buffer.readLine();
                        if(ret != null && !ret.isEmpty()) {
                            System.out.println("received a message:" + ret);
                            receivedMessage = ret;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.receivingThread.start();
    }

    public void sendMessage(String message) {
        try {
            // write our entered message to the stream
            this.socket.getOutputStream().write(message.getBytes());
            System.out.println("Sent:" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public String receiveMessage() {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try {
                // Read to the next newline (\n) and display that text on labelMessage
                // labelMessage.setText(
                String ret = buffer.readLine();//);
                if(ret != null && !ret.isEmpty()) {
                    System.out.println("received a message:" + ret);
                    return ret;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return "";
    }*/

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void clearReceivedMessage() {
        this.receivedMessage = "";
    }
}
