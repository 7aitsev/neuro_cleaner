package org.appspot.neurostorage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.TextView;

import org.appspot.neurostorage.Activity.MainActivity;
import org.appspot.neurostorage.R;
import org.appspot.neurostorage.RecordNeuro;

import java.io.File;
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
    holder.openListener.setPath(rn.getPath());
  }

  @Override
  public int getItemCount() {
    return mRecords.size();
  }

  /** A ViewHolder realisation. It holds widgets references */
  class ViewHolder extends RecyclerView.ViewHolder{
    private TextView fileName, fileSize, fileLastModified;
    private CheckBox checkBox;
    private OpenFileOnClickListener openListener;

    public ViewHolder(View itemView) {
      super(itemView);
      fileName = (TextView) itemView.findViewById(R.id.file_name);
      fileSize = (TextView) itemView.findViewById(R.id.file_size);
      fileLastModified = (TextView) itemView.findViewById(R.id.file_last_modified);
      checkBox = (CheckBox) itemView.findViewById(R.id.neuro_checkbox);
      openListener = new OpenFileOnClickListener();
      itemView.setOnClickListener(openListener);
    }
  }

  private static String humanReadableByteCount(long bytes) {
    int unit = 1024;
    if (bytes < unit) return bytes + " B";
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = ("KMGTPE").charAt(exp-1) + ("i");
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
  }

  private class OpenFileOnClickListener implements View.OnClickListener {
    private final Context c = MainActivity.getInstance().getApplicationContext();
    private String mPath;

    void setPath(final String path) {
      mPath = path;
    }

    @Override
    public void onClick(View v) {
      Log.d(TAG, "Clicked: " + mPath);
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      intent.setDataAndType(Uri.fromFile(new File(mPath)), getMimeType(mPath));

      String title = c.getResources().getString(R.string.chooser_view_title);
      // Create intent to show the chooser dialog
      Intent chooser = Intent.createChooser(intent, title);
      chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

      // Verify the intent will resolve to at least one activity
      if(intent.resolveActivity(c.getPackageManager()) != null) {
        c.startActivity(chooser);
      } else {
        Snackbar.make(v, c.getString(R.string.msg_not_registered_app),Snackbar.LENGTH_LONG)
          .show();
      }
    }

    /**
     * Returns a MIME type from the given URL using an extension of file.
     *
     * @param url an absolute path to the file
     * @return the MIME type for the given extension or null iff there is none
     */
    private String getMimeType(final String url) {
      int lastIndex = url.lastIndexOf('.');
      String extension = (lastIndex != -1) ? url.substring(lastIndex + 1).toLowerCase() : "";
      String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
      Log.d(TAG, "MIME type of selected file: " + mime);
      return mime;
    }
  }
}