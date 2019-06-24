package Data;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;


public class Post {
    public int id;
    @SerializedName("liked_by")
    public String[] likedBy;
    @SerializedName("published_when")
    public long publishedWhen;
    @SerializedName("published_by")
    public String publishedBy;
    public Double[] location;
    public String[] hashtags;

    public Post() {
        location = new Double[2];
        location[0] = Double.MAX_VALUE;
        location[1] = Double.MAX_VALUE;
    }

    public Post(int id, String[] likedBy, long publishedWhen, String publishedBy, Double[] location, String[] hashtags) {
        this.id = id;
        this.likedBy = likedBy;
        this.publishedWhen = publishedWhen;
        this.publishedBy = publishedBy;
        this.location = location;
        this.hashtags = hashtags;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", likedBy=" + Arrays.toString(likedBy) +
                ", publishedWhen=" + publishedWhen +
                ", publishedBy='" + publishedBy + '\'' +
                ", location=" + Arrays.toString(location) +
                ", hashtags=" + Arrays.toString(hashtags) +
                '}';
    }
}
