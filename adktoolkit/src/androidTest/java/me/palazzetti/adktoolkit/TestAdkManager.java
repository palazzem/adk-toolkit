package me.palazzetti.adktoolkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
        adkManager.resumeAdk();

        // Mock Input stream with in memory write
        adkManager.setFileInputStream(mockFileInputStream);
        adkManager.setFileOutputStream(mockFileOutputStream);
    }

    @Override
    protected void tearDown() throws Exception {
        adkManager.closeAdk();
    }

    @SmallTest
    public void testAdkManagerInit() throws Exception {
        assertNotNull(adkManager);
    }

    @SmallTest
    public void testAdkManagerWrite() throws Exception {
        adkManager.writeSerial("It works!");
    }

    @SmallTest
    public void testAdkManagerRead() throws Exception {
        adkManager.writeSerial("0");
        String readValue = adkManager.readSerial();

        assertEquals("0", readValue);
    }
}
