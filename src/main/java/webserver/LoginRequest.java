package webserver;

import lombok.Value;

@Value
class LoginRequest {
    String userId;
    String password;
}
