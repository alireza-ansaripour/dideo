package Models;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import db.DBHelper;

/**
 * Created by alireza on 7/6/16.
 */
@DatabaseTable
public class Search {
    @DatabaseField(id = true)
    public String query;
    @DatabaseField
    public int count;
    public Search(){}
    public Search(String query){
        this.query = query;
        this.count = 0;
    }
    public static Search[] getAllSearches(Context context) throws SQLException {
        Dao<Search,String> searches = OpenHelperManager.getHelper(context, DBHelper.class).getSearchDao();
        List<Search> searchList = searches.queryForAll();
        return searchList.toArray(new Search[searchList.size()]);
    }
}
