package me.palazzetti.adktoolkit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.ParcelFileDescriptor;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.mockito.Mockito.*;

public class TestAdkManager extends ActivityUnitTestCase<MockActivity> {
    Activity mockActivity;
    UsbAccessory mockAccessory;
    ParcelFileDescriptor mockFileDescriptor;
    FileInputStream mockFileInputStream;
    FileOutputStream mockFileOutputStream;

    AdkManager adkManager = null;

    public TestAdkManager() {
        super(MockActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Workaround to solve dexmaker bug. See https://code.google.com/p/dexmaker/issues/detail?id=2
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

        // Mock read/write streams so serial communication with accessory is mocked with a temporary file
        File mockMemoryReadWrite = File.createTempFile("read_write_memory_mock", null, getInstrumentation().getTargetContext().getCacheDir());
        mockFileOutputStream = new FileOutputStream(mockMemoryReadWrite);
        mockFileInputStream = new FileInputStream(mockMemoryReadWrite);

        // Launch unit test activity
        Intent mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), MockActivity.class);
        startActivity(mLaunchIntent, null, null);
        mockActivity = getActivity();

        // Spy objects
        UsbManager usbManager = (UsbManager) mockActivity.getSystemService(Context.USB_SERVICE);
        UsbManager spyUsbManager = spy(usbManager);
        mockAccessory = mock(UsbAccessory.class);
        mockFileDescriptor = mock(ParcelFileDescriptor.class);

        doReturn(new UsbAccessory[]{mockAccessory}).when(spyUsbManager).getAccessoryList();
        doReturn(mockFileDescriptor).when(spyUsbManager).openAccessory(mockAccessory);

        adkManager = new AdkManager(spyUsbManager);
        adkManager.open();

        // Mock Input stream with in memory write
        adkManager.setFileInputStream(mockFileInputStream);
        adkManager.setFileOutputStream(mockFileOutputStream);
    }

    @Override
    protected void tearDown() throws Exception {
        adkManager.close();
    }

    @SmallTest
    public void testAdkManagerInit() throws Exception {
        assertNotNull(adkManager);
        assertEquals(adkManager.getDetachedFilter() instanceof IntentFilter, true);
        assertEquals(adkManager.getUsbReceiver() instanceof BroadcastReceiver, true);
    }

    @SmallTest
    public void testUsbAccessoryConnection() throws Exception {
        // NOTE: this test is required to be sure that AdkManger has a valid UsbManager mock
        assertEquals(adkManager.getUsbManager().getAccessoryList().length, 1);
    }

    @SmallTest
    public void testWriteString() throws Exception {
        adkManager.writeSerial("It works!");
    }

    @SmallTest
    public void testWriteByte() throws Exception {
        adkManager.writeSerial(42);
    }

    @SmallTest
    public void testReadSerial() throws Exception {
        adkManager.writeSerial("Hello world!");
        String readValue = adkManager.readSerial();

        assertEquals("Hello world!", readValue);
    }

    @SmallTest
    public void testReadString() throws Exception {
        adkManager.writeSerial("Hello world!");
        String readValue = adkManager.readString();

        assertEquals("Hello world!", readValue);
    }

    @SmallTest
    public void testReadByte() throws Exception {
        adkManager.writeSerial(42);
        byte readValue = adkManager.readByte();

        assertEquals(42, readValue);
    }

    @SmallTest
    public void testContinuousReadsByte() throws Exception {
        adkManager.writeSerial(42);
        byte readValue = adkManager.readByte();

        assertEquals(42, readValue);

        readValue = adkManager.readByte();
        assertEquals(0, readValue);

        adkManager.writeSerial(42);
        readValue = adkManager.readByte();
        assertEquals(42, readValue);
    }
}
