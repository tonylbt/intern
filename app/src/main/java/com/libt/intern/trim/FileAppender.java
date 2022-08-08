package com.libt.intern.trim;

import com.libt.intern.util.AndroidUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileAppender {
    private File mDestFile;
    private OutputStream mStream = null;

    public FileAppender(File file) throws Exception {
        this(file, true);
    }

    public FileAppender(File file, boolean append) throws Exception {
        mDestFile = file;
        if (!mDestFile.exists())
            mDestFile.createNewFile();
        mStream = new FileOutputStream(file, append);
    }

    public void flush() {
        try {
            mStream.flush();
        } catch (IOException e) {}
    }

    // note: must be called after removed from Logger
    public void close() {
        AndroidUtil.close(mStream);
    }

    public void writeToFile(String msg) {
        try {
            mStream.write(new String(msg.getBytes(), "UTF-8").getBytes());
            mStream.flush();
        } catch (Exception e) {}
    }
}
