package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    indices = {
        @Index(value = "name", unique = true)
    }
)
public class Setup {

  @ColumnInfo(name = "setup_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  private String SetupNotes;

  @NonNull
  @ColumnInfo (collate = ColumnInfo.NOCASE)
  private String name;

  private String Gear;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getSetupNotes() {
    return SetupNotes;
  }

  public void setSetupNotes(String notes) {
    this.SetupNotes = notes;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }


  public String getGear() {
    return Gear;
  }

  public void setGear(String gear) {
    this.Gear = gear;
  }

}
