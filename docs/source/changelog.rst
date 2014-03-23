=========
Changelog
=========

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
