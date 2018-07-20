package com.adulgr.audio.audiopop.pojo;

import android.arch.persistence.room.Embedded;
import com.adulgr.audio.audiopop.entities.Test;

public class TestResult {

  @Embedded
  private Test test;


}
