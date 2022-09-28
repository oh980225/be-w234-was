package memo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static db.MySQLInfo.*;

public class MemoJDBCAdapter implements MemoFindable, MemoWritable {
    private static final String FIND_ALL_SORTED_SQL = "SELECT * FROM memo ORDER BY create_date DESC, id DESC";
    private static final String INSERT_SQL = "INSERT INTO MEMO(author_id, contents, create_date) VALUES (?, ?, ?)";

    @Override
    public List<Memo> findAllBySorted() {
        List<Memo> memoList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL.getDetail(), USER.getDetail(), PASSWORD.getDetail());
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SORTED_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            makeAllMemoList(memoList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return memoList;
    }

    @Override
    public void write(String userId, String contents) {
        try (Connection connection = DriverManager.getConnection(URL.getDetail(), USER.getDetail(), PASSWORD.getDetail());
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            makeInsertMemoQuery(userId, contents, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeInsertMemoQuery(String userId, String contents, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, contents);
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
    }

    private void makeAllMemoList(List<Memo> memoList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            var memoEntity = new MemoEntity(resultSet);
            memoList.add(memoEntity.toMemo());
        }
    }
}
