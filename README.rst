===================
Android ADK Toolkit
===================

This toolkit helps beginners to be up and running with ADK 2012 without difficulties.
If you have any ideas to improve this toolkit, go to ``contribution`` section.

ADK toolkit exposes an ``AdkManager`` to manage ``UsbManager`` and ``UsbAccessory``. In this way
you don't need to fully understand some background concept about how ADK works but, as soon as possibile,
you should read `ADK official documentation`_.

.. _ADK official documentation: http://developer.android.com/tools/adk/adk2.html

Support
-------

If you need support please send a message to the `Android ADK Toolkit group`_.

.. _Android ADK Toolkit group: https://groups.google.com/forum/#!forum/android-adk-toolkit/

Contribution guidelines
-----------------------

If you want to contribute, just `follow the guidelines`_.

.. _follow the guidelines: http://android-adk-toolkit.readthedocs.org/en/latest/contributing.html

Usage
-----

Gradle dependency
~~~~~~~~~~~~~~~~~

This library is available on ``MavenCentral`` and you can add it to your ``build.gradle``::

    dependencies {
        compile 'me.palazzetti:adktoolkit:0.2.0'
    }

AndroidManifest.xml
~~~~~~~~~~~~~~~~~~~

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

Java code
~~~~~~~~~

To use this toolkit initialize an AdkManager as follows:

.. code-block:: java

    private AdkManager mAdkManager;
    // ...
    mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));

and then use these methods to read/write to your accessory:

.. code-block:: java

    adkManager.sendText("Hello world!");
    String response = adkManager.readText();

Documentation
-------------

This README just provides basic information to show quickly how this library works. You can check
the `full documentation`_ on *Read the Docs*.

.. _full documentation: http://android-adk-toolkit.readthedocs.org/en/latest/

Change log
----------

0.2.0 [Not released]
~~~~~~~~~~~~~~~~~~~~

* ``FileInputStream`` and ``FileOutputStream`` are ``protected`` so they can be mocked easily during testing
* Testing with `Mockito`_

**Bugfixes**

* Better input/output stream management to avoid NullPointerException on Accessory loading

**Backwards incompatible changes in 0.2.0**

* Some class/method names are misleading so readText/sendText become readSerial/writeSerial and closeAdk/resumeAdk become close/open
* ``AdkReceiver`` has been removed because the actual implementation of read/write can handle multiple char

.. _Mockito: https://github.com/mockito/mockito

0.1.0 [2014-02-05]
~~~~~~~~~~~~~~~~~~

* ADK fast constructor
* Simple default implementation of Broadcast receiver and IntentFilter
* Writing and reading features available
* Simple AsyncTask support

Example projects
----------------

* `Android ADK rover`_
* `UDOO light bulb`_

.. _Android adk rover: https://github.com/palazzem/android-udoo-rover
.. _UDOO light bulb: https://github.com/palazzem/udoo-adk-lightbulb

License
-------

* Application code: FreeBSD (see ``LICENSE`` file)
