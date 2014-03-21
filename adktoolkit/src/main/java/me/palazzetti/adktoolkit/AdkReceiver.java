package me.palazzetti.adktoolkit;

import android.os.AsyncTask;

/**
 * AsyncTask to listen ADK serial communication port
 */
public abstract class AdkReceiver extends AsyncTask<AdkManager, String, Void> {

    @Override
    protected Void doInBackground(AdkManager... params) {
        AdkManager adkManager = params[0];

        while (adkManager.serialAvailable()) {
            publishProgress(adkManager.readSerial());
        }
        return null;
    }
}
