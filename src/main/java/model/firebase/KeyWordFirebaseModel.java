package model.firebase;

import common.ErrorCode;
import entity.firebase.KeyWordFirebase;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class KeyWordFirebaseModel {

    public static KeyWordFirebaseModel INSTANCE = new KeyWordFirebaseModel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public List<KeyWordFirebase> getSliceKeyWordFirebase(int offset, int limit, int searchStatus) throws IOException {
        List<KeyWordFirebase> resultListKeyWordFirebase = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet("https://zalochatbot-default-rtdb.asia-southeast1.firebasedatabase.app/key_word.json");
            request.setHeader("Accept-Encoding", "UTF-8");

            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity, "UTF-8");
                    JSONObject json = new JSONObject(result);

                    for (String keyStr : json.keySet()) {
                        JSONObject keyvalue = json.getJSONObject(keyStr);
                        KeyWordFirebase keyWord = new KeyWordFirebase();
                        keyWord.setId(keyStr);
                        keyWord.setKeyWord(keyvalue.getString("key_word"));
                        keyWord.setResponseMessage(keyvalue.getString("response_message"));
                        keyWord.setStatus(keyvalue.getInt("status"));
                        long currentTimeMillis = keyvalue.optLong("created_date");
                        Date date = new Date(currentTimeMillis);
                        String dateString = sdf.format(date);
                        keyWord.setCreatedDate(dateString);
                        resultListKeyWordFirebase.add(keyWord);
                    }
                    return resultListKeyWordFirebase;
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return resultListKeyWordFirebase;
    }

    public int getTotalKeyWordFirebase(int searchStatus) throws IOException {
        int total = 0;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet("https://zalochatbot-default-rtdb.asia-southeast1.firebasedatabase.app/key_word.json");
            request.setHeader("Accept-Encoding", "UTF-8");

            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity, "UTF-8");
                    JSONObject json = new JSONObject(result);

                    for (String keyStr : json.keySet()) {
                        total = total + 1;
                    }
                    return total;
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return total;
    }

    public KeyWordFirebase getKeyWordFirebaseByID(String id) throws IOException {
        KeyWordFirebase result = new KeyWordFirebase();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet("https://zalochatbot-default-rtdb.asia-southeast1.firebasedatabase.app/key_word/" + id + ".json");
            request.setHeader("Accept-Encoding", "UTF-8");

            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String strResult = EntityUtils.toString(entity, "UTF-8");
                    JSONObject json = new JSONObject(strResult);
                    result.setId(id);
                    result.setKeyWord(json.getString("key_word"));
                    result.setResponseMessage(json.getString("response_message"));
                    result.setStatus(json.getInt("status"));
                    return result;
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return result;
    }

    public int addKeyWordFirebase(String keyWord, String responseMessage, int status) throws IOException {
        HttpPost post = new HttpPost("https://zalochatbot-default-rtdb.asia-southeast1.firebasedatabase.app/key_word.json");
        JSONObject jsonData = new JSONObject();
        jsonData.put("key_word", keyWord);
        jsonData.put("response_message", responseMessage);
        jsonData.put("status", status);
        jsonData.put("created_date", System.currentTimeMillis() + "");
        String databody = jsonData.toString();

        StringEntity postingString = new StringEntity(databody, "UTF-8");
        post.setEntity(postingString);
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            if (!responseString.isEmpty()) {
                return 1;
            }
        }

        return ErrorCode.FAIL.getValue();
    }

    public int editKeyWord(String id, String keyWord, String responseMessage, int status) throws IOException {
        HttpPut post = new HttpPut("https://zalochatbot-default-rtdb.asia-southeast1.firebasedatabase.app/key_word/" + id + ".json");
        JSONObject jsonData = new JSONObject();
        jsonData.put("key_word", keyWord);
        jsonData.put("response_message", responseMessage);
        jsonData.put("status", status);
        String databody = jsonData.toString();

        StringEntity postingString = new StringEntity(databody, "UTF-8");
        post.setEntity(postingString);
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            if (!responseString.isEmpty()) {
                return 1;
            }
        }

        return ErrorCode.FAIL.getValue();
    }

    public int deleteKeyWord(String id) throws IOException {
        HttpDelete post = new HttpDelete("https://zalochatbot-default-rtdb.asia-southeast1.firebasedatabase.app/key_word/" + id + ".json");
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(post);
        return ErrorCode.SUCCESS.getValue();
    }

    public KeyWordFirebase getKeyWordByKey(String keyWord) throws IOException {
        List<KeyWordFirebase> listKeyWord = INSTANCE.getSliceKeyWordFirebase(0, 10, 1);
        for (KeyWordFirebase keyWordFirebase : listKeyWord) {
            if (keyWordFirebase.getKeyWord().equalsIgnoreCase(keyWord)) {
                return keyWordFirebase;
            }
        }
        return null;
    }
}
