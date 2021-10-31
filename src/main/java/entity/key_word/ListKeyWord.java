package entity.key_word;

import java.util.List;

public class ListKeyWord {

    private int total;
    private List<KeyWord> listKeyWord;
    private int itemPerPage;

    public ListKeyWord() {
    }

    public ListKeyWord(int total, List<KeyWord> listKeyWord, int itemPerPage) {
        this.total = total;
        this.listKeyWord = listKeyWord;
        this.itemPerPage = itemPerPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KeyWord> getListKeyWord() {
        return listKeyWord;
    }

    public void setListKeyWord(List<KeyWord> listKeyWord) {
        this.listKeyWord = listKeyWord;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

}
