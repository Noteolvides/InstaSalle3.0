package Data;

import com.google.gson.annotations.SerializedName;

public class Post {
    public int id;
    @SerializedName("liked_by")
    public String[] likedBy;
    @SerializedName("published_when")
    public int publishedWhen;
    @SerializedName("published_by")
    public String publishedBy;
    public Double[] location;
    public String[] hashtags;

    public Post() {
    }

    public Post(int id, String[] likedBy, int publishedWhen, String publishedBy, Double[] location, String[] hashtags) {
        this.id = id;
        this.likedBy = likedBy;
        this.publishedWhen = publishedWhen;
        this.publishedBy = publishedBy;
        this.location = location;
        this.hashtags = hashtags;
    }
}
