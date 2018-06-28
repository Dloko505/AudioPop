package com.adulgr.audio.audiopop.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.adulgr.audio.audiopop.entities.Results;
import java.util.List;

@Dao
public interface ResultsDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long insert(Results results);

  @Query("SELECT * FROM Results WHERE results_id = :id")
  Results select(long id);


}
