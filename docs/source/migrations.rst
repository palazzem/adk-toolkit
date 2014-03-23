==============================
Migrate from previous versions
==============================

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
