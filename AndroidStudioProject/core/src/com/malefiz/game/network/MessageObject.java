package network;

import java.util.ArrayList;

public class MessageObject {

    private String nickName;
    private MessageTypeEnum messageType;
    private ArrayList<String> information;
    private static String separator = ";";

    public MessageObject(String nickName, MessageTypeEnum messageType, ArrayList<String> information) {
        this.nickName = nickName;
        this.messageType = messageType;
        this.information = information;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public ArrayList<String> getInformation() {
        return information;
    }

    public void setInformation(ArrayList<String> information) {
        this.information = information;
    }

    public String getMessage() {
        String message = nickName + separator + messageType;
        if(information != null) {
            for (String msg : information) {
                message += separator + msg;
            }
        }
        message += "\n";
        return message;
    }

    public static MessageObject MessageToMessageObject(String message) {
        String[] splittedMessage = message.split(separator);
        if(splittedMessage.length < 2)
            return null;
        ArrayList<String> info = new ArrayList<String>();
        for(int i = 2; i<splittedMessage.length; i++)
            info.add(splittedMessage[i]);
        return new MessageObject(splittedMessage[0], MessageTypeEnum.valueOf(splittedMessage[1]), info);
    }

    public static String getSeparator() {
        return separator;
    }
}
