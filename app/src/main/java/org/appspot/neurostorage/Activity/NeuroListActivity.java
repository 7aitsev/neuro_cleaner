package org.appspot.neurostorage.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import org.appspot.neurostorage.Adapter.NeuroRecyclerViewAdapter;
import org.appspot.neurostorage.Dialog.BottomSheet;
import org.appspot.neurostorage.R;
import org.appspot.neurostorage.RecordNeuro;
import org.appspot.neurostorage.Util.ListGenerator;
import org.appspot.neurostorage.interfaces.ListGeneratorListener;

import java.util.ArrayList;

public class NeuroListActivity extends AppCompatActivity
                               implements ListGeneratorListener {
  final private String TAG = getClass().getSimpleName();
//  private ArrayList<RecordNeuro> mRecords = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private BottomSheet bs;

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

    // initialize BottomSheet
    bs = new BottomSheet(NeuroListActivity.this, R.style.MaterialDialogSheet);
    // create final instance of FAB
    final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_show_bs);
    bs.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialog) {
        if(!fab.isShown()) {
          fab.show();
        }
      }
    });
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        fab.hide();
        bs.show();
      }
    });
    fab.hide(); // button is hidden on start

    // attach adapter with empty list
    mRecyclerView = (RecyclerView) findViewById(R.id.neuro_recycle_view);
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new NeuroRecyclerViewAdapter(new ArrayList<RecordNeuro>());
    mRecyclerView.setAdapter(mAdapter);

    // create a list
    Bundle extras = getIntent().getExtras();
    String rawPath = "unknown", path = "unknown";
    try {
      rawPath = extras.getString(Intent.EXTRA_TEXT);
      path = Environment.getExternalStoragePublicDirectory(rawPath).getAbsolutePath();
      // the task can be executed only once
      new ListGenerator(NeuroListActivity.this, this).execute(path);
    } catch(NullPointerException e) {
      Log.e(TAG, e.getMessage() +
        ". rawPath: " + rawPath + ", path: " + path + ", extras: " + extras);
    }
  }

  @Override
  public void onDataSetUpdated(ArrayList<RecordNeuro> records) {
    mAdapter = new NeuroRecyclerViewAdapter(records);
    mRecyclerView.setAdapter(mAdapter);
    if(records.size() > 0) {
      bs.show();
    } else {
      Toast.makeText(this, "All clear", Toast.LENGTH_SHORT).show();
    }
  }
}