package com.adulgr.audio.audiopop.fragments.setup;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import com.adulgr.audio.audiopop.R;
import com.adulgr.audio.audiopop.db.TestResults;
import com.adulgr.audio.audiopop.entities.Setup;

public class SetupFragment extends Fragment {

  private EditText setupName;
  private RadioButton setupGear;
  private EditText setupNotes;
  private Button setupSave;
  private Button setupCancel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("SETUP");

  }

  public SetupFragment() {
    // Left blank intentionally
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.setup_fragment, container, false);

    setupName = view.findViewById(R.id.setup_title);
    setupGear = view.findViewById(R.id.setup_gear);
    setupNotes = view.findViewById(R.id.setup_notes);
    setupCancel = view.findViewById(R.id.setup_cancel);
    setupSave = view.findViewById(R.id.setup_save);

    setupSave.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Setup setup = new Setup();
        setup.setName(setupName.getText().toString());
        setup.setGear(setupGear.getText().toString());
        setup.setSetupNotes(setupNotes.getText().toString());
        new SetupInsert().execute(setup);
      }
    });
    return view;
  }

  private class SetupInsert extends AsyncTask<Setup, Void, Long> {

    @Override
    protected Long doInBackground(Setup... setups) {
      return TestResults.getInstance(getActivity()).getSetupDao().insert(setups[0]);
    }
  }
}
