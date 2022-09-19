package webserver;

import db.Database;
import model.User;

public class UserAuthProvider {
    public static Response signUpForGet(Request request) {
        var query = request.getStartLine().getUrl().getQuery();

        var newUser = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));

        Database.addUser(newUser);

        return Response.ok(request.getStartLine().getProtocol());
    }

    public static Response signUp(Request request) {
        var body = request.getBody();

        var newUser = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));

        Database.addUser(newUser);

        return Response.redirect(request.getStartLine().getProtocol(), "/index.html");
    }
}
