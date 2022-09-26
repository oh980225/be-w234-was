package user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAuthProvider {
    private final UserFindable userFindable;
    private final UserRegisterable userRegisterable;

    void signUp(SignUpRequest signUpRequest) {
        if (userFindable.findByUserId(signUpRequest.getUserId()).isPresent()) {
            throw new UserException(UserErrorMessage.DUPLICATE_USER_ID);
        }

        var newUser = User.builder()
                .userId(signUpRequest.getUserId())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .build();

        userRegisterable.register(newUser);
    }

    void login(LoginRequest loginRequest) {
        var existedUser = userFindable.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new UserException(UserErrorMessage.NOT_EXIST_USER));

        if (!existedUser.getPassword().equals(loginRequest.getPassword())) {
            throw new UserException(UserErrorMessage.NOT_MATCH_PASSWORD);
        }
    }
}
