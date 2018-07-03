package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private String userEmail;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(@NonNull String userEmail) {
    this.userEmail = userEmail;
  }
}
