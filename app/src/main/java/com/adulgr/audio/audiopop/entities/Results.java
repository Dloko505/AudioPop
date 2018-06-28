package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Test.class,
            parentColumns = "test_id", childColumns = "test_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Results {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "results_id")
  private long id;

  @ColumnInfo(name = "test_id", index = true)
  private long testId;

  public long getTestId() {
    return testId;
  }

  public void setTestId(long testId) {
    this.testId = testId;
  }

  private String notes;

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
}
