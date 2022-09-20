package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum UserErrorMessage {
    DUPLICATE_USER_ID("중복되는 userId 입니다."),
    ;

    private final String detail;
}
