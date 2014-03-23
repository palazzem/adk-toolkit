========
Overview
========

Accessory Development Kit allows you to build USB and Bluetooth accessories to extend the
capabilities of your user's Android-powered devices. Android defines a standard protocol (`AOA`_)
that you can implement in your accessories and have it compatible with a wide range of Android devices.

.. _AOA: http://source.android.com/accessories/protocol.html

Compatible Android devices
--------------------------

Android Open Accessory support is included in Android 3.1 (API Level 12) and higher, and supported
through an Add-On Library in Android 2.3.4 (API Level 10) and higher. Check `official documentation`_
for more information.

.. warning::
    If your Android device uses API level 12+, it doesn't mean that it is ADK compatible. You should
    always check your smartphone/board specifications to see if they have this support.

.. _official documentation: http://source.android.com/accessories/index.html

Compatible Arduino devices
--------------------------

Your accessory should implement many features as described in `building custom accessories`_ section.
Many Arduino devices have a built-in support for AOA protocol and there is a small list of what is
supported:

* `Arduino ADK`_
* `Arduino Due`_
* `UDOO board`_

.. note::
    This list is community driven. There I will list **only** devices that me or other
    contributors have used. If you are pretty sure that other boards have this support, follow
    the contribution guidelines.

.. _building custom accessories: http://source.android.com/accessories/custom.html
.. _Arduino ADK: http://arduino.cc/en/Main/ArduinoBoardADK
.. _Arduino Due: http://arduino.cc/en/Main/ArduinoBoardDue
.. _UDOO board: http://www.udoo.org/
