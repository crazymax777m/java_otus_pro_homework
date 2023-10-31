import java.util.List;
import javax.xml.bind.annotation.*;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChatSessionsData {
    @XmlElement(name = "chat_sessions")
    private List<ChatSession> chat_sessions;

    public List<ChatSession> getChat_sessions() {
        return chat_sessions;
    }

    public void setChat_sessions(List<ChatSession> chat_sessions) {
        this.chat_sessions = chat_sessions;
    }

    // Геттеры и сеттеры
}
