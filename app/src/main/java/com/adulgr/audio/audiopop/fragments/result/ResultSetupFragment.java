package com.adulgr.audio.audiopop.fragments.result;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.adulgr.audio.audiopop.R;
import com.adulgr.audio.audiopop.db.AudioPopDb;
import com.adulgr.audio.audiopop.fragments.test.TestFragment;
import com.adulgr.audio.audiopop.pojo.SetupResult;
import java.text.DateFormat;
import java.util.List;


public class ResultSetupFragment extends Fragment {

  private ListView setupList;


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("RESULTS");
    new SetupQuery().execute();
    getView();

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.result_setup_fragment, container, false);

    setupList = view.findViewById(R.id.setup_list);

    setupList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToResultTestFrag((SetupResult) parent.getItemAtPosition(position));
      }
    });
    return view;
  }

  private void goToResultTestFrag(SetupResult result) {
    Fragment fragment = new ResultTestFragment();
    Bundle args = new Bundle();
    args.putSerializable(TestFragment.SETUP_KEY, result.getSetup().getId());
    args.putSerializable(TestFragment.TEST_KEY, result.getTestId());
    fragment.setArguments(args);
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_container, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  private class SetupQuery extends AsyncTask<Void, Void, List<SetupResult>> {

    @Override
    protected List<SetupResult> doInBackground(Void... voids) {
      return AudioPopDb.getInstance(getContext()).getSetupDao().selectResults();
    }

    @Override
    protected void onPostExecute(List<SetupResult> setups) {
      ResultSetupAdaptor adapter = new ResultSetupAdaptor(getContext(), setups);
      setupList.setAdapter(adapter);
    }

  }

  private class ResultSetupAdaptor extends ArrayAdapter<SetupResult> {

    public ResultSetupAdaptor(@NonNull Context context,
        @NonNull List<SetupResult> objects) {
      super(context, R.layout.result_setup_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView == null) {
        convertView = getLayoutInflater().inflate(R.layout.result_setup_item, null, false);
      }
      SetupResult setupResult = getItem(position);
      TextView listSetup = convertView.findViewById(R.id.list_setup);
      TextView listTimestamp = convertView.findViewById(R.id.list_timestamp);
      DateFormat format = DateFormat.getDateTimeInstance();
      listSetup.setText(setupResult.getSetup().getName());
      listTimestamp.setText(format.format(setupResult.getTimestamp()));
      return convertView;
    }
  }

}
