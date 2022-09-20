package webserver;

import db.Database;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthProvider {

    public static void signUp(SignUpRequest signUpRequest) {
        if (Database.findUserById(signUpRequest.getUserId()) != null) {
            throw new UserException(UserErrorMessage.DUPLICATE_USER_ID);
        }

        var newUser = User.builder()
                .userId(signUpRequest.getUserId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .build();

        Database.addUser(newUser);
    }
}
