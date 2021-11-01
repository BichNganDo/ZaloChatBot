package entity.key_word;

public class KeyWord {

    private int id;
    private String keyWord;
    private String responseMessage;
    private int status;
    private String createdDate;

    public KeyWord() {
    }

    public KeyWord(int id, String keyWord, String responseMessage, int status, String createdDate) {
        this.id = id;
        this.keyWord = keyWord;
        this.responseMessage = responseMessage;
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

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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
