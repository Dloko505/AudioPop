package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  private long userId;
  @NonNull
  private Date userTimestamp;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  @NonNull
  public Date getUserTimestamp() {
    return userTimestamp;
  }

  public void setUserTimestamp(@NonNull Date userTimestamp) {
    this.userTimestamp = userTimestamp;
  }
}
