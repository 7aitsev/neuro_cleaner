package org.appspot.neurostorage.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.appspot.neurostorage.R;

public class BottomSheet extends Dialog {

  public BottomSheet(Context context, int resource) {
    super(context, resource);
    View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
    setContentView(view);
    setCanceledOnTouchOutside(true);
    getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
    getWindow().setGravity(Gravity.BOTTOM);

    TextView txtRemove = (TextView) view.findViewById(R.id.txt_remove);
    view.findViewById(R.id.option_remove).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
    view.findViewById(R.id.option_selection).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
    TextView txtSelection = (TextView) view.findViewById(R.id.txt_selection);
  }
}
