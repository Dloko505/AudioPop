package com.adulgr.audio.audiopop.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.adulgr.audio.audiopop.entities.Test;
import java.util.List;

@Dao
public interface TestDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert (Test test);

  @Query("SELECT * FROM Test WHERE test_id = :id")
  Test select(long id);

  @Query("SELECT * FROM Test WHERE setup_id = :id ORDER BY timestamp DESC")
  List<Test> selectBySetup(long id);

  @Query("SELECT * FROM Test ORDER BY timestamp DESC")
  List<Test> select();
}
