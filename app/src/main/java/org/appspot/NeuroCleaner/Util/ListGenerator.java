package org.appspot.NeuroCleaner.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.appspot.NeuroCleaner.Activity.MainActivity;
import org.appspot.NeuroCleaner.R;
import org.appspot.NeuroCleaner.RecordNeuro;
import org.appspot.NeuroCleaner.interfaces.ListGeneratorListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ListGenerator extends AsyncTask<String, Integer, ArrayList<RecordNeuro>> {
  private final String TAG = getClass().getSimpleName();
  private final boolean D = true;

  private ProgressDialog progressDialog;
  private final ListGeneratorListener mListener;
  private final Context mContext;

  private long currentTime;

  public ListGenerator(final Context context, final ListGeneratorListener listener) {
    mContext = context;
    mListener = listener;
  }

  // To show progress bar on UI thread
  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    log("onPreExecute");
    if(isExternalStorageWritable()) {
      progressDialog = ProgressDialog.show(mContext,
        mContext.getString(R.string.list_gen_pb_title),
        mContext.getString(R.string.list_gen_pb_msg, 0));
      progressDialog.setCanceledOnTouchOutside(true);
      progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
          cancel(true);
        }
      });
    } else {
      cancel(true);
      Toast.makeText(MainActivity.getInstance(), "Storage is busy", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected ArrayList<RecordNeuro> doInBackground(String... params) {
    log("doInBackground");
    ArrayList<RecordNeuro> mRecords = new ArrayList<>();
    // TODO implement a path validation
    if(! isCancelled()) {
      log("Starting AsyncTask for creating a Neuro List");
      ArrayList<RecordNeuro> tempRecords = new ArrayList<>();
      currentTime = System.currentTimeMillis();
      getListOfAllFilesInDir(params[0], tempRecords);
      Collections.sort(tempRecords, FACTOR_ORDER);
      for(RecordNeuro rn : tempRecords) {
        if(rn.getFactor() >= tempRecords.get(0).getFactor() * 0.38) { // Golden Ratio!
          mRecords.add(rn);
        }
      }
    }
    return mRecords;
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    super.onProgressUpdate(values);
    progressDialog.setMessage(mContext.getString(R.string.list_gen_pb_msg, values[0]));
  }

  @Override
  protected void onPostExecute(ArrayList<RecordNeuro> records) {
    super.onPostExecute(records);
    log("onPostExecute");
    progressDialog.dismiss();
    mListener.onDataSetUpdated(records);
  }

  @Override
  protected void onCancelled() {
    super.onCancelled();
    log("onCancelled");
    progressDialog.dismiss();
  }

  public void getListOfAllFilesInDir(String dirName, ArrayList<RecordNeuro> records) {
    log("Working in the path: " + dirName);
    File directory = new File(dirName);
    // get all the files from a directory
    File[] fList = directory.listFiles();
    try {
      for(final File f : fList) {
        if(! isCancelled()) {
          if(f.isDirectory()) {
//            try {
//              Thread.sleep(300);
//            } catch(InterruptedException e) {
//              e.printStackTrace();
//            }
            getListOfAllFilesInDir(f.getAbsolutePath(), records);
          } else {
            // form a list
            Date lmd = new Date(f.lastModified());
            long diff = getDifferenceSeconds(lmd.getTime(), currentTime);
            if(diff >= 432000) { // Five days TODO make it adjustable from settings
              long size = f.length();
              RecordNeuro rn = new RecordNeuro(size, lmd, f.getName(), f.getPath());
              rn.setFactor(diff * size);
              records.add(rn);
              publishProgress(records.size());
            }
          }
        }
      }
    } catch(NullPointerException e) {
      Log.e(TAG, e.getMessage());
    }
  }

  /** Checks if external storage is available for read and write */
  public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(state);
  }

  /** Calculates difference between two dates in seconds */
  private int getDifferenceSeconds(long startDate, long endDate) {
    long diff = endDate - startDate;
    return (int) TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS);
  }

  private final Comparator<RecordNeuro> FACTOR_ORDER = new Comparator<RecordNeuro>() {
    @Override
    public int compare(RecordNeuro lhs, RecordNeuro rhs) {
      // It needs to form descending set.
      // returns negative value if rhs < lhs, 0 if they are equal and positive if rhs > lhs
      // All factor values must be positive!
      return (int)(rhs.getFactor() - lhs.getFactor());
    }
  };

  private void log(final String msg) {
    if(D)
      Log.d(TAG, msg);
  }
}