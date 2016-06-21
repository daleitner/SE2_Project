package network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MalefizClient {
    private BufferedReader buffer;
    private String nickName;
    private String ipAddress;
    private Socket socket;
    private Thread receivingThread;
    private String receivedMessage = "";
    private Logger log;
    public MalefizClient(String nickName, String ipAddress) {
        this.nickName = nickName;
        this.ipAddress = ipAddress;
        SocketHints socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        socketHints.connectTimeout = 4000;
        //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
        this.socket = Gdx.net.newClientSocket(Net.Protocol.TCP, this.ipAddress, 9021, socketHints);
        this.receivingThread = new Thread(new Runnable(){

            @Override
            public void run() {
                buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Loop forever
                while(!Thread.currentThread().isInterrupted()){
                    try {
                        // Read to the next newline (\n) and display that text on labelMessage
                        String ret = buffer.readLine();
                        if(ret != null && !ret.isEmpty()) {
                            System.out.println("received a message:" + ret);
                            receivedMessage = ret;
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        });
        this.receivingThread.start();
    }

    public String getNickName() {
        return nickName;
    }

    public void sendMessage(MessageObject message) {
        try {
            // write our entered message to the stream
                Thread.sleep(300);
            String msg = message.getMessage();
            this.socket.getOutputStream().write(msg.getBytes());
            System.out.println("Sent to server:\n" + msg);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        } catch (InterruptedException ex)
        {
            log.error(ex.getMessage());
        }
    }

    public String getReceivedMessage() {
        String msg = receivedMessage;
        clearReceivedMessage();
        return msg;
    }

    public void clearReceivedMessage() {
        this.receivedMessage = "";
    }

    public void disconnect() {
        this.receivingThread.interrupt();
        this.socket.dispose();
        if(this.buffer != null) {
            try {
                this.buffer.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
