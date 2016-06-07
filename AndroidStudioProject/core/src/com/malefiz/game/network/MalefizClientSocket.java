package network;

import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MalefizClientSocket {
    private Socket socket;
    private Thread receivingThread;
    private String receivedMessage = "";
    private String nickName;
    public MalefizClientSocket(String _nickName, Socket _socket) {
        this.socket = _socket;
        this.nickName = _nickName;
        this.receivedMessage = _nickName;
        this.receivingThread = new Thread(new Runnable(){

            @Override
            public void run() {
                // Loop forever
                while(!Thread.currentThread().isInterrupted()){
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

    public String getNickName() {
        return nickName;
    }

    public String getReceivedMessage() {
        String ret = this.receivedMessage;
        this.receivedMessage = "";
        return ret;
    }

    public void disconnect(){
        this.receivingThread.interrupt();
        this.socket.dispose();

    }
}
