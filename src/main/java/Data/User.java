package Data;

public class User {
    public String username;
    public String creation;
    public User[] toFollow;

    public User(String username, String creation) {
        this.username = username;
        this.creation = creation;
    }

    public User(String username, String creation, User[] toFollow) {
        this.username = username;
        this.creation = creation;
        this.toFollow = toFollow;
    }

    public void setToFollow(User[] toFollow) {
        this.toFollow = toFollow;
    }
}
