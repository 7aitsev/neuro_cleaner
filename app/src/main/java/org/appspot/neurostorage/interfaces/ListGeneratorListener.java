package org.appspot.neurostorage.interfaces;

import org.appspot.neurostorage.RecordNeuro;

import java.util.ArrayList;

public interface ListGeneratorListener {
  public void onDataSetUpdated(ArrayList<RecordNeuro> records);
}