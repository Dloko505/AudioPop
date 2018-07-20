package com.adulgr.audio.audiopop.compare;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.media.audiofx.AutomaticGainControl;
import android.net.Uri;
import android.os.Bundle;
import com.adulgr.audio.audiopop.R;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;


public class RecordPop {

  private static final int SAMPLE_RATE = 8000;
  private static final int RECORD_BUFFER_MULTIPLIER = 4;
  private static final int READ_BUFFER_SIZE = 4096;
  private static final int NUM_CHANNELS = 1;
  private static final int BITS_PER_SAMPLE_PER_CHANNEL = 16;
  private static final int AUDIO_ENCODING_FORMAT = 1;   // Corresponds to PCM.
  private static final int[] AUDIO_FORMAT_CHANNELS = {
      AudioFormat.CHANNEL_IN_MONO,
      AudioFormat.CHANNEL_IN_STEREO
  };

  private Activity host;
  private File file = null;
  private static boolean recording = false;
  private Recorder recorder = null;


  public RecordPop(Activity host) {
    this.host = host;
  }

  public void startRecording(int secondsToRecord) {
    recording = true;
    new Recorder().start();
    if (secondsToRecord > 0) {
      scheduleStopRecording(secondsToRecord);
    }
  }

  public void stopRecording() {
    recording = false;
  }

