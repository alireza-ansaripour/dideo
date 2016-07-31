package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by alireza on 7/4/16.
 */
@DatabaseTable(tableName = "videos")
public class Video {
    public Video(){}

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    public int source_id;
    @DatabaseField
    public String source_video_key;
    @DatabaseField
    public String title;
    @DatabaseField
    public String description;
    @DatabaseField
    public String video_key;
    @DatabaseField
    public String dideo_url;
    @DatabaseField
    public String embed_url;
    @DatabaseField
    public String thumbnail;
    @DatabaseField
    public String thumbnail_hq;
    @DatabaseField
    public String duration;

    public Links links;
    SourceVideoDetails source_details;
    ArrayList<Tag>tags = new ArrayList<>();
    @Override
    public String toString() {
        return source_id+"\n"+source_video_key+"\n"+title+"\n"+description+"\n"+video_key+"\n"+dideo_url+"\n"+embed_url+"\n"+source_details;
    }



}
