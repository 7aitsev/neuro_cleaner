package org.appspot.neurostorage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class RecordNeuro implements Parcelable {
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

  // let's make it parcelable

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(mSize);
    dest.writeLong(mDate.getTime());
    dest.writeString(mName);
    dest.writeString(mPath);
    dest.writeDouble(mFactor);
    dest.writeByte((byte) (mChecked ? 1 : 0));
  }

  public static final Parcelable.Creator<RecordNeuro> CREATOR =
    new Parcelable.Creator<RecordNeuro>() {
      public RecordNeuro createFromParcel(Parcel in) {
        return new RecordNeuro(in);
      }
      public RecordNeuro[] newArray(int size) {
        throw new UnsupportedOperationException();
      }
    };

  private RecordNeuro(Parcel source) {
    mSize = source.readLong();
    mDate = new Date(source.readLong());
    mName = source.readString();
    mPath = source.readString();
    mFactor = source.readDouble();
    mChecked = (source.readByte() == 1);
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