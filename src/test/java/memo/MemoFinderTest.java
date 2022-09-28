package memo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoFinderTest {

    private int findAllCallCnt = 0;

    @Test
    @DisplayName("MemoFindable을 이용하여 모든 메모를 조회한다.")
    void findAll() {
        var writer = User.builder()
                .userId("george.5")
                .password("password123")
                .name("오승재")
                .email("oh980225@naver.com")
                .build();
        var memoFinder = new MemoFinder(() -> {
            findAllCallCnt++;
            return List.of(Memo.builder()
                    .id(1L)
                    .contents("오늘은 열심히 개발을 합시다.")
                    .localDate(LocalDate.now())
                    .authorId(writer.getUserId())
                    .build(), Memo.builder()
                    .id(2L)
                    .contents("오늘은 열심히 게임을 합시다.")
                    .localDate(LocalDate.now())
                    .authorId(writer.getUserId())
                    .build());
        });

        memoFinder.findAll();

        assertThat(findAllCallCnt).isOne();
    }
}