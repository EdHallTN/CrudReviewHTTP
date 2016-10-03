import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    public static HashMap<String, User> users = new HashMap<>();
    public static HashMap m = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);


                    if (user == null) {
                        return new ModelAndView(m, "/login.html");

                    } else {
                        m.put("firstName", user.firstName);
                        m.put("lastName", user.lastName);
                        m.put("albums", user.albums);
                        return new ModelAndView(m, "/index.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",

                ((request, response) -> {

                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    User user = users.get(name);
                    user = new User(name, password);
                    users.put(name, user);

                    Session session = request.session();
                    session.attribute("loginName", name);

                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(
                "/home",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);
                    String title = request.queryParams("title");
                    String artist = request.queryParams("artist");

                    Album newAlbum = new Album(title, artist);
                    user.albums.add(newAlbum);

                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();

                    response.redirect("/");

                    return "";
                })

        );

        Spark.post(
                "/delete",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    int albumEntry = Integer.valueOf(request.queryParams("deleteID"));
                    for (int i = 0; i < albumEntry; i++) {
                        if (albumEntry <= i) {
                            albumEntry++;
                        }
                    }
                    user.albums.remove(albumEntry);

                    response.redirect("/");

                    return "";

                }));

        Spark.get(
                "/update-album/:id",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    int albumID = Integer.valueOf(request.queryParams("updateID"));

                    HashMap m = new HashMap<>();

                    m.put("updateId", albumID);
                    m.put("firstName", user.firstName);
                    m.put("lastName", user.lastName);
                    m.put("albums", user.albums);

                    return new ModelAndView(m, "update.html");

                }),
                new MustacheTemplateEngine()

        );

        Spark.post(
                "/update-entry",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    int albumEntry = Integer.valueOf(request.queryParams("updateID"));

                    String title = request.queryParams("title");
                    String artist = request.queryParams("artist");
                    Album newAlbum = new Album(title, artist);

                    user.albums.set(albumEntry, newAlbum);

                    response.redirect("/");

                    return "";
                }
        );
    }
}
