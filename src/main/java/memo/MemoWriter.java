package memo;

import lombok.RequiredArgsConstructor;
import user.UserErrorMessage;
import user.UserException;
import user.UserFindable;

@RequiredArgsConstructor
public class MemoWriter {
    private final MemoWritable memoWritable;
    private final UserFindable userFindable;

    void write(String userId, String contents) {
        if (userFindable.findByUserId(userId).isEmpty()) {
            throw new UserException(UserErrorMessage.NOT_EXIST_USER);
        }

        memoWritable.write(userId, contents);
    }
}
