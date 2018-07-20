package com.adulgr.audio.audiopop.fragments.result;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
import com.adulgr.audio.audiopop.pojo.Result;
import java.text.DateFormat;
import java.util.List;


public class ResultFragment extends Fragment {

  private ListView list;


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
//    return super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.result_fragment, container, false);

    list = view.findViewById(R.id.list);

    list.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });
    return view;
  }

  private class SetupQuery extends AsyncTask<Void, Void, List<Result>> {

    @Override
    protected List<Result> doInBackground(Void... voids) {
      return AudioPopDb.getInstance(getContext()).getSetupDao().selectResults();
    }

    @Override
    protected void onPostExecute(List<Result> setups) {
      ResultAdaptor adapter = new ResultAdaptor(getContext(), setups);
      list.setAdapter(adapter);
    }

  }

  private class ResultAdaptor extends ArrayAdapter<Result> {

    public ResultAdaptor(@NonNull Context context,
        @NonNull List<Result> objects) {
      super(context, R.layout.result_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView == null) {
        convertView = getLayoutInflater().inflate(R.layout.result_item, null, false);
      }
      Result result = getItem(position);
      TextView listSetup = convertView.findViewById(R.id.list_setup);
      TextView listTimestamp = convertView.findViewById(R.id.list_timestamp);
      DateFormat format = DateFormat.getDateTimeInstance();
      listSetup.setText(result.getSetup().getName());
      listTimestamp.setText(format.format(result.getTimestamp()));
      return convertView;
    }
  }

}
