package me.palazzetti.adktoolkit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Allows to manager your ADK Usb Interface. It exposes
 * methods to close/open connected accessory and to read/write
 * from/to it.
 */

public class AdkManager implements IAdkManager {
    private static final String LOG_TAG = "AdkManager";

    private UsbManager mUsbManager;
    private UsbAccessory mUsbAccessory;
    private ParcelFileDescriptor mParcelFileDescriptor;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    private IntentFilter mDetachedFilter;
    private BroadcastReceiver mUsbReceiver;

    private int mByteRead = 0;

    public AdkManager(UsbManager mUsbManager) {
        // Store Android UsbManager reference
        this.mUsbManager = mUsbManager;

        // Filter for detached events
        mDetachedFilter = new IntentFilter();
        mDetachedFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);

        // Broadcast Receiver
        mUsbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (action.equals(UsbManager.ACTION_USB_ACCESSORY_DETACHED)) {
                    UsbAccessory usbAccessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if (usbAccessory != null && usbAccessory.equals(mUsbAccessory)) {
                        closeAccessory();
                    }
                }
            }
        };
    }

    protected void openAccessory(UsbAccessory usbAccessory) {
        mParcelFileDescriptor = mUsbManager.openAccessory(usbAccessory);
        if (mParcelFileDescriptor != null) {
            mUsbAccessory = usbAccessory;
            FileDescriptor fileDescriptor = mParcelFileDescriptor.getFileDescriptor();

            if (fileDescriptor != null) {
                mFileInputStream = new FileInputStream(fileDescriptor);
                mFileOutputStream = new FileOutputStream(fileDescriptor);
            }
        }
    }

    protected void closeAccessory() {
        if (mParcelFileDescriptor != null) {
            try {
                mParcelFileDescriptor.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }

        mParcelFileDescriptor = null;
        mUsbAccessory = null;
    }

    @Override
    public String readSerial() {
        byte[] buffer = new byte[255];
        StringBuilder stringBuilder = new StringBuilder();

        try {
            mByteRead = mFileInputStream.read(buffer, 0, buffer.length);
            for (int i = 0; i < mByteRead; i++) {
                stringBuilder.append((char) buffer[i]);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return stringBuilder.toString();
    }

    public boolean serialAvailable() {
        return mByteRead >= 0;
    }

    @Override
    public void writeSerial(String text) {
        byte[] buffer = text.getBytes();

        try {
            mFileOutputStream.write(buffer);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void closeAdk() {
        closeAccessory();
    }

    @Override
    public void resumeAdk() {
        if (mFileInputStream == null || mFileOutputStream == null) {
            UsbAccessory[] usbAccessoryList = mUsbManager.getAccessoryList();
            if (usbAccessoryList != null && usbAccessoryList.length > 0) {
                openAccessory(usbAccessoryList[0]);
            }
        }
    }

    public IntentFilter getDetachedFilter() {
        return mDetachedFilter;
    }
    public BroadcastReceiver getUsbReceiver() {
        return mUsbReceiver;
    }

    // Protected methods used by tests
    // -------------------------------

    protected void setFileInputStream(FileInputStream fileInputStream) {
        this.mFileInputStream = fileInputStream;
    }

    protected void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.mFileOutputStream = fileOutputStream;
    }

    protected UsbManager getUsbManager() {
        return mUsbManager;
    }
}
