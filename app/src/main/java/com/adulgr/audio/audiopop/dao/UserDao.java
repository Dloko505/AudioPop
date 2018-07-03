package com.adulgr.audio.audiopop.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.adulgr.audio.audiopop.entities.Test;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long insert(Test test);
}
