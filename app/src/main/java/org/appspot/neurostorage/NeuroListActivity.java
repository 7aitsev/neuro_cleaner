package org.appspot.neurostorage;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.appspot.neurostorage.Adapter.NeuroRecyclerViewAdapter;
import org.appspot.neurostorage.Dialog.BottomSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NeuroListActivity extends AppCompatActivity {
  final private String TAG = getClass().getSimpleName();
  private ArrayList<RecordNeuro> mRecords = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
//  private BottomSheet bs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.neuro_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(getString(R.string.neuro_list_title));
    setActionBar(toolbar);
    // setActionBar first! Order is important
    toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    Bundle extras = getIntent().getExtras();
    if(extras != null) {
      String path = extras.getString("PATH");
      if(path != null) {
        doWork(path);
      }
    }

    mRecyclerView = (RecyclerView) findViewById(R.id.neuro_recycle_view);
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new NeuroRecyclerViewAdapter(mRecords);
    mRecyclerView.setAdapter(mAdapter);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(0xFFFFFFFF);
        sbView.setPadding(0, 0, 0, 0);

        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar
        snackbar.show();
////        layout.getRootView().setVisibility(View.GONE);
//        TextView textView =
//          (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setVisibility(View.GONE);
//        View snackView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
//        ((Snackbar.SnackbarLayout) sbView).addView(snackView, 0);
//        snackbar.show();
//        bs = new BottomSheet(context, R.style.MaterialDialogSheet,
//          (Snackbar.SnackbarLayout) sbView);
//        bs.show();
//        snackbar.show();
      }
    });
  }

  /**
   * Находит файлы в директории path, которые старше N дней (устанавливается пользователем).
   * @param path корневой коталог.
   */
  private void doWork(String path) {
    Log.d(TAG, path);
    if(isExternalStorageWritable()) {
      Log.d(TAG, "Storage is available for read and write");
    }
    long currentTime = System.currentTimeMillis();
    ArrayList<RecordNeuro> temp = new ArrayList<>();
    File[] fileList = Environment.getExternalStoragePublicDirectory(path).listFiles();
    for(File f : fileList) {
      Date lmd = new Date(f.lastModified());
      long diff = getDifferenceSeconds(lmd, currentTime);
      if(diff >= 432000) { // Это 5 (пять) дней
        long size = f.length();
        RecordNeuro rn = new RecordNeuro(size, lmd, f.getName(), f.getPath());
        rn.setFactor(diff * size);
        temp.add(rn);
      }
    }
    Collections.sort(temp, FACTOR_ORDER);
    for(RecordNeuro rn : temp) {
      if(rn.getFactor() >= 0) { // Golden Ratio!
        mRecords.add(rn);
      }
    }
    if(mRecords.size() > temp.get(1).getFactor()*0.62) {
//      bs.show();
    } else {
      Toast.makeText(this, "All clear", Toast.LENGTH_SHORT).show();
    }
  }

  /* Checks if external storage is available for read and write */
  public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if(Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  // TODO переписать это. Похоже, что тут есть ошибки.
  private int getDifferenceSeconds(Date fileDate, long currentTime) {
    long diff = currentTime - fileDate.getTime();
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
}
