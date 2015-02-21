===================
Android ADK Toolkit
===================

.. image:: https://readthedocs.org/projects/android-adk-toolkit/badge/?version=latest
    :target: http://docs.adktoolkit.org/
    :alt: Documentation Status

.. image:: https://img.shields.io/badge/Android%20Arsenal-Android%20ADK%20Toolkit-brightgreen.svg?style=flat
    :target: https://android-arsenal.com/details/1/1266

.. image:: https://img.shields.io/badge/API-12%2B-brightgreen.svg?style=flat
    :target: https://android-arsenal.com/api?level=12

.. image:: https://api.bintray.com/packages/bintray/jcenter/me.palazzetti%3Aadktoolkit/images/download.svg
    :target: https://bintray.com/bintray/jcenter/me.palazzetti%3Aadktoolkit/_latestVersion

This toolkit helps beginners to be up and running with ADK 2012 without difficulties.
If you have any ideas to improve this toolkit, go to ``contribution`` section.

ADK toolkit exposes an ``AdkManager`` to manage ``UsbManager`` and ``UsbAccessory``. In this way
you don't need to fully understand any background concepts about how ADK works. Anyhow don't forget
to read the `ADK official documentation`_.

.. _ADK official documentation: http://developer.android.com/tools/adk/adk2.html

Support
-------

If you need support please send a message to the `Android ADK Toolkit group`_.

.. _Android ADK Toolkit group: https://groups.google.com/forum/#!forum/android-adk-toolkit/

Contribution guidelines
-----------------------

If you want to contribute, just `follow the guidelines`_.

.. _follow the guidelines: http://docs.adktoolkit.org/en/latest/contributing.html

Usage
-----

**Note**: full documentation has more usage options. Check `Usage section`_ for more details.

.. _Usage section: http://docs.adktoolkit.org/en/latest/usage.html

Gradle dependency
~~~~~~~~~~~~~~~~~

This library is available on ``MavenCentral`` and `JCenter`_ (which is now the default repository
used in Android) so you can add this dependency directly in your ``build.gradle``::

    dependencies {
        compile 'me.palazzetti:adktoolkit:0.3.0'
    }

.. _JCenter: https://bintray.com/bintray/jcenter

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

Java code
~~~~~~~~~

To use this toolkit initialize an ``AdkManager`` in your ``Activity`` ``onCreate`` callback and then
open your accessory in the ``onResume`` callback:

.. code-block:: java

    private AdkManager mAdkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        mAdkManager = new AdkManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdkManager.open();
    }

You can use the below methods to access your accessory:

.. code-block:: java

    // Write
    mAdkManager.write("Hello from Android!");

    // Read
    AdkMessage response = mAdkManager.read();
    System.out.println(response.getString());
    // Could outputs: "Hello from Arduino!"

Documentation
-------------

This README just provides basic information to show quickly how this library works. You can check
the `full documentation`_ on *Read the Docs*.

.. _full documentation: http://docs.adktoolkit.org/

Change log
----------

0.3.0 [2015-01-10]
~~~~~~~~~~~~~~~~~~

**New features**

* Updated to latest gradle version ``1.0.0``
* Added ``AdkMessage`` class, which exposes the raw ``byte[]`` array with some utility methods to get string, byte, int and float representations
* Issue `#13`_: refactoring ``AdkManager`` to expose a common interface for ``read()`` and ``write()``
* Issue `#16`_: ``AdkManager`` constructor now accept an ``Activity`` context to initialize the accessory

**Backwards incompatible changes from 0.2.x**

* removed ``writeSerial(String text)``
* removed ``writeSerial(int value)``
* removed ``readSerial()``
* removed ``readString()``
* removed ``readByte()``

.. _#13: https://github.com/palazzem/adk-toolkit/issues/13
.. _#16: https://github.com/palazzem/adk-toolkit/issues/16

0.2.1 [2014-10-14]
~~~~~~~~~~~~~~~~~~

* ``writeSerial`` now accept both ``byte`` and ``String`` values
* ``readSerial`` is now **deprecated** and default to ``readString`` method
* Added ``readString`` and ``readByte`` so you can read ``String`` and ``byte`` values from the serial port

**Bugfixes**

* Fixed documentation: `#9`_

.. _#9: https://github.com/palazzem/adk-toolkit/issues/9

0.2.0 [2014-03-24]
~~~~~~~~~~~~~~~~~~

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

Projects that use ADKToolkit
----------------------------

* `Android ADK rover`_
* `UDOO light bulb`_
* `Mobile tanker`_ an Android application powered by `OpenCV`_

.. _Android adk rover: https://github.com/palazzem/android-udoo-rover
.. _UDOO light bulb: https://github.com/palazzem/udoo-adk-lightbulb
.. _Mobile tanker: https://github.com/DMIAlumni/mobile-tanker
.. _OpenCV: http://opencv.org/

License
-------

* Application code: FreeBSD (see ``LICENSE`` file)
