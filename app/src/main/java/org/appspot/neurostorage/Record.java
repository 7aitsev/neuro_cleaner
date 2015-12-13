package org.appspot.neurostorage;

import java.util.Date;

public class Record {
  final public long mSize;
  final public Date mDate;
  final public String mName;
  final public String mPath;
  public double mFactor;

  Record(final long size, final Date date, final String name, final String path) {
    mName = name;
    mPath = path;
    mSize = size;
    mDate = date;
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