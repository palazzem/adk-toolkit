=====
Usage
=====

ADK 2012 is the latest version you can use to develop amazing accessories for Android-powered
devices. This library only works as a wrapper of all ADK features for your Android app. If you
want to see how ADK 2012 works, follow `official documentation`_.

.. note::
    Even if this library is a wrapper and supports Android API level 10, I will only target API level
    12 in this documentation as stated in `cutting down backward support`_ issue.

.. _official documentation: http://developer.android.com/tools/adk/adk2.html
.. _cutting down backward support: https://github.com/palazzem/adk-toolkit/issues/2

Install
-------

This library is available in MavenCentral repository and its deployment flow is based on SonaType
Nexus repository. To use the library simply configure your Gradle or Maven dependencies as follows:

Gradle
~~~~~~

.. code-block:: groovy

    dependencies {
        compile 'me.palazzetti:adktoolkit:0.2.0'
    }

Maven
~~~~~

.. code-block:: xml

    <dependency>
        <groupId>me.palazzetti</groupId>
        <artifactId>adktoolkit</artifactId>
        <version>0.2.0</version>
        <type>aar</type>
    </dependency>

Eclipse users
~~~~~~~~~~~~~

All published libraries in MavenCentral are in AAR format.
Unfortunately, `Eclipse seems to have a bug`_ and AAR import will not work as expected. However
there is an assemble task to produce a JAR library. To create the library simply launch in your
root folder:

.. code-block:: bash

    $ ./gradlew assembleJar

This will create a JAR library inside ``adktoolkit/build/libs/`` folder. Pre-assembled libraries
are available in `GitHub release section`_.

.. _Eclipse seems to have a bug: https://code.google.com/p/android/issues/detail?id=59183
.. _GitHub release section: https://github.com/palazzem/adk-toolkit/releases

Android Manifest
----------------

Create ``res/xml/usb_accessory_filter.xml`` configuration file to identify your accessory:

.. code-block:: xml

    <resources>
        <usb-accessory
            version="0.1.0"
            model="External-Droid"
            manufacturer="Example, Inc."/>
    </resources>

Declare in your manifest that your application requires USB accessory support:

.. code-block:: xml

    <manifest>
        <uses-feature android:name="android.hardware.usb.accessory" android:required="true"/>

        <!-- ... -->
    </manifest>

Then add in your activity block this ADK intent filter:

.. code-block:: xml

    <manifest>
        <!-- ... -->

        <!-- Adk Intent Filter -->
        <intent-filter>
            <action android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED" />
        </intent-filter>

        <meta-data android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED"
            android:resource="@xml/usb_accessory_filter"/>
    </manifest>

Android code
------------

To use this toolkit initialize the ``AdkManager`` in your Activity during ``onCreate()`` method:

.. code-block:: java

    private AdkManager mAdkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));
    }

If you need to register a ``BroadcastReceiver`` to catch ``UsbManager.ACTION_USB_ACCESSORY_DETACHED``
action, you can use library default implementation as follows (always in your ``onCreate()`` method):

.. code-block:: java

    registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());

Starting and stopping ADK listener
----------------------------------

When you initialize an ``AdkManager``, it just create a connection object between your device and your
accessory. You need to start/stop AOA communication when you open/close your activity. Add these calls
in your ``onResume()`` and ``onPause()`` methods:

.. code-block:: java

    @Override
    protected void onPause() {
        super.onPause();
        mAdkManager.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdkManager.open();
    }

.. note::
    If you need to leave the activity without stopping the communication, you can avoid ``mAdkManager.close()``.
    However don't forget to close the communication with a widget or a button in your activity so
    users can disable the accessory when they want. This avoid useless battery consumption.

Write and read serial text
--------------------------

As I write in my unittest, you can simply:

.. code-block:: java

    adkManager.writeSerial("Hello world!");
    String readValue = adkManager.readSerial();
    assertEquals("Hello world!", readValue);
    // Not bad! :)

``writeSerial()`` allows you to write a single char or a String object.

``readSerial()`` read a single char or a String object until there are bytes to read in the buffer

.. note::
    ``readSerial()`` could be a long-running task (ex: you want to continuously read data from a
    thermal sensor). In this case, put ``readSerial()`` call inside a ``Service`` or an ``AsyncTask``
    and don't run this in your UI main thread.
