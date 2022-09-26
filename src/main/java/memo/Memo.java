package memo;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class Memo {
    Long id;
    String userId;
    String contents;
    LocalDate localDate;
}
