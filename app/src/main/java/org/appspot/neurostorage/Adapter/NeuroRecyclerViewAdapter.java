package org.appspot.neurostorage.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.appspot.neurostorage.R;
import org.appspot.neurostorage.RecordNeuro;

import java.text.DateFormat;
import java.util.ArrayList;

public class NeuroRecyclerViewAdapter extends
  RecyclerView.Adapter<NeuroRecyclerViewAdapter.ViewHolder> {
  private ArrayList<RecordNeuro> mRecords;
  final private String TAG = getClass().getSimpleName();

  public NeuroRecyclerViewAdapter(ArrayList<RecordNeuro> records) {
    mRecords = records;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_neuro,
      parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    RecordNeuro rn = mRecords.get(position);
    holder.fileName.setText(rn.getName());
    holder.fileSize.setText(humanReadableByteCount(rn.getSize()));
    String date = DateFormat.getDateInstance().format(rn.getDate());
    holder.fileLastModified.setText(date);
    holder.checkBox.setChecked(rn.isChecked());
  }

  @Override
  public int getItemCount() {
    return mRecords.size();
  }

  /** A ViewHolder realisation. It holds widgets references */
  class ViewHolder extends RecyclerView.ViewHolder{
    private TextView fileName, fileSize, fileLastModified;
    private CheckBox checkBox;

    public ViewHolder(View itemView) {
      super(itemView);
      fileName = (TextView) itemView.findViewById(R.id.file_name);
      fileSize = (TextView) itemView.findViewById(R.id.file_size);
      fileLastModified = (TextView) itemView.findViewById(R.id.file_last_modified);
      checkBox = (CheckBox) itemView.findViewById(R.id.neuro_checkbox);
    }
  }

  private static String humanReadableByteCount(long bytes) {
    int unit = 1024;
    if (bytes < unit) return bytes + " B";
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = ("KMGTPE").charAt(exp-1) + ("i");
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
  }
}