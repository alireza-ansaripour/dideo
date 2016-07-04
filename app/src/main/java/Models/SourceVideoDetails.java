package Models;

/**
 * Created by alireza on 7/4/16.
 */
public class SourceVideoDetails {
    int view_count;
    int like_count;
    long published_at;

    @Override
    public String toString() {
        return "View Count: " + Integer.toString(view_count) + "\n" + "Like count: "+Integer.toString(like_count) + "\n published at" + Long.toString(published_at);
    }
}
