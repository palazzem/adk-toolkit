========
Overview
========

The `Accessory Development Kit (ADK)`_ allows you building USB or Bluetooth accessories that extend
the capabilities of your user's Android-powered devices. Android defines the
`Android Open Accessory Protocol (AOA)`_ used in accessories to create a communication channel
between your Android application and your ADK compatible device.

.. _Accessory Development Kit (ADK): http://developer.android.com/tools/adk/adk2.html
.. _Android Open Accessory Protocol (AOA): http://source.android.com/accessories/protocol.html

Compatible Android devices
--------------------------

Android Open Accessory support is included since Android 3.1 (API Level 12), but a porting through
an Add-On Library was available even in Android 2.3.4 (API Level 10).
Check the `official documentation`_ for more information.

.. warning::
    Even if your Android device uses an API level 12+, it doesn't mean that it is ADK compatible.
    The ADK support is related to some hardware specifications that your smartphone / board
    manufacturer should comply. Before you proceed, check if your smartphone or board have a full
    ADK support.

.. _official documentation: http://source.android.com/accessories/index.html

Compatible ADK devices
----------------------

Your accessory should implement many features as described in `building custom accessories`_
section. Many Arduino devices have a built-in support for ADK (and so AOA protocol) and the
following is a list of supported devices:

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
