package entity.key_word;

public class KeyWord {

    private int id;
    private String keyWord;
    private String respondMessage;
    private int status;
    private String createdDate;

    public KeyWord() {
    }

    public KeyWord(int id, String keyWord, String respondMessage, int status, String createdDate) {
        this.id = id;
        this.keyWord = keyWord;
        this.respondMessage = respondMessage;
        this.status = status;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getRespondMessage() {
        return respondMessage;
    }

    public void setRespondMessage(String respondMessage) {
        this.respondMessage = respondMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
