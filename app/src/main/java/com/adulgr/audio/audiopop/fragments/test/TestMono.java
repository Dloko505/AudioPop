package com.adulgr.audio.audiopop.fragments.test;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.Gravity.CENTER_VERTICAL;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import com.adulgr.audio.audiopop.R;
import com.adulgr.audio.audiopop.compare.RecordPop;
import com.adulgr.audio.audiopop.db.AudioPopDb;
import com.adulgr.audio.audiopop.entities.Test;
import com.adulgr.audio.audiopop.fragments.result.ResultFragment;
import java.util.Date;

public class TestMono extends Fragment {

  private Button testButton;
  private Button testSave;
  private Button skipButton;
  private EditText testNotes;
  private RecordPop recordPop;
  private Spinner testSelect;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.test_mono, container, false);

    testButton = view.findViewById(R.id.test_button);
    testButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        RecordPop rec = new RecordPop(getActivity());

        rec.startRecording(5);
        rec.play(Uri.parse(
            "android.resource://" + getActivity().getPackageName() + "/raw/speaker_pop.wav"));
        Toast.makeText(getContext(), "Test Commencing", Toast.LENGTH_LONG).show();

        testSelect = getActivity().findViewById(R.id.test_spinner);
        testNotes = view.findViewById(R.id.test_notes);

        final ViewGroup nested = view.findViewById(R.id.notes_popup);
        nested.setVisibility(View.VISIBLE);

        skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            nested.setVisibility(View.INVISIBLE);
            goToResultFrag();
          }
        });

        testSave = view.findViewById(R.id.test_save);
        testSave.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            Test test = new Test();
            test.setTimestamp(new Date());
            test.setTestResult(true);
            test.setNotes(testNotes.getText().toString());
            test.setTestType(testSelect.getSelectedItem().toString());
            new TestInsert().execute(test);

            nested.setVisibility(View.INVISIBLE);
            goToResultFrag();
          }
        });
      }
    });

    return view;
  }

  private void goToResultFrag() {
    Fragment fragment = new ResultFragment();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_container, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  @Override
  public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(v, savedInstanceState);
    getActivity().setTitle("MONO");
  }

  private class TestInsert extends AsyncTask<Test, Void, Long> {

    @Override
    protected Long doInBackground(Test... tests) {
      return AudioPopDb.getInstance(getActivity()).getTestDao().insert(tests[0]);
    }
  }
}
