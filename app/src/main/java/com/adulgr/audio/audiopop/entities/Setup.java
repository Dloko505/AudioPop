package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity
public class Setup {

  @PrimaryKey(autoGenerate = true)
  private long setupId;

  private String preNotes;
  @NonNull
  private String setupName;
  @NonNull
  private String setupGear;
  @NonNull
  private Date setupTimestamp;

  public long getSetupId() {
    return setupId;
  }

  public void setSetupId(long setupId) {
    this.setupId = setupId;
  }

  public String getPreNotes() {
    return preNotes;
  }

  public void setPreNotes(String preNotes) {
    this.preNotes = preNotes;
  }

  @NonNull
  public String getSetupName() {
    return setupName;
  }

  public void setSetupName(@NonNull String setupName) {
    this.setupName = setupName;
  }

  @NonNull
  public String getSetupGear() {
    return setupGear;
  }

  public void setSetupGear(@NonNull String setupGear) {
    this.setupGear = setupGear;
  }

  @NonNull
  public Date getSetupTimestamp() {
    return setupTimestamp;
  }

  public void setSetupTimestamp(@NonNull Date setupTimestamp) {
    this.setupTimestamp = setupTimestamp;
  }
}
