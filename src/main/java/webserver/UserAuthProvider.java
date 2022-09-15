package webserver;

import db.Database;
import model.User;

public class UserAuthProvider {
    public static Response signUpForGet(Request request) {
        var query = request.getRequestStartLine().getUrl().getQuery();

        var newUser = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));

        Database.addUser(newUser);

        return Response.ok(
                request.getRequestStartLine().getProtocol(),
                ContentType.TEXT_HTML,
                ("Sign Up " + newUser.getUserId()).getBytes());
    }
}
