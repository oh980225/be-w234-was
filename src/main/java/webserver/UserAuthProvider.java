package webserver;

import db.Database;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthProvider {

    public static void signUp(SignUpRequest signUpRequest) {
        var newUser = User.builder()
                .userId(signUpRequest.getUserId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .build();

        Database.addUser(newUser);
    }
}
