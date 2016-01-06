package org.appspot.neurostorage;

public class RecordControl {
  private String mDirName;
  private long mDirSize;
  private String mDirPath;

  public RecordControl(String dirName, long dirSize, String dirPath) {
    mDirName = dirName;
    mDirSize = dirSize;
    mDirPath = dirPath;
  }

  public final String getDirName() {
    return mDirName;
  }

  public final long getDirSize() {
    return mDirSize;
  }

  public final String getDirPath() {
    return mDirPath;
  }
}