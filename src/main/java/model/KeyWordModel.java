package model;

import client.MysqlClient;
import common.ErrorCode;
import entity.key_word.KeyWord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class KeyWordModel {

    private static final MysqlClient dbClient = MysqlClient.getMysqlCli();
    private final String NAMETABLE = "key_word";
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static KeyWordModel INSTANCE = new KeyWordModel();

    public List<KeyWord> getSliceKeyWord(int offset, int limit, String searchQuery, int searchStatus) {
        List<KeyWord> resultListKeyWord = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListKeyWord;

            }
            String sql = "SELECT * FROM `" + NAMETABLE
                    + "` WHERE 1 = 1";

            if (StringUtils.isNotEmpty(searchQuery)) {
                sql = sql + " AND key_word LIKE ? ";
            }

            if (searchStatus > 0) {
                sql = sql + " AND status = ? ";
            }

            sql = sql + " LIMIT ? OFFSET ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            int param = 1;

            if (StringUtils.isNotEmpty(searchQuery)) {
                ps.setString(param++, "%" + searchQuery + "%");
            }

            if (searchStatus > 0) {
                ps.setInt(param++, searchStatus);
            }

            ps.setInt(param++, limit);
            ps.setInt(param++, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KeyWord keyWord = new KeyWord();
                keyWord.setId(rs.getInt("id"));
                keyWord.setKeyWord(rs.getString("key_word"));
                keyWord.setResponseMessage(rs.getString("respond_message"));
                keyWord.setStatus(rs.getInt("status"));

                long currentTimeMillis = rs.getLong("created_date");
                Date date = new Date(currentTimeMillis);
                String dateString = sdf.format(date);
                keyWord.setCreatedDate(dateString);

                resultListKeyWord.add(keyWord);
            }

            return resultListKeyWord;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListKeyWord;
    }

    public int getTotalKeyWord(String searchQuery, int searchStatus) {
        int total = 0;
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return total;
            }
            String sql = "SELECT COUNT(id) AS total FROM `" + NAMETABLE + "` WHERE 1=1";
            if (StringUtils.isNotEmpty(searchQuery)) {
                sql = sql + " AND key_word LIKE ? ";
            }

            if (searchStatus > 0) {
                sql = sql + " AND status = ? ";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            int param = 1;

            if (StringUtils.isNotEmpty(searchQuery)) {
                ps.setString(param++, "%" + searchQuery + "%");
            }

            if (searchStatus > 0) {
                ps.setInt(param++, searchStatus);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return total;
    }

    public KeyWord getKeyWordByID(int id) {
        KeyWord result = new KeyWord();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return result;
            }
            PreparedStatement getKeyWordByIdStmt = conn.prepareStatement("SELECT * FROM `" + NAMETABLE + "` WHERE id = ? ");
            getKeyWordByIdStmt.setInt(1, id);

            ResultSet rs = getKeyWordByIdStmt.executeQuery();

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

    public int addKeyWord(String keyWord, String respondMessage, int status) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            PreparedStatement addStmt = conn.prepareStatement("INSERT INTO `" + NAMETABLE + "` (key_word, respond_message, status,"
                    + "created_date) "
                    + "VALUES (?, ?, ?, ?)");
            addStmt.setString(1, keyWord);
            addStmt.setString(2, respondMessage);
            addStmt.setInt(3, status);
            addStmt.setString(4, System.currentTimeMillis() + "");

            int rs = addStmt.executeUpdate();

            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return ErrorCode.FAIL.getValue();
    }

    public int editKeyWord(int id, String keyWord, String respondMessage, int status) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            PreparedStatement editStmt = conn.prepareStatement("UPDATE `" + NAMETABLE + "` SET key_word = ?, respond_message = ?, "
                    + "status = ? WHERE id = ? ");
            editStmt.setString(1, keyWord);
            editStmt.setString(2, respondMessage);
            editStmt.setInt(3, status);
            editStmt.setInt(4, id);

            int rs = editStmt.executeUpdate();

            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return ErrorCode.FAIL.getValue();
    }

    public int deleteKeyWord(int id) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            KeyWord keyWordById = getKeyWordByID(id);
            if (keyWordById.getId() == 0) {
                return ErrorCode.NOT_EXIST.getValue();
            }
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM `" + NAMETABLE + "` WHERE id = ?");
            deleteStmt.setInt(1, id);
            int rs = deleteStmt.executeUpdate();

            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return ErrorCode.FAIL.getValue();
    }
}
