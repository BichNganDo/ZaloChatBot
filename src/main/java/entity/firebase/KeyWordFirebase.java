package entity.firebase;

public class KeyWordFirebase {

    private String id;
    private String keyWord;
    private String responseMessage;
    private int status;
    private String createdDate;

    public KeyWordFirebase() {
    }

    public KeyWordFirebase(String id, String keyWord, String responseMessage, int status, String createdDate) {
        this.id = id;
        this.keyWord = keyWord;
        this.responseMessage = responseMessage;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
