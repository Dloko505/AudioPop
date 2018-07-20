package com.adulgr.audio.audiopop.pojo;

import android.arch.persistence.room.Embedded;
import android.support.annotation.NonNull;
import com.adulgr.audio.audiopop.entities.Setup;
import java.util.Date;

public class SetupResult {

  @Embedded
  private Setup setup;

  @NonNull
  private Date timestamp;

  private long testId;

  public long getTestId() {
    return testId;
  }

  public void setTestId(long testId) {
    this.testId = testId;
  }

  public Setup getSetup() {
    return setup;
  }

  public void setSetup(Setup setup) {
    this.setup = setup;
  }

  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(@NonNull Date timestamp) {
    this.timestamp = timestamp;
  }
}
