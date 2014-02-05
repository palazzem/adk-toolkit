===================
Android ADK Toolkit
===================

**DISCLAIMER**

This toolkit aims to help beginners to be up and running with ADK 2012 without any difficulties.
If you have any ideas to improve this toolkit, go to ``contribution`` section.

ADK toolkit exposes an ``AdkManager`` to manage ``UsbManager`` and ``UsbAccessory``. In this way
you don't need to fully understand some background concept about how ADK works but, as soon as possibile,
you should read `ADK official documentation`_.

.. _ADK official documentation: http://developer.android.com/tools/adk/adk2.html

Usage
-----

AndroidManifest.xml
~~~~~~~~~~~~~~~~~~~

Declare in your manifest that your application will use an USB accessory::

    <manifest ...>
        <uses-feature android:name="android.hardware.usb.accessory" android:required="true"/>
    <!-- ... -->

Add in your Activity ADK intent filter declaration::

    <activity ...>
        <!-- ... -->

        <!-- Adk Intent Filter -->
        <intent-filter>
            <action android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED" />
        </intent-filter>
        <meta-data android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED"
            android:resource="@xml/usb_accessory_filter"/>
    </activity>

Done!

Java code
~~~~~~~~~

To use this toolkit simply declare and AdkManager and register a new ``BroadcastReceiver`` and
``IntentFilter`` as follows::

    private AdkManager mAdkManager;
    // ...
    mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));
    registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());

``getUsbReceiver()`` and ``getDetachedFilter()`` simply offer a default implementation to close
ADK when a ``UsbManager.ACTION_USB_ACCESSORY_DETACHED`` is caught.

Send and read serial text
-------------------------

It's really easy::

    adkManager.sendText("Hello world!");
    String response = adkManager.readText();

Done!

Reading a buffered response
---------------------------

``readText()`` will only ready a single serial byte. If you want to get more bytes to compose a String object,
you should create a new ``Thread`` which read serial data until bytes are available. However you should do
it in Android way so you need to create a ``Service`` or ``AsyncTask`` to manage continuous read.

To reduce complexity an abstract ``AdkReceiver`` is available and you can extend it without overriding
``doInBackground`` method. Implemented background task simply reads from serial and ``publishProgress`` of read byte.

Change log
----------

0.1.0 [2014-02-05]
~~~~~~~~~~~~~~~~~~

* ADK fast constructor
* Simple default implementation of Broadcast receiver and IntentFilter
* Writing and reading features available
* Simple AsyncTask to return read bytes

Roadmap
-------

* Better AsyncTask which uses StringBuilder to publishProgress of aa String object as result
* Simple implementation of a Service

Contribution guidelines
-----------------------

Available soon.

Example projects
----------------

* `UDOO light builb`_
* `Android ADK rover`_

.. _UDOO light builb: https://github.com/palazzem/udoo-adk-lightbulb
.. _Android adk rover: https://github.com/palazzem/android-udoo-rover


License
-------

* Application code: FreeBSD (see ``LICENSE`` file)
