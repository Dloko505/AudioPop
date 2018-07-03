package com.adulgr.audio.audiopop.compare;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.adulgr.audio.audiopop.R;

public class RecordPop extends Activity {

  private static final int RECORDER_BPP = 16;
  private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
  private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
  private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
  private static final int RECORDER_SAMPLERATE = 44100;
  private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
  private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

  private AudioRecord recorder = null;
  private int bufferSize = 0;
  private Thread recordingThread = null;
  private boolean isRecording = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.test_fragment);
  }
}
