package user;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    String userId;
    String password;
    String name;
    String email;
}
