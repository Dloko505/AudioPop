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
}