  private void scheduleStopRecording(final int secondsToRecord) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        long startTime = System.currentTimeMillis();
        long stopTime = startTime + secondsToRecord * 1000L;
        while (stopTime > startTime && recording) {
          try {
            Thread.sleep(Math.min(stopTime - startTime, 100));
          } catch (InterruptedException e) {
            // Do nothing
          }
          startTime = System.currentTimeMillis();
        }
        if (recording) {
          host.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              stopRecording();
            }
          });
        }
      }
    }).start();
  }

  public void play(Uri source) {
    class Listener implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

      @Override
      public void onPrepared(MediaPlayer player) {
        player.start();
      }

      @Override
      public void onCompletion(MediaPlayer player) {
        player.release();
      }
    }
    MediaPlayer player = null;
    try {
      Listener listener = new Listener();
      AudioAttributes attributes = new AudioAttributes.Builder()
          .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
          .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
          .build();
      player = new MediaPlayer();
      player.setDataSource(host, source);
      player.setAudioAttributes(attributes);
      player.setVolume(1, 1);
      player.setOnPreparedListener(listener);
      player.setOnCompletionListener(listener);
      player.prepareAsync();
    } catch (IOException e) {
      if (player != null) {
        player.release();
      }
    }
  }

  private class Recorder extends Thread {

    @Override
    public void run() {
      try {
        File internal = host.getFilesDir();
        File rawFile = new File(internal, host.getString(R.string.raw_filename_format, new Date()));
        File wavFile = new File(internal, host.getString(R.string.wav_filename_format, new Date()));
        recordRawAudio(rawFile);
        writeWavFile(rawFile, wavFile);
        MediaScannerConnection.scanFile(
            host.getApplicationContext(), new String[]{wavFile.toString()}, null, null);
        file = wavFile;
        rawFile.delete();
        host.runOnUiThread(new Runnable() {
          @Override
          public void run() {

          }
        });
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void recordRawAudio(File rawFile) throws IOException {
      AudioRecord record = null;
      try (
          FileOutputStream os = new FileOutputStream(rawFile);
          BufferedOutputStream output = new BufferedOutputStream(os)
      ) {
        short[] readBuffer = new short[READ_BUFFER_SIZE];
        byte[] writeBuffer = new byte[READ_BUFFER_SIZE * 2];

        record = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
            AUDIO_FORMAT_CHANNELS[NUM_CHANNELS - 1], AudioFormat.ENCODING_PCM_16BIT,
            RECORD_BUFFER_MULTIPLIER * AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT));
        AutomaticGainControl agc = AutomaticGainControl.create(record.getAudioSessionId());
//        agc.setEnabled(true);
        while (record.getState() != AudioRecord.STATE_INITIALIZED) {
        }
        record.startRecording();
        int readLength = 0;
        while (recording || readLength > 0) {
          if (!recording) {
            record.stop();
          }
          readLength = record.read(readBuffer, 0, readBuffer.length);
          if (readLength > 0) {
            shortArrayToLEByteArray(readBuffer, 0, readLength, writeBuffer, 0);
            output.write(writeBuffer, 0, 2 * readLength);
          }
        }
        output.flush();
      }
    }

    private void writeWavFile(File rawFile, File wavFile) throws IOException {
      try (
          FileInputStream is = new FileInputStream(rawFile);
          BufferedInputStream input = new BufferedInputStream(is);
          FileOutputStream os = new FileOutputStream(wavFile);
          BufferedOutputStream output = new BufferedOutputStream(os)
      ) {
        byte[] xferBuffer = new byte[READ_BUFFER_SIZE * 2];
        writeWavHeader(output,
            is.getChannel().size(),         // Number of bytes in raw data
            AUDIO_ENCODING_FORMAT,          // = 1 for PCM
            NUM_CHANNELS,                   // Number of channels
            SAMPLE_RATE,                    // Samples per second
            BITS_PER_SAMPLE_PER_CHANNEL
        );
        while (true) {
          int readLength = input
              .read(xferBuffer, 0, Math.min(input.available(), xferBuffer.length));
          if (readLength <= 0) {
            break;
          } else {
            output.write(xferBuffer, 0, readLength);
          }
        }
        output.flush();
      }
    }

    private void shortArrayToLEByteArray(short[] input, int readOffset, int readLength,
        byte[] output, int writeOffset) {
      for (int i = readOffset, j = writeOffset; i < readOffset + readLength; i++, j += 2) {
        output[j] = (byte) (input[i] & 0xff);
        output[j + 1] = (byte) ((input[i] >> 8) & 0xff);
      }
    }

    private void writeWavHeader(OutputStream output, long rawDataLength, int format,
        int channels, int sampleRate, int bitsPerSamplePerChannel) throws IOException {
      long allDataLength = rawDataLength + 36;
      short bytesPerSample = (short) (channels * bitsPerSamplePerChannel / 8);
      int byteRate = sampleRate * bytesPerSample;
      byte[] header = {
          'R', 'I', 'F', 'F',                      // [0, 4)
          (byte) (allDataLength & 0xff),           // [4, 8)
          (byte) ((allDataLength >> 8) & 0xff),
          (byte) ((allDataLength >> 16) & 0xff),
          (byte) ((allDataLength >> 24) & 0xff),
          'W', 'A', 'V', 'E',                      // [8, 12)
          'f', 'm', 't', ' ',                      // [12, 16)
          16, 0, 0, 0,                             // [16, 20)
          (byte) (format & 0xff),                  // [20, 22)
          (byte) ((format >> 8) & 0xff),
          (byte) (channels & 0xff),                // [22, 24)
          (byte) ((channels >> 8) & 0xff),
          (byte) (sampleRate & 0xff),              // [24, 28)
          (byte) ((sampleRate >> 8) & 0xff),
          (byte) ((sampleRate >> 16) & 0xff),
          (byte) ((sampleRate >> 24) & 0xff),
          (byte) (byteRate & 0xff),                // [28, 32)
          (byte) ((byteRate >> 8) & 0xff),
          (byte) ((byteRate >> 16) & 0xff),
          (byte) ((byteRate >> 24) & 0xff),
          (byte) (bytesPerSample & 0xff),          // [32, 34)
          (byte) ((bytesPerSample >> 8) & 0xff),
          (byte) (bitsPerSamplePerChannel & 0xff), // [34, 36)
          (byte) ((bitsPerSamplePerChannel >> 8) & 0xff),
          'd', 'a', 't', 'a',                      // [36, 40)
          (byte) (rawDataLength & 0xff),           // [40, 44)
          (byte) ((rawDataLength >> 8) & 0xff),
          (byte) ((rawDataLength >> 16) & 0xff),
          (byte) ((rawDataLength >> 24) & 0xff)
      };
      output.write(header);
    }

  }

//  private boolean compareFile() {
//    File internal = getFilesDir();
//    File file = new File(internal, getString(R.string.wav_filename_format, new Date()));

//    Wave w2 = new Wave(file.getPath());
//
//    FingerprintSimilarity fps = w1.getFingerprintSimilarity(w2);
//    float score = fps.getScore();
//    float sim = fps.getSimilarity();
//    System.out.println(sim);
//  }

  public class compare {

    float result;

    public void match() {

      try {
        InputStream fis1 = null, fis2 = null;

        fis1 = new FileInputStream(
            String.valueOf(host.getResources().openRawResource(R.raw.speaker_pop)));
        fis2 = new FileInputStream(file);

        Wave wave1 = new Wave(fis1),
            wave2 = new Wave(fis2);
        FingerprintSimilarity similarity;

        similarity = wave1.getFingerprintSimilarity(wave2);
        result = similarity.getSimilarity();

      } catch (Exception e) {
      }
      System.out.println(result);

    }
  }
}
