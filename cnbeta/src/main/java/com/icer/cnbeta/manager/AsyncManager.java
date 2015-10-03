package com.icer.cnbeta.manager;

import android.os.AsyncTask;

/**
 * Created by icer on 2015/9/30.
 */
public class AsyncManager {

    public static void postTask(final Runnable doInBackground, final Runnable doInForeground) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                if (doInBackground != null)
                    doInBackground.run();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (doInForeground != null)
                    doInForeground.run();
            }
        }.execute();
    }
}
