=====
Usage
=====

ADK 2012 is the latest version that can be used during accessories development. This library works
as a wrapper of ADK capabilities so your application development will be smoothest. If you
want to see how ADK 2012 works, follow the `official documentation`_.

.. note::
    Even if ADK is supported since Android API level 10, I will only target API level 12 in this
    library as stated in `cutting down backward support`_ issue.

.. _official documentation: http://developer.android.com/tools/adk/adk2.html
.. _cutting down backward support: https://github.com/palazzem/adk-toolkit/issues/2

Installing the library
----------------------

This library is available in MavenCentral and JCenter repositories. Adding the library dependency
is pretty easy and you can configure your Gradle or Maven dependency file as follows:

Gradle
~~~~~~

.. code-block:: groovy

    dependencies {
        compile 'me.palazzetti:adktoolkit:0.3.0'
    }

Maven
~~~~~

.. code-block:: xml

    <dependency>
        <groupId>me.palazzetti</groupId>
        <artifactId>adktoolkit</artifactId>
        <version>0.3.0</version>
        <type>aar</type>
    </dependency>

Eclipse users
~~~~~~~~~~~~~

All published libraries in MavenCentral or JCenter are in AAR format. Unfortunately,
`Eclipse seems to have a bug`_ and AAR import will not work as expected. I've created an assemble
task in the gradle build script to produce a JAR library that you can easily import manually in
your project. The pre-assembled libraries are available in the repository `release section`_.

.. note::
    If you are using Eclipse with ADT, be aware that Android Studio is now the official IDE for
    Android, so it's a good idea to migrate your projects to Android Studio. For help moving
    projects, see `Migrating to Android Studio`_. Despite that, ADKToolkit library will continue
    to support JAR library releases.

.. _Eclipse seems to have a bug: https://code.google.com/p/android/issues/detail?id=59183
.. _release section: https://github.com/palazzem/adk-toolkit/releases
.. _Migrating to Android Studio: http://developer.android.com/sdk/installing/migrate.html

Configuring the Android application
-----------------------------------

Android Manifest
~~~~~~~~~~~~~~~~

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

    <manifest ...>
        <application ...>
            <activity ...>

                <!-- ... -->

                <!-- Adk Intent Filter -->
                <intent-filter>
                    <action android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED" />
                </intent-filter>

                <meta-data android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED"
                    android:resource="@xml/usb_accessory_filter"/>
            </activity>
        </application>
    </manifest>

Starting the ADK listener
~~~~~~~~~~~~~~~~~~~~~~~~~

To use this library, initialize the ``AdkManager`` in your ``Activity`` ``onCreate()`` callback
like you can see in the following snippet:

.. code-block:: java

    private AdkManager mAdkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdkManager = new AdkManager(this);
    }

If you need to register a ``BroadcastReceiver`` to catch ``UsbManager.ACTION_USB_ACCESSORY_DETACHED``
action, you can use the library default implementations as follows:

.. code-block:: java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdkManager = new AdkManager(this);
        registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());
    }

When you initialize an ``AdkManager``, it just create a connection object between your device and
your accessory. You need to start/stop AOA communication when you open/close your activity. Add
the following calls in your ``onResume()`` and ``onPause()`` callbacks to open and close ADK
communication, when your Activity is resumed or paused:

.. code-block:: java

    @Override
    protected void onResume() {
        super.onResume();
        mAdkManager.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdkManager.close();
    }

.. warning::
    Because of an `internal ADK bug`_ that is still not fixed, it's not possible to open the ADK
    again when the channel has been closed. This means that if you need to use the ADK between
    activities, you should not call the ``close()`` method otherwise the only way to open the
    communication again is to restart your hardware accessory.

.. _internal ADK bug: https://github.com/palazzem/adk-toolkit/issues/11

Using the toolkit
~~~~~~~~~~~~~~~~~

The ADKToolkit library exposes an interface to write and read bytes in/from the internal ADK buffer.
If you need to send some values to your accessory, you can use the following methods within your
application code:

.. code-block:: java

    byte[] byteArray = {4, 2};
    byte byteValue = 42;
    int intValue = 42
    float floatValue = 42.0f;
    String stringValue = "Answer to The Ultimate Question of Life, the Universe, and Everything"

    adkManager.write(byteArray);
    adkManager.write(byteValue);
    adkManager.write(intValue);
    adkManager.write(floatValue);
    adkManager.write(stringValue);

On the other hand if you need to read a value from your accessory (for instance, a sensor value),
you can use the ``read()`` method that returns an ``AdkMessage`` instance. This class, wraps the
returned ``byte[]`` array from the buffer and exposes an API to parse retrieved value. The following
is an example how to read accessory data from your Android application:

.. code-block:: java

    AdkMessage response = mAdkManager.read();

Then you can call the following methods according to sent data:

* ``response.getBytes()``: returns the raw bytes array so you can manipulate it on your own
* ``response.getString()``: returns a string applying a ``(char)`` typecasting for each byte
* ``response.getByte()``: returns the first byte of the bytes array buffer
* ``response.getFloat()``: expects that the content of the bytes array buffer is a string; it calls the ``getString()`` method and tries to parse the string in a float value
* ``response.getInt()``: expects that the content of the bytes array buffer is a string; it calls the ``getString()`` method and tries to parse the string in an integer value
* ``response.isEmpty()``: returns ``true`` if the received buffer is empty or not initialized

If for any reasons the parsing causes an exceptions, it will be caught from the AdkResponse's
methods and the returned value will be ``null``.

.. note::
    The ``read()`` method could be a long-running task, in particular if you want to read
    continuously data from a your accessory. In this case, call the ``read()`` method outside your
    main thread otherwise it will cause the `Application Not Responding (ANR)`_ error. A good approach
    is to use an `IntentService`_, an `AsyncTask`_, or a `Runnable`_ implementation together
    with an `ExecutorService`_.

.. _Application Not Responding (ANR): http://developer.android.com/training/articles/perf-anr.html
.. _IntentService: http://developer.android.com/reference/android/app/IntentService.html
.. _AsyncTask: http://developer.android.com/reference/android/os/AsyncTask.html
.. _Runnable: https://developer.android.com/training/multiple-threads/define-runnable.html
.. _ExecutorService: http://developer.android.com/reference/java/util/concurrent/ExecutorService.html
