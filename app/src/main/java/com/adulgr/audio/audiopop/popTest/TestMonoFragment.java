package com.adulgr.audio.audiopop.popTest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import com.adulgr.audio.audiopop.R;

public class TestMonoFragment extends Fragment {

  private Button monoButton;

  private SeekBar sensitivitySlider;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.test_mono_frag, container, false);

    monoButton = (Button) view.findViewById(R.id.mono_button);

    sensitivitySlider = (SeekBar) view.findViewById(R.id.sensitivity_slider);

    return view;
  }
}
