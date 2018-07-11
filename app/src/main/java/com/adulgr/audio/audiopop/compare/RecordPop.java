package com.adulgr.audio.audiopop.compare;

import android.app.Activity;
import android.media.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.adulgr.audio.audiopop.R;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RecordPop extends Activity {

  private static final int RECORD_SAMPLERATE = 8000;
  private static final int RECORD_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
  private static final int RECORD_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

  int bufferSize = 0;
  private Thread recordingThread = null;
  private Thread timerThread = null;
  private boolean isRecording = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.test_mono);

    setButtonHandlers();
    enableButtons(false);

    bufferSize = AudioRecord.getMinBufferSize(RECORD_SAMPLERATE,
        RECORD_CHANNELS, RECORD_AUDIO_ENCODING);
  }

  private void setButtonHandlers() {
    findViewById(R.id.mono_button).setOnClickListener(btnClick);
  }

  private void enableButton(int id, boolean isEnable) {
    findViewById(id).setEnabled(isEnable);
  }

  private void enableButtons(boolean isRecording) {
    enableButton(R.id.mono_button, !isRecording);
  }

  int BufferElement2Rec = 1024;
  int BytesPerElement = 2;

  private void startRecording() {
    isRecording = true;
    new Thread(new Runnable() {
      public void run() {
        writeAudioDataToFile();
      }
    }).start();
    recordingThread.start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          // DO nothing; might have to re-sleep.
        }
        stopRecording();
      }
    }).start();
  }

  // Convert short to byte
  private byte[] short2byte(short[] sData) {
    int shortArraySize = sData.length;
    byte[] bytes = new byte[shortArraySize * 2];
    for (int i = 0; i < shortArraySize; i++) {
      bytes[i * 2] = (byte) (sData[i] & 0x00FF);
      bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
      sData[i] = 0;
    }
    return bytes;
  }

  private void writeAudioDataToFile() {
    //Write the output audio in byte
    String testFilePath = "/res/sample_pop/temp_pop.wav";
    try (FileOutputStream os = openFileOutput(testFilePath, 0)) {
      AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
          RECORD_SAMPLERATE, RECORD_CHANNELS,
          RECORD_AUDIO_ENCODING, BufferElement2Rec * BytesPerElement);
      recorder.startRecording();
      short sData[] = new short[BufferElement2Rec];
      while (isRecording) {
        recorder.read(sData, 0, BufferElement2Rec);
        byte bData[] = short2byte(sData);
        os.write(bData, 0, BufferElement2Rec * BytesPerElement);
      }
      recorder.stop();
      recorder.release();
    } catch (IOException e) {
      e.printStackTrace();
    }
//    try (FileInputStream in = openFileInput(testFilePath, )
//        // Open raw file for input, wav file for output.
//        ) {
//      // Write wav file header, copy data from raw file to wav file.
//    }
  }

  private void stopRecording() {
    // Stops the recording activity
    if (null != recordingThread) {
      isRecording = false;
      recordingThread = null;
    }
  }

  private View.OnClickListener btnClick = new View.OnClickListener() {
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.mono_button:
          enableButtons(true);
          startRecording();
          Toast.makeText(getApplicationContext(), "Test Commencing", Toast.LENGTH_LONG).show();
          break;
      }
    }
  };

  public class compare {

    float result;

    public void match() {
      String sample = "res/sample-pop/speaker-pop.wav";
      String input = "/res/sample_pop/temp_pop.wav";

      try {
        InputStream fis1 = null, fis2 = null;

        fis1 = new FileInputStream(sample);
        fis2 = new FileInputStream(input);

        Wave wave1 = new Wave(fis1),
            wave2 = new Wave(fis2);
        FingerprintSimilarity similarity;

        similarity = wave1.getFingerprintSimilarity(wave2);
//      result = similarity.setSimilarity();

      } catch (Exception e) {
      }
      System.out.println(result);

    }
  }
}

