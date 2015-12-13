package org.appspot.neurostorage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.appspot.neurostorage.Adapter.ControlRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabControl extends Fragment {
  private ArrayList<RecordControl> mRecords = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private MainActivity mInstance;

  public TabControl() {
    RecordControl rc = new RecordControl("DCIM", 2048, Environment.DIRECTORY_DCIM);
    mRecords.add(rc);
    rc = new RecordControl("Movies", 1024, Environment.DIRECTORY_MOVIES);
    mRecords.add(rc);
    rc = new RecordControl("Downloads", 512, Environment.DIRECTORY_DOWNLOADS);
    mRecords.add(rc);
    rc = new RecordControl("Music", 256, Environment.DIRECTORY_MUSIC);
    mRecords.add(rc);
    rc = new RecordControl("Documents", 128, Environment.DIRECTORY_DOCUMENTS);
    mRecords.add(rc);
    rc = new RecordControl("Podcasts", 64, Environment.DIRECTORY_PODCASTS);
    mRecords.add(rc);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.control, container, false);
    return rootView;
  }

  @Override
  public void onStart() {
    super.onStart();
    mInstance = MainActivity.getInstance();
    mRecyclerView = (RecyclerView) mInstance.findViewById(R.id.control_recycle_view);
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(mInstance);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new ControlRecyclerViewAdapter(mRecords);
    mRecyclerView.setAdapter(mAdapter);
  }
}
