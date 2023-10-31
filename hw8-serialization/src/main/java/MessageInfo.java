import java.util.Date;

class MessageInfo {
    private String chatIdentifier;
    private String memberLastName;
    private String belongNumber;
    private Date sendDate;
    private String text;

    public MessageInfo(String chatIdentifier, String memberLastName, String belongNumber, Date sendDate, String text) {
        this.chatIdentifier = chatIdentifier;
        this.memberLastName = memberLastName;
        this.belongNumber = belongNumber;
        this.sendDate = sendDate;
        this.text = text;
    }

    public String getChatIdentifier() {
        return chatIdentifier;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public String getBelongNumber() {
        return belongNumber;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public String getText() {
        return text;
    }
}
