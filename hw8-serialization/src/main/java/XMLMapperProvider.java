import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLMapperProvider {
    public static ObjectMapper getXMLMapper() {
        return new XmlMapper();
    }
}

