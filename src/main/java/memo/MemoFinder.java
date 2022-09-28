package memo;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemoFinder {
    private final MemoFindable memoFindable;

    public List<Memo> findAll() {
        return memoFindable.findAllBySorted();
    }
}
