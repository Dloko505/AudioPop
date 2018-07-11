package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Setup.class,
            parentColumns = "setup_id", childColumns = "setup_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Test {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "test_id")
  private long id;


  @ColumnInfo(name = "setup_id", index = true)
  private long setupId;

  public long getSetupId() {
    return setupId;
  }

  public void setSetupId(long setupId) {
    this.setupId = setupId;
  }

  private String notes;

  @NonNull
  private String testType;

  @NonNull
  private boolean testResult;

  @NonNull
  private Date timestamp;

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
  public String getTestType() {
    return testType;
  }

  public void setTestType(@NonNull String testType) {
    this.testType = testType;
  }

  @NonNull
  public boolean isTestResult() {
    return testResult;
  }

  public void setTestResult(@NonNull boolean testResult) {
    this.testResult = testResult;
  }

  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(@NonNull Date timestamp) {
    this.timestamp = timestamp;
  }
}