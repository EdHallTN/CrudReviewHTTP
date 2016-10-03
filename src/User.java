import java.util.ArrayList;

/**
 * Created by EdHall on 9/25/16.
 */

public class User {

    String firstName;
    String lastName;

    ArrayList<Album> albums = new ArrayList<>();

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
