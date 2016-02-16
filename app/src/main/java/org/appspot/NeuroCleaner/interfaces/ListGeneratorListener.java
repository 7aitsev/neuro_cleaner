package org.appspot.NeuroCleaner.interfaces;

import org.appspot.NeuroCleaner.RecordNeuro;

import java.util.ArrayList;

public interface ListGeneratorListener {
  public void onDataSetUpdated(ArrayList<RecordNeuro> records);
}