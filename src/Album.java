/**
 * Created by EdHall on 9/26/16.
 */
public class Album {
    String title;
    String artist;
    int id;


    public Album(String title, String artist) {
        this.title = title;
        this.artist = artist;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


