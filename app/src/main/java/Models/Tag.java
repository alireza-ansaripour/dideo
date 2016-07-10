package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alireza on 7/6/16.
 */
@DatabaseTable(tableName = "tags")
public class Tag  {
    @DatabaseField(id = true)
    public String tagName;
    @DatabaseField
    public int count;
}
