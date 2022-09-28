package memo;

import lombok.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Value
public class MemoEntity {
    private static final String ID_FIELD = "id";
    private static final String AUTHOR_ID_FIELD = "author_id";
    private static final String CONTENTS_FIELD = "contents";
    private static final String CREATE_DATE_FIELD = "create_date";

    Long id;
    String authorId;
    String contents;
    LocalDate localDate;

    public MemoEntity(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getLong(ID_FIELD);
        this.authorId = resultSet.getString(AUTHOR_ID_FIELD);
        this.contents = resultSet.getString(CONTENTS_FIELD);
        this.localDate = resultSet.getDate(CREATE_DATE_FIELD).toLocalDate();
    }

    public Memo toMemo() {
        return Memo.builder()
                .id(id)
                .authorId(authorId)
                .contents(contents)
                .localDate(localDate)
                .build();
    }
}
