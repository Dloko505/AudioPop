package com.adulgr.audio.audiopop.pojo;

import android.arch.persistence.room.Embedded;
import android.support.annotation.NonNull;
import com.adulgr.audio.audiopop.entities.Setup;
import java.util.Date;

public class Result {

  @Embedded
  private Setup setup;


  private boolean testResult;

  @NonNull
  private Date timestamp;

  public Setup getSetup() {
    return setup;
  }

  public void setSetup(Setup setup) {
    this.setup = setup;
  }

  public boolean getTestResult() {
    return testResult;
  }

  public void setTestResult(boolean testResult) {
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
