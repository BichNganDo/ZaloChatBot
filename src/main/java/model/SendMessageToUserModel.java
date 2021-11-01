package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class SendMessageToUserModel {

    public static SendMessageToUserModel INSTANCE = new SendMessageToUserModel();

    private SendMessageToUserModel() {
    }

    public int sendMessageToUser(String userId, String message) throws UnsupportedEncodingException, IOException {
        String postUrl = "https://openapi.zalo.me/v2.0/oa/message";// put in your url

        JSONObject jsonData = new JSONObject();
        JSONObject recipient = new JSONObject();
        recipient.put("user_id", userId);
        JSONObject messageObj = new JSONObject();
        messageObj.put("text", message);
        jsonData.put("recipient", recipient);
        jsonData.put("message", messageObj);
        String databody = jsonData.toString();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(databody);
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        post.setHeader("access_token", "fHhfOHrDKoog6-Lc7KfIAAyLvsONNGjoyLh48WbT1slYG_rU6sCPPzzcedyPDMrLt1x26JqiEYU85UuBHZ4e1Aq8s3HR62uDxXdk9GOfBJRCFDKe1mWbBD0tuYW24pOuyH3hTY8Q063s5R5dD1PuQjWzWYPeQd08q6NA7pXw6ZNFTE0u66iGBkuwq3y6FXDrzJlYGG0xEbRMQkf1EMaVSibG-tmwJof4_L742mbK5Z3p1_SK60SsFzzHxGbjO2CUeJFR5nCG4ntBCUGHA68X5VmonWiV5Wa_pZh2BNii92d36-yJW6Q2VHPIM2y");

        HttpResponse response = httpClient.execute(post);
        System.out.println(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println("Response body: " + responseString);
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
//        INSTANCE.sendMessageToUser("111111111111", "Ngan");

        JSONObject json = new JSONObject();
        JSONObject recipient = new JSONObject();
        recipient.put("user_id", "2286817161506731157");
        JSONObject messageObj = new JSONObject();
        messageObj.put("text", "hello, Ngan!");
        json.put("recipient", recipient);

        json.put("message", messageObj);
        System.out.println(json);
    }

}
