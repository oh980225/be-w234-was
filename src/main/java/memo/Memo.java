package memo;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class Memo {
    Long id;
    String authorId;
    String contents;
    LocalDate localDate;
}
