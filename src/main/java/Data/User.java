package Data;

public class User {
    public String username;
    public String creation;
    public String[] toFollow;

    public User(String username, String creation) {
        this.username = username;
        this.creation = creation;
    }

    public User(String username, String creation, String[] toFollow) {
        this.username = username;
        this.creation = creation;
        this.toFollow = toFollow;
    }

    public void setToFollow(String[] toFollow) {
        this.toFollow = toFollow;
    }
}
