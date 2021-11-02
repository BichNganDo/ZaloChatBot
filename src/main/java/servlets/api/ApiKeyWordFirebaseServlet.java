package servlets.api;

import com.google.gson.Gson;
import common.APIResult;
import entity.firebase.KeyWordFirebase;
import entity.firebase.ListKeyWordFirebase;
import helper.ServletUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.firebase.KeyWordFirebaseModel;
import model.mysql.KeyWordModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ApiKeyWordFirebaseServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        APIResult result = new APIResult(0, "Success");

        String action = request.getParameter("action");
        switch (action) {
            case "getkeyword": {
                int pageIndex = NumberUtils.toInt(request.getParameter("page_index"));
                int limit = NumberUtils.toInt(request.getParameter("limit"), 10);
                int searchStatus = NumberUtils.toInt(request.getParameter("search_status"));

                int offset = (pageIndex - 1) * limit;
                List<KeyWordFirebase> listKeyWord = KeyWordFirebaseModel.INSTANCE.getSliceKeyWordFirebase(offset, limit, searchStatus);
                int totalKeyWord = KeyWordFirebaseModel.INSTANCE.getTotalKeyWordFirebase(searchStatus);

                ListKeyWordFirebase listKeyWordsFirebase = new ListKeyWordFirebase();
                listKeyWordsFirebase.setTotal(totalKeyWord);
                listKeyWordsFirebase.setListKeyWordFirebase(listKeyWord);
                listKeyWordsFirebase.setItemPerPage(10);

                if (listKeyWord.size() >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Success");
                    result.setData(listKeyWordsFirebase);
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Fail");
                }
                break;
            }
            case "getKeyWordById": {
                String idKeyWord = request.getParameter("id_key_word");
                KeyWordFirebase keyWordById = KeyWordFirebaseModel.INSTANCE.getKeyWordFirebaseByID(idKeyWord);
                if (StringUtils.isNotEmpty(keyWordById.getId())) {
                    result.setErrorCode(0);
                    result.setMessage("Success");
                    result.setData(keyWordById);
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Fail");
                }
                break;
            }

            default:
                throw new AssertionError();
        }

        ServletUtil.printJson(request, response, gson.toJson(result));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        APIResult result = new APIResult(0, "Success");

        String action = request.getParameter("action");
        switch (action) {
            case "add": {
                String keyWord = request.getParameter("keyWord");
                String respondMessage = request.getParameter("respondMessage");
                int status = NumberUtils.toInt(request.getParameter("status"));

                int addKeyWord = KeyWordFirebaseModel.INSTANCE.addKeyWordFirebase(keyWord, respondMessage, status);
                if (addKeyWord >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Them KeyWord thanh cong!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Them KeyWord that bai!");
                }
                break;
            }
            case "edit": {
                String id = request.getParameter("id");
                String keyWord = request.getParameter("keyWord");
                String respondMessage = request.getParameter("respondMessage");
                int status = NumberUtils.toInt(request.getParameter("status"));

                int editKeyWord = KeyWordFirebaseModel.INSTANCE.editKeyWord(id, keyWord, respondMessage, status);
                if (editKeyWord >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Sua KeyWord thanh cong!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Sua KeyWord that bai!");
                }
                break;
            }

            case "delete": {
                String id = request.getParameter("id");
                int delKeyWord = KeyWordFirebaseModel.INSTANCE.deleteKeyWord(id);
                if (delKeyWord >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Xoa KeyWord thanh cong!");
                } else {
                    result.setErrorCode(-2);
                    result.setMessage("Xoa KeyWord that bai!");
                }
                break;
            }
            default:
                throw new AssertionError();
        }

        ServletUtil.printJson(request, response, gson.toJson(result));

    }
}
