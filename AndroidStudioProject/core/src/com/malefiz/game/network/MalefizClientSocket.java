package network;

import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MalefizClientSocket {
    private Socket socket;
    private Thread receivingThread;
    private String receivedMessage = "";
    public MalefizClientSocket(Socket _socket) {
        this.socket = _socket;
        this.receivingThread = new Thread(new Runnable(){

            @Override
            public void run() {
                // Loop forever
                while(!Thread.currentThread().isInterrupted()){
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    try {
                        // Read to the next newline (\n) and display that text on labelMessage
                        System.out.println("wait for a message  ");
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

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void clearReceivedMessage() {
        this.receivedMessage = "";
    }

    public void disconnect(){
        this.receivingThread.interrupt();
        this.socket.dispose();

    }
}
