import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("ROWID")
    private int ROWID;
    private String attributedBody;
    private String belong_number;
    private long date;
    private long date_read;
    private String guid;
    private int handle_id;
    private int has_dd_results;
    private int is_deleted;
    private int is_from_me;
    private String send_date;
    private int send_status;
    private String service;
    private String text;

    public int getROWID() {
        return ROWID;
    }

    public void setROWID(int ROWID) {
        this.ROWID = ROWID;
    }

    public String getAttributedBody() {
        return attributedBody;
    }

    public void setAttributedBody(String attributedBody) {
        this.attributedBody = attributedBody;
    }

    public String getBelong_number() {
        return belong_number;
    }

    public void setBelong_number(String belong_number) {
        this.belong_number = belong_number;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate_read() {
        return date_read;
    }

    public void setDate_read(long date_read) {
        this.date_read = date_read;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getHandle_id() {
        return handle_id;
    }

    public void setHandle_id(int handle_id) {
        this.handle_id = handle_id;
    }

    public int getHas_dd_results() {
        return has_dd_results;
    }

    public void setHas_dd_results(int has_dd_results) {
        this.has_dd_results = has_dd_results;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getIs_from_me() {
        return is_from_me;
    }

    public void setIs_from_me(int is_from_me) {
        this.is_from_me = is_from_me;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public int getSend_status() {
        return send_status;
    }

    public void setSend_status(int send_status) {
        this.send_status = send_status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
