==============================
Migrate from previous versions
==============================

Migrate from 0.2.x to 0.3.0
---------------------------

Improvement
~~~~~~~~~~~

The old initialization uses the following code:

.. code-block:: java

    private AdkManager mAdkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));
    }

Now you can also create the AdkManager instance passing the Activity ``Context`` like you see
in the following code:

.. code-block:: java

    private AdkManager mAdkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdkManager = new AdkManager(this);
    }

Incompatibility
~~~~~~~~~~~~~~~

The following methods have been deleted:

* ``writeSerial(String text)``
* ``writeSerial(int value)``
* ``readSerial()``
* ``readString()``
* ``readByte()``

For this reason you should rename in your project:

* ``writeSerial()`` to ``write()``
* ``readSerial()`` to ``read().getString()``
* ``readString()`` to ``read().getString()``
* ``readByte()`` to ``read().getByte()``

.. note::
    Remember that ``read()`` returns an ``AdkMessage`` instance and you may want to cache this
    response in a variable.

Migrate from 0.1.0 to 0.2.0
---------------------------

Incompatibility
~~~~~~~~~~~~~~~

Some class/method names are misleading so readText/sendText become readSerial/writeSerial and
closeAdk/resumeAdk become close/open.

Rename in your project:

* ``readText`` to ``readSerial``
* ``sendText`` to ``writeSerial``
* ``closeAdk`` to ``close``
* ``resumeAdk`` to ``open``

Incompatibility
~~~~~~~~~~~~~~~

``AdkReceiver`` has been removed because the actual implementation of read/write can handle
multiple char.

If you have some ``AsyncTask`` which extend ``AdkReceiver``, simply extend a regular AsyncTask and
add a valid doInBackground method as follows:

.. code-block:: java

    public class MyAsyncTask extends AsyncTask<AdkManager, String, Void> {

        @Override
        protected Void doInBackground(AdkManager... params) {
            AdkManager adkManager = params[0];
            publishProgress(adkManager.readSerial());

            return null;
        }

        // Follows your implementation of MyAsyncTask
    }
