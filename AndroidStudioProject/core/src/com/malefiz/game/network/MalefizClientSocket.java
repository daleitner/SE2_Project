package network;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class MalefizClientSocket {
    private Socket socket;
    private Thread receivingThread;
    private String nickName;
    private IDistribute messageDistributor;
    public MalefizClientSocket(String _nickName, Socket _socket, final IDistribute messageDistributor) {
        this.socket = _socket;
        this.nickName = _nickName;
        this.messageDistributor = messageDistributor;
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
                            System.out.println("received a message on server side. <" + nickName + ">,:" + ret);
                            if(messageDistributor != null)
                                messageDistributor.distributeMessage(ret);
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage() + "\n" + e.getStackTrace());
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
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
    }

    public String getNickName() {
        return nickName;
    }


    public void disconnect(){
        this.receivingThread.interrupt();
        this.socket.dispose();
    }
}
