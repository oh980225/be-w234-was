package user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum UserErrorMessage {
    DUPLICATE_USER_ID("중복되는 userId 입니다."),
    NOT_EXIST_USER("존재하지 않는 사용자입니다."),
    NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    ;

    private final String detail;
}
