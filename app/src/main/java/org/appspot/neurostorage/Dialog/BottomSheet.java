package org.appspot.neurostorage.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.appspot.neurostorage.R;

public class BottomSheet extends Dialog {

  public BottomSheet(Context context, int resource, ViewGroup viewGroup) {
    super(context, resource);
    View view = getLayoutInflater().inflate(R.layout.bottom_sheet, viewGroup, true);
    TextView txtRemove = (TextView) view.findViewById(R.id.txt_remove);
    TextView txtSelection = (TextView) view.findViewById(R.id.txt_selection);
    setContentView(view);
    setCancelable(true);
    getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
    getWindow().setGravity(Gravity.BOTTOM);
  }
}
