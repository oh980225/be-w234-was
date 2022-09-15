package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum WebServerErrorMessage {
    EMPTY_REQUEST("요청이 비어있습닏다."),
    INVALID_FORMAT("잘못된 형식의 요청입니다."),
    INVALID_PROTOCOL("지원하지 않는 프로토콜입니다."),
    NOT_FOUND("찾을 수 없는 페이지입니다.");

    private final String detail;
}
