package db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MySQLInfo {
    URL("jdbc:mysql://localhost:3306/was?serverTimezone=Asia/Seoul&useSSL=false"),
    USER("george"),
    PASSWORD("password123!"),
    ;

    private final String detail;
}
