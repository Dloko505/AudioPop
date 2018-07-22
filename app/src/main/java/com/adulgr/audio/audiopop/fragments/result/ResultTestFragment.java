package com.adulgr.audio.audiopop.fragments.result;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.adulgr.audio.audiopop.R;
import com.adulgr.audio.audiopop.db.AudioPopDb;
import com.adulgr.audio.audiopop.entities.Setup;
import com.adulgr.audio.audiopop.entities.Test;
import com.adulgr.audio.audiopop.fragments.test.TestFragment;
import com.adulgr.audio.audiopop.pojo.SetupResult;
import com.adulgr.audio.audiopop.pojo.TestResult;
import java.text.DateFormat;
import java.util.List;

public class ResultTestFragment extends Fragment {

  private ListView testList;
  private Long setupId;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    if (args != null) {
      setupId = (Long) args.getSerializable(TestFragment.SETUP_KEY);
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("RESULTS");
    getView();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.result_test_fragment, container, false);
    testList = view.findViewById(R.id.test_list);
    if (setupId != null) {
      new SetupQuery().execute(setupId);
      new TestQuery().execute(setupId);
    }
    return view;
  }

  private class SetupQuery extends AsyncTask<Long, Void, Setup> {

    @Override
    protected Setup doInBackground(Long... longs) {
      return AudioPopDb.getInstance(getContext()).getSetupDao().select(longs[0]);
    }

    @Override
    protected void onPostExecute(Setup setup) {
      // TODO Populate UI objects with setup.
      TextView setupTitle = getActivity().findViewById(R.id.setup_list_title);
      TextView setupGear = getActivity().findViewById(R.id.setup_list_gear);
      TextView setupNotes = getActivity().findViewById(R.id.setup_list_notes);

      setupTitle.setText(setup.getName());
      setupGear.setText("Gear: " + setup.getGear());
      setupNotes.setText("Pre-notes: " + setup.getSetup_notes());


    }
  }

  private class TestQuery extends AsyncTask<Long, Void, List<Test>> {

    @Override
    protected List<Test> doInBackground(Long... longs) {
      return AudioPopDb.getInstance(getContext()).getTestDao().selectBySetup(longs[0]);
    }

    @Override
    protected void onPostExecute(List<Test> tests) {
      TestListAdapter adapter = new TestListAdapter(getContext(), tests);
      testList.setAdapter(adapter);
    }

  }

  private class TestListAdapter extends ArrayAdapter<Test> {

    public TestListAdapter(@NonNull Context context,
        @NonNull List<Test> tests) {
      super(context, R.layout.result_test_item, tests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView == null) {
        convertView = getLayoutInflater().inflate(R.layout.result_test_item, parent, false);
      }
      Test test = getItem(position);
      TextView listTimestamp = convertView.findViewById(R.id.test_list_timestamp);
      TextView listTestType = convertView.findViewById(R.id.test_list_test_type);
      TextView listTestNotes = convertView.findViewById(R.id.test_list_test_notes);
      TextView listTestResult = convertView.findViewById(R.id.test_list_test_result);
      DateFormat format = DateFormat.getDateTimeInstance();
      listTimestamp.setText(format.format(test.getTimestamp()));
      listTestType.setText("Type: \n" + test.getTestType());
      listTestNotes.setText("Test Notes: \n" + test.getNotes());
      listTestResult.setText("Results: \n" + Boolean.toString(test.isTestResult()));

      return convertView;
    }
  }
}
