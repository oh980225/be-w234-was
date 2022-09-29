package user;

import lombok.Value;

import java.sql.ResultSet;
import java.sql.SQLException;

@Value
public class UserEntity {
    private static final String USER_ID_FIELD = "user_id";
    private static final String PASSWORD_FIELD = "password";
    private static final String NAME_FIELD = "name";
    private static final String EMAIL_FIELD = "email";

    String userId;
    String password;
    String name;
    String email;

    public UserEntity(ResultSet resultSet) throws SQLException {
        this.userId = resultSet.getString(USER_ID_FIELD);
        this.password = resultSet.getString(PASSWORD_FIELD);
        this.name = resultSet.getString(NAME_FIELD);
        this.email = resultSet.getString(EMAIL_FIELD);
    }

    public User toUser() {
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }
}
