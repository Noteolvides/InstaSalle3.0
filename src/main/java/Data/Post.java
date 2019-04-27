package Data;

public class Post {
    public int id;
    public User[] likedBy;
    public String publishedWhen;
    public String publishedBy;
    public Long[] location;
    public String[] hashtags;

    public Post(int id, User[] likedBy, String publishedWhen, String publishedBy, Long[] location, String[] hashtags) {
        this.id = id;
        this.likedBy = likedBy;
        this.publishedWhen = publishedWhen;
        this.publishedBy = publishedBy;
        this.location = location;
        this.hashtags = hashtags;
    }
}
