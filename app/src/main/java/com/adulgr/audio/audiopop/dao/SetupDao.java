package com.adulgr.audio.audiopop.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.adulgr.audio.audiopop.entities.Setup;
import com.adulgr.audio.audiopop.pojo.Result;
import java.util.List;

@Dao
public interface SetupDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Setup setup);

  @Query("SELECT * FROM setup")
  List<Setup> select();

  @Query("SELECT setup.*, test.timestamp, test.testResult FROM setup JOIN ("
      + "SELECT setup_id, Max(timestamp) AS timestamp FROM test GROUP BY setup_id) "
      + "last_test ON setup.setup_id = last_test.setup_id "
      + "JOIN test ON test.setup_id = last_test.setup_id AND test.timestamp = last_test.timestamp ORDER BY test.timestamp DESC")
  List<Result> selectResults();

}