package webserver;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
class SignUpRequest {
    String userId;
    String password;
    String name;
    String email;
}
