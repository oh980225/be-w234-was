package memo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.User;
import user.UserErrorMessage;
import user.UserException;
import user.UserFindable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoWriterTest {
    private MemoWritable memoWritable;
    private UserFindable userFindable;
    private MemoWriter memoWriter;
    private int writeCallCnt = 0;
    private int findByUserIdCallCnt = 0;


    @Test
    @DisplayName("userId와 contents를 받아서 메모를 작성한다.")
    void write() {
        memoWritable = (userId, contents) -> writeCallCnt++;
        userFindable = (userId) -> {
            findByUserIdCallCnt++;
            return Optional.of(User.builder()
                    .userId("george.5")
                    .password("password456")
                    .name("오승재")
                    .email("oh980225@naver.com")
                    .build());
        };
        memoWriter = new MemoWriter(memoWritable, userFindable);

        memoWriter.write("george.5", "오늘은 step7 과제를 해결할 것이다.");

        assertThat(findByUserIdCallCnt).isOne();
        assertThat(writeCallCnt).isOne();
    }

    @Test
    @DisplayName("userId의 사용자가 없다면 예외를 발생시킨다.")
    void write_() {
        memoWritable = (userId, contents) -> writeCallCnt++;
        userFindable = (userId) -> {
            findByUserIdCallCnt++;
            return Optional.empty();
        };
        memoWriter = new MemoWriter(memoWritable, userFindable);

        assertThatThrownBy(() -> memoWriter.write("george.5", "오늘은 step7 과제를 해결할 것이다."))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.NOT_EXIST_USER.getDetail());
    }
}