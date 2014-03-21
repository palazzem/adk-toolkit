===================
Android ADK Toolkit
===================

**DISCLAIMER**

This toolkit helps beginners to be up and running with ADK 2012 without difficulties.
If you have any ideas to improve this toolkit, go to ``contribution`` section.

ADK toolkit exposes an ``AdkManager`` to manage ``UsbManager`` and ``UsbAccessory``. In this way
you don't need to fully understand some background concept about how ADK works but, as soon as possibile,
you should read `ADK official documentation`_.

.. _ADK official documentation: http://developer.android.com/tools/adk/adk2.html

Usage
-----

Gradle dependency
~~~~~~~~~~~~~~~~~

This library is available on ``MavenCentral`` and you can add it to your ``build.gradle``::

    dependencies {
        compile 'me.palazzetti:adktoolkit:0.1.0'
    }

AndroidManifest.xml
~~~~~~~~~~~~~~~~~~~

Declare in your manifest that your application will use an USB accessory:

.. code-block:: xml

    <manifest ...>
        <uses-feature android:name="android.hardware.usb.accessory" android:required="true"/>
        <!-- ... -->

Add in your Activity ADK intent filter declaration:

.. code-block:: xml

    <activity ...>
        <!-- ... -->

        <!-- Adk Intent Filter -->
        <intent-filter>
            <action android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED" />
        </intent-filter>
        <meta-data android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED"
            android:resource="@xml/usb_accessory_filter"/>
    </activity>

You need to create your accessory descriptor defined in the last code block (``res/xml/usb_accessory_filter.xml``).
You can use this template as an example:

.. code-block:: xml

    <resources>
        <usb-accessory
            version="0.1.0"
            model="External-Droid"
            manufacturer="Example, Inc."/>
    </resources>

Java code
~~~~~~~~~

To use this toolkit simply declare and AdkManager and register a new ``BroadcastReceiver`` and
``IntentFilter`` as follows:

.. code-block:: java

    private AdkManager mAdkManager;
    // ...
    mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));
    registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());

``getUsbReceiver()`` and ``getDetachedFilter()`` simply offer a default implementation to close
ADK when a ``UsbManager.ACTION_USB_ACCESSORY_DETACHED`` is caught.

Send and read serial text
-------------------------

It's really easy:

.. code-block:: java

    adkManager.sendText("Hello world!");
    String response = adkManager.readText();

Reading a buffered response
---------------------------

``readText()`` will only ready a single serial byte. If you want to get more bytes to compose a String object,
you should create a new ``Thread`` which read serial data until bytes are available. However you should do
it in Android way so you need to create a ``Service`` or ``AsyncTask`` to manage continuous read.

To reduce complexity an abstract ``AdkReceiver`` is available and you can extend it without overriding
``doInBackground`` method. Implemented background task simply reads from serial and ``publishProgress`` of read byte.

Change log
----------

0.2.0 [Not released]
~~~~~~~~~~~~~~~~~~~~

* ``FileInputStream`` and ``FileOutputStream`` are ``protected`` so they can be mocked easily during testing
* Testing with `Mockito`_

**Bugfixes**

* Better input/output stream management to avoid NullPointerException on Accessory loading

**Backwards incompatible changes in 0.2.0**

* Some class/method names are misleading so readText/sendText become readSerial/writeSerial

.. _Mockito: https://github.com/mockito/mockito

0.1.0 [2014-02-05]
~~~~~~~~~~~~~~~~~~

* ADK fast constructor
* Simple default implementation of Broadcast receiver and IntentFilter
* Writing and reading features available
* Simple AsyncTask support

Roadmap
-------

* Better AsyncTask which uses StringBuilder to publishProgress of a String object as result
* Service implementation

Contribution guidelines
-----------------------

Available soon.

Example projects
----------------

* `Android ADK rover`_
* `UDOO light bulb`_

.. _Android adk rover: https://github.com/palazzem/android-udoo-rover
.. _UDOO light bulb: https://github.com/palazzem/udoo-adk-lightbulb

License
-------

* Application code: FreeBSD (see ``LICENSE`` file)
