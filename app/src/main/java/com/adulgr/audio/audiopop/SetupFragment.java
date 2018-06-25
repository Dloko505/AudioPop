package com.adulgr.audio.audiopop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SetupFragment extends Fragment {
  private Setup mSetup;
  private EditText mTitleField;
  private Button mDateButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mSetup = new Setup();
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("Setup");
  }

  public SetupFragment() {
    // Left blank intentionally
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
       Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.setup_fragment, container, false);

    mTitleField = (EditText) v.findViewById(R.id.setup_title);
    mTitleField.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // This space has been left blank intentionally.
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
       mSetup.setTitle(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {
        // This is another space left intentionally blank.
      }
    });

    mDateButton = (Button) v.findViewById(R.id.setup_date);
    mDateButton.setText(mSetup.getDate().toString());
    mDateButton.setEnabled(false);

    return v;
  }

}
