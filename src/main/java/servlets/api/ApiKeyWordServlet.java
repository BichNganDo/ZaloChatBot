package servlets.api;

import com.google.gson.Gson;
import common.APIResult;
import entity.key_word.KeyWord;
import entity.key_word.ListKeyWord;
import helper.ServletUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.KeyWordModel;
import org.apache.commons.lang3.math.NumberUtils;

public class ApiKeyWordServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        APIResult result = new APIResult(0, "Success");

        String action = request.getParameter("action");
        switch (action) {
            case "getkeyword": {
                int pageIndex = NumberUtils.toInt(request.getParameter("page_index"));
                int limit = NumberUtils.toInt(request.getParameter("limit"), 10);
                String searchQuery = request.getParameter("search_query");
                int searchStatus = NumberUtils.toInt(request.getParameter("search_status"));

                int offset = (pageIndex - 1) * limit;
                List<KeyWord> listKeyWord = KeyWordModel.INSTANCE.getSliceKeyWord(offset, limit, searchQuery, searchStatus);
                int totalKeyWord = KeyWordModel.INSTANCE.getTotalKeyWord(searchQuery, searchStatus);

                ListKeyWord listKeyWords = new ListKeyWord();
                listKeyWords.setTotal(totalKeyWord);
                listKeyWords.setListKeyWord(listKeyWord);
                listKeyWords.setItemPerPage(10);

                if (listKeyWord.size() >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Success");
                    result.setData(listKeyWords);
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Fail");
                }
                break;
            }
            case "getKeyWordById": {
                int idKeyWord = NumberUtils.toInt(request.getParameter("id_key_word"));

                KeyWord keyWordById = KeyWordModel.INSTANCE.getKeyWordByID(idKeyWord);

                if (keyWordById.getId() > 0) {
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

                int addKeyWord = KeyWordModel.INSTANCE.addKeyWord(keyWord, respondMessage, status);

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
                int id = NumberUtils.toInt(request.getParameter("id"));
                String keyWord = request.getParameter("keyWord");
                String respondMessage = request.getParameter("respondMessage");
                int status = NumberUtils.toInt(request.getParameter("status"));

                KeyWord keyWordById = KeyWordModel.INSTANCE.getKeyWordByID(id);
                if (keyWordById.getId() == 0) {
                    result.setErrorCode(-1);
                    result.setMessage("Thất bại!");
                    return;
                }

                int editKeyWord = KeyWordModel.INSTANCE.editKeyWord(id, keyWord, respondMessage, status);
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
                int id = NumberUtils.toInt(request.getParameter("id"));
                int deleteKeyWord = KeyWordModel.INSTANCE.deleteKeyWord(id);
                if (deleteKeyWord >= 0) {
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
