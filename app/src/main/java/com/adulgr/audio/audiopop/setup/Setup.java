package com.adulgr.audio.audiopop.setup;

import java.util.Date;
import java.util.UUID;

public class Setup {

  private UUID mId;
  private String mTitle;
  private Date mDate;

  public Setup() {
    mId = UUID.randomUUID();
    mDate = new Date();
  }

  public UUID getId() {
    return mId;
  }

  public void setId(UUID id) {
    mId = id;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }
}
