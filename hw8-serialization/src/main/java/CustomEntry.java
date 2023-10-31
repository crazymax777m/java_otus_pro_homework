import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;
@XmlRootElement(name = "custom_entry")
class CustomEntry {
    @XmlAttribute
    private String key;
    @XmlElement(name = "chat_identifier")
    private String chatIdentifier;
    @XmlElement(name = "member_last")
    private String memberLast;
    @XmlElement(name = "belong_number")
    private String belongNumber;
    @XmlElement(name = "send_date")
    private Date sendDate;
    @XmlElement
    private String text;

    @JsonCreator
    public CustomEntry(@JsonProperty("key") String key,
                       @JsonProperty("chat_identifier") String chatIdentifier,
                       @JsonProperty("member_last") String memberLast,
                       @JsonProperty("belong_number") String belongNumber,
                       @JsonProperty("send_date") Date sendDate,
                       @JsonProperty("text") String text) {
        this.key = key;
        this.chatIdentifier = chatIdentifier;
        this.memberLast = memberLast;
        this.belongNumber = belongNumber;
        this.sendDate = sendDate;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public String getChatIdentifier() {
        return chatIdentifier;
    }

    public String getMemberLast() {
        return memberLast;
    }

    public String getBelongNumber() {
        return belongNumber;
    }

    public Date getSendDate() {
        return sendDate;
    }
    @JsonIgnore
    public String getSendDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return dateFormat.format(sendDate);
    }

    public String getText() {
        return text;
    }

    public void setBelongNumber(String belongNumber) {
        this.belongNumber = belongNumber;
    }
}
