package org.appspot.neurostorage.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.appspot.neurostorage.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabExclusions extends Fragment {

  public TabExclusions() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.exclusions, container, false);;
    return rootView;
  }
}
