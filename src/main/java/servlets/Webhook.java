package servlets;

import entity.firebase.KeyWordFirebase;
import entity.sql.KeyWord;
import helper.HttpHelper;
import helper.ServletUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ResponseMessageModel;
import model.SendMessageToUserModel;
import model.firebase.KeyWordFirebaseModel;
import org.json.JSONObject;

public class Webhook extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtil.printJson(request, response, "GET OK");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtil.printJson(request, response, "OK");
        String body = HttpHelper.getBodyData(request);
        JSONObject jbody = new JSONObject(body);
        String idSender = jbody.optJSONObject("sender").optString("id");
        String textMessage = jbody.optJSONObject("message").optString("text");

//        KeyWord keyWordByKey = ResponseMessageModel.INSTANCE.getKeyWordByKey(textMessage);
//        SendMessageToUserModel.INSTANCE.sendMessageToUser(idSender, keyWordByKey.getResponseMessage());
        KeyWordFirebase keyWordByKey = KeyWordFirebaseModel.INSTANCE.getKeyWordByKey(textMessage);
        SendMessageToUserModel.INSTANCE.sendMessageToUser(idSender, keyWordByKey.getResponseMessage());

    }
}
