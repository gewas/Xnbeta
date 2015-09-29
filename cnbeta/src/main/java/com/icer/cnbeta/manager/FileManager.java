package com.icer.cnbeta.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.icer.cnbeta.app.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by icer on 2015-09-29.
 */
public class FileManager {

    public static final String TAG = FileManager.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private static FileManager appFileManager;

    private FileManager() {

    }

    public static FileManager getInstance() {
        if (appFileManager == null)
            appFileManager = new FileManager();
        return appFileManager;
    }

//    public void saveFile2InternalStorage(Context context, String fileName, InputStream is) {
//        File file = new File(context.getFilesDir(), fileName);
//        if (file.exists() && file.length() > 0)
//            return;
//        else {
//            FileOutputStream fos = null;
//            try {
//                fos = context.openFileOutput(file.getAbsolutePath(), Context.MODE_PRIVATE);
//                byte[] buf = new byte[512];
//                int offset;
//                long count = 0;
//                while ((offset = is.read(buf)) != -1) {
//                    count++;
//                    fos.write(buf, 0, offset);
//                    if (count % 8 == 0)
//                        fos.flush();
//                }
//                fos.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (fos != null)
//                        fos.close();
//                    if (is != null)
//                        is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public void saveBitmap2InternalStorage(final Context context, final String fileName, final Bitmap bitmap) {
        final File file = new File(context.getFilesDir(), fileName.hashCode() + "");
        if (file.exists() && file.length() > 0)
            return;
        new AsyncTask<Boolean, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(Boolean... params) {
                boolean res = false;
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG")
                            || fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG")) {
                        ((BaseActivity) context).logI(TAG, "Bitmap " + fileName + " saved in type: JPEG");
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } else {
                        ((BaseActivity) context).logI(TAG, "Bitmap " + fileName + " saved in type: PNG");
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    }
                    res = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return res;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean)
                    ((BaseActivity) context).logI(TAG, "Bitmap " + fileName + " saved in: " + file.getAbsolutePath());
            }
        }.execute();
    }

    public File findFileWithinInternalStorage(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists() && file.length() > 0)
            return file;
        return null;
    }

}
