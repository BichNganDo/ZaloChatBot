package entity.firebase;

import java.util.List;

public class ListKeyWordFirebase {

    private int total;
    private List<KeyWordFirebase> listKeyWordFirebase;
    private int itemPerPage;

    public ListKeyWordFirebase() {
    }

    public ListKeyWordFirebase(int total, List<KeyWordFirebase> listKeyWordFirebase, int itemPerPage) {
        this.total = total;
        this.listKeyWordFirebase = listKeyWordFirebase;
        this.itemPerPage = itemPerPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KeyWordFirebase> getListKeyWordFirebase() {
        return listKeyWordFirebase;
    }

    public void setListKeyWordFirebase(List<KeyWordFirebase> listKeyWordFirebase) {
        this.listKeyWordFirebase = listKeyWordFirebase;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

}
