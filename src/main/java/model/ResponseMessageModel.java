package model;

import client.MysqlClient;
import entity.sql.KeyWord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseMessageModel {

    private static final MysqlClient dbClient = MysqlClient.getMysqlCli();
    private final String NAMETABLE = "key_word";
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static ResponseMessageModel INSTANCE = new ResponseMessageModel();

    public KeyWord getKeyWordByKey(String keyWord) {
        KeyWord result = new KeyWord();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return result;
            }
            PreparedStatement getKeyWordByKeyStmt = conn.prepareStatement("SELECT * FROM `" + NAMETABLE + "` WHERE key_word = ? AND status = ? ");
            getKeyWordByKeyStmt.setString(1, keyWord);
            getKeyWordByKeyStmt.setInt(2, 1);

            ResultSet rs = getKeyWordByKeyStmt.executeQuery();

            if (rs.next()) {
                result.setId(rs.getInt("id"));
                result.setKeyWord(rs.getString("key_word"));
                result.setResponseMessage(rs.getString("respond_message"));
                result.setStatus(rs.getInt("status"));

                long currentTimeMillis = rs.getLong("created_date");
                Date date = new Date(currentTimeMillis);
                String dateString = sdf.format(date);
                result.setCreatedDate(dateString);
            }

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return result;
    }

}
