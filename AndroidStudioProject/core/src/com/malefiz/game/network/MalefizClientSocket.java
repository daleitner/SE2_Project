package network;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class MalefizClientSocket {
    private BufferedReader buffer;
    private Socket socket;
    private Thread receivingThread;
    private String nickName;
    private IDistribute messageDistributor;
    private Logger log;
    public MalefizClientSocket(String _nickName, Socket _socket, final IDistribute messageDistributor) {
        this.socket = _socket;
        this.nickName = _nickName;
        this.messageDistributor = messageDistributor;
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
                            System.out.println("received a message on server side. <" + nickName + ">,:" + ret);
                            if(messageDistributor != null)
                                messageDistributor.distributeMessage(ret);
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
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
            log.error(e.getMessage());
        }
    }

    public String getNickName() {
        return nickName;
    }


    public void disconnect(){
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
