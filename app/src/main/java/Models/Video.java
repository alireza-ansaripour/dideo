package Models;

/**
 * Created by alireza on 7/4/16.
 */
public class Video {
    public String source_id;
    public String source_video_key;
    public String title;
    public String description;
    public String video_key;
    public String dideo_url;
    public String embed_url;
    public String thumbnail;
    public String thumbnail_hq;
    public String duration;
    SourceVideoDetails source_details;

    @Override
    public String toString() {
        return source_id+"\n"+source_video_key+"\n"+title+"\n"+description+"\n"+video_key+"\n"+dideo_url+"\n"+embed_url+"\n"+source_details;
    }
}
