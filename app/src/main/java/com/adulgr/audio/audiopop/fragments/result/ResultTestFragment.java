package com.adulgr.audio.audiopop.fragments.result;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.adulgr.audio.audiopop.R;
import com.adulgr.audio.audiopop.db.AudioPopDb;
import com.adulgr.audio.audiopop.entities.Setup;
import com.adulgr.audio.audiopop.entities.Test;
import com.adulgr.audio.audiopop.fragments.test.TestFragment;
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
      new TestQuery().equals(setupId);
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
      Setup temp = setup;
      // TODO Populate UI objects with query result data for setup.
    }
  }

  private class TestQuery extends AsyncTask<Long, Void, List<Test>> {

    @Override
    protected List<Test> doInBackground(Long... longs) {
      return AudioPopDb.getInstance(getContext()).getTestDao().selectBySetup(longs[0]);
    }

    @Override
    protected void onPostExecute(List<Test> tests) {
      // TODO Populate UI objects (e.g. a ListView) with query results for tests.
    }

  }
}
