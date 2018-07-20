package com.adulgr.audio.audiopop.fragments.setup;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.adulgr.audio.audiopop.R;
import com.adulgr.audio.audiopop.db.AudioPopDb;
import com.adulgr.audio.audiopop.entities.Setup;
import com.adulgr.audio.audiopop.fragments.test.TestFragment;

public class SetupFragment extends Fragment {

  private EditText setupName;
  private RadioGroup setupGear;
  private EditText setupNotes;
  private Button setupSave;
  private Button setupSkip;

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
    final View view = inflater.inflate(R.layout.setup_fragment, container, false);

    setupName = view.findViewById(R.id.setup_title);
    setupGear = view.findViewById(R.id.setup_gear);

    setupNotes = view.findViewById(R.id.setup_notes);
    setupSkip = view.findViewById(R.id.skip_button);
    setupSave = view.findViewById(R.id.setup_save);

    setupSkip.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        goToTestFrag();
      }
    });

    setupSave.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Setup setup = new Setup();
        RadioButton selectedGear = view.findViewById(setupGear.getCheckedRadioButtonId());
        setup.setName(setupName.getText().toString());
        setup.setGear(selectedGear.getText().toString());
        setup.setSetup_notes(setupNotes.getText().toString());
        new SetupInsert().execute(setup);

        Toast.makeText(getActivity(), "Setup added successfully", Toast.LENGTH_SHORT).show();

        goToTestFrag();
      }
    });
    return view;
  }

  private void goToTestFrag() {
    Fragment fragment = new TestFragment();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_container, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }


  private class SetupInsert extends AsyncTask<Setup, Void, Long> {

    @Override
    protected Long doInBackground(Setup... setups) {
      return AudioPopDb.getInstance(getActivity()).getSetupDao().insert(setups[0]);
    }
  }
}
