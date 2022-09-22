package webserver;

import db.Database;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthProvider {
    public static void signUp(SignUpRequest signUpRequest) {
        if (Database.findUserById(signUpRequest.getUserId()).isPresent()) {
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

    public static void login(LoginRequest loginRequest) {
        var existedUser = Database.findUserById(loginRequest.getUserId())
                .orElseThrow(() -> new UserException(UserErrorMessage.NOT_EXIST_USER));

        if (!existedUser.getPassword().equals(loginRequest.getPassword())) {
            throw new UserException(UserErrorMessage.NOT_MATCH_PASSWORD);
        }
    }
}
