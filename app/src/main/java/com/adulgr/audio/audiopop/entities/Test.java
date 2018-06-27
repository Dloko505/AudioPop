package com.adulgr.audio.audiopop.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity
public class Test {

  @PrimaryKey(autoGenerate = true)
  private long testId;
  @NonNull
  private String testType;
  @NonNull
  private boolean testResult;
  @NonNull
  private Date testTimestamp;

  public long getTestId() {
    return testId;
  }

  public void setTestId(long testId) {
    this.testId = testId;
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
  public Date getTestTimestamp() {
    return testTimestamp;
  }

  public void setTestTimestamp(@NonNull Date testTimestamp) {
    this.testTimestamp = testTimestamp;
  }
}
