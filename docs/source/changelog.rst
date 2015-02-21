=========
Changelog
=========

0.3.0 [2015-01-10]
------------------

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
------------------

* ``writeSerial`` now accept both ``byte`` and ``String`` values
* ``readSerial`` is now **deprecated** and default to ``readString`` method
* Added ``readString`` and ``readByte`` so you can read ``String`` and ``byte`` values from the serial port

**Bugfixes**

* Fixed documentation: `#9`_

.. _#9: https://github.com/palazzem/adk-toolkit/issues/9

0.2.0 [2014-03-24]
------------------

* ``FileInputStream`` and ``FileOutputStream`` are ``protected`` so they can be mocked easily during testing
* Testing with `Mockito`_

**Bugfixes**

* Better input/output stream management to avoid NullPointerException on Accessory loading

**Backwards incompatible changes in 0.2.0**

* Some class/method names are misleading so readText/sendText become readSerial/writeSerial and closeAdk/resumeAdk become close/open
* ``AdkReceiver`` has been removed because the actual implementation of read/write can handle multiple char

.. _Mockito: https://github.com/mockito/mockito

0.1.0 [2014-02-05]
------------------

* ADK fast constructor
* Simple default implementation of Broadcast receiver and IntentFilter
* Writing and reading features available
* Simple AsyncTask support
