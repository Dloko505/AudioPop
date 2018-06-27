package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Results {

  @PrimaryKey(autoGenerate = true)
  private long resultsId;

  private String resNotes;

  public long getResultsId() {
    return resultsId;
  }

  public void setResultsId(long resultsId) {
    this.resultsId = resultsId;
  }


  public String getResNotes() {
    return resNotes;
  }

  public void setResNotes( String resNotes) {
    this.resNotes = resNotes;
  }
}
