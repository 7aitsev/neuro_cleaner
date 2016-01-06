package org.appspot.neurostorage;

import java.util.Date;

public class RecordNeuro {
  private long mSize;
  private Date mDate;
  private String mName;
  private String mPath;
  private double mFactor;
  private boolean mChecked;

  public RecordNeuro(final long size, final Date date, final String name, final String path) {
    mName = name;
    mPath = path;
    mSize = size;
    mDate = date;
    mChecked = true;
  }

  public final long getSize() {
    return mSize;
  }

  public final Date getDate() {
    return mDate;
  }

  public final String getName() {
    return mName;
  }

  public final String getPath() {
    return mPath;
  }

  public final double getFactor() {
    return mFactor;
  }

  public void setFactor(double factor) {
    mFactor = factor;
  }

  public void setChecked(boolean checked) {
    mChecked = checked;
  }

  public final boolean isChecked() {
    return mChecked;
  }
  
//  public class ImmutableFactor {
//    final private double mFactor;
//
//    ImmutableFactor(double factor) {
//      mFactor = factor;
//    }
//
//    public final double getFactor() {
//      return mFactor;
//    }
//  }
}