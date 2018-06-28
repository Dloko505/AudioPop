package com.adulgr.audio.audiopop.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.adulgr.audio.audiopop.entities.Setup;
import java.util.List;

@Dao
public interface SetupDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Setup setup);

  @Query("SELECT * FROM Setup WHERE setup_id = :id")
  Setup select(long id);

  @Query("SELECT * FROM Setup ORDER BY name ASC")
  List<Setup> select();

}
