package org.appspot.neurostorage.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.appspot.neurostorage.MainActivity;
import org.appspot.neurostorage.NeuroListActivity;
import org.appspot.neurostorage.R;
import org.appspot.neurostorage.RecordControl;

import java.util.ArrayList;

public class ControlRecyclerViewAdapter extends
  RecyclerView.Adapter<ControlRecyclerViewAdapter.ViewHolder> {
  private ArrayList<RecordControl> mRecords;
  final private String TAG = getClass().getSimpleName();

  public ControlRecyclerViewAdapter(ArrayList<RecordControl> records) {
    mRecords = records;
  }

  @Override
  public ControlRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_control,
      parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ControlRecyclerViewAdapter.ViewHolder holder, int position) {
    RecordControl rc = mRecords.get(position);
    holder.dirName.setText(rc.getDirName());
    try {
      holder.dirSize.setText(Long.toString(rc.getDirSize()) + " MiB");
    } catch(NumberFormatException e) {
      Log.d(TAG, e.getMessage());
      holder.dirSize.setText("unknown size");
    }
    holder.startListener.setPath(rc.getDirPath());
  }

  @Override
  public int getItemCount() {
    return mRecords.size();
  }

  /** A ViewHolder realisation. It holds widgets references */
  class ViewHolder extends RecyclerView.ViewHolder{
    private TextView dirName, dirSize;
    private StartNeuroActivityListener startListener;

    public ViewHolder(View itemView) {
      super(itemView);
      dirName = (TextView) itemView.findViewById(R.id.directory_name);
      dirSize = (TextView) itemView.findViewById(R.id.directory_size);
      startListener = new StartNeuroActivityListener();
      itemView.setOnClickListener(startListener);
    }
  }

  private class StartNeuroActivityListener implements View.OnClickListener {
    private String mPath;

    public void setPath(String path) {
      mPath = path;
    }

    @Override
    public void onClick(View v) {
      Intent intent = new Intent(MainActivity.getInstance(), NeuroListActivity.class);
      intent.putExtra("PATH", mPath);
      MainActivity.getInstance().startActivity(intent);
      Log.d("TAGGGGGG", "Clicked: " + mPath);
    }
  }
}