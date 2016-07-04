package Models;

/**
 * Created by alireza on 7/4/16.
 */
public class Video {
    String source_id;
    String source_video_key;
    String title;
    String description;
    String video_key;
    String dideo_url;
    String embed_url;
    String thumbnail;
    String thumbnail_hq;
    String duration;
    SourceVideoDetails source_details;

    @Override
    public String toString() {
        return source_id+"\n"+source_video_key+"\n"+title+"\n"+description+"\n"+video_key+"\n"+dideo_url+"\n"+embed_url+"\n"+source_details;
    }
}
