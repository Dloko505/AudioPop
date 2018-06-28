package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity(
    indices = {
        @Index(value = "name", unique = true)
    }
)
public class Setup {

  @ColumnInfo(name = "setup_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  private String notes;

  @NonNull
  @ColumnInfo (collate = ColumnInfo.NOCASE)
  private String name;
  @NonNull
  private String gear;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public String getGear() {
    return gear;
  }

  public void setGear(@NonNull String gear) {
    this.gear = gear;
  }

}
