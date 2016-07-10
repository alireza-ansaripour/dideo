package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import Models.Search;
import Models.Tag;
import Models.Video;
import ir.dideo.dideo.R;
import Models.Search;
/**
 * Created by alireza on 7/6/16.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "dd.db";
    private static final int DATABASE_VERSION = 1;
    private RuntimeExceptionDao<Video,Integer> videos = null;
    private Dao<Search,String> searches = null;
    private RuntimeExceptionDao<Tag,String> tags = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,Search.class);
            TableUtils.createTable(connectionSource,Video.class);
            TableUtils.createTable(connectionSource,Tag.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    public Dao<Search,String> getSearchDao(){
        if(searches == null)
            try {
                return getDao(Search.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return searches;
    }
}
