package webserver;

import db.Database;
import model.User;

public class UserAuthProvider {
    public static byte[] signUpForGet(Request request) {
        var query = request.getUrl().getQuery();

        var newUser = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));

        Database.addUser(newUser);

        return ("Sign Up " + newUser.getUserId()).getBytes();
    }
}
