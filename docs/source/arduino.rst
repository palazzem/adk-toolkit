=====================
Arduino communication
=====================

Your Android application needs an Arduino sketch as a counterpart. Here you can find a basic
template that shows how to initialize your **accessory descriptor** to let Android knows that
an accessory is connected to the system:

.. code-block:: c

    #include <adk.h>

    #define RCVSIZE 128

    // Accessory descriptor. It's how Arduino identifies itself in Android.
    char accessoryName[] = "Terminal echo";
    char manufacturer[] = "Example, Inc.";
    char model[] = "Terminal-echo";

    char versionNumber[] = "0.1.0";
    char serialNumber[] = "1";
    char url[] = "http://www.example.com";

    USBHost Usb;
    ADK adk(&Usb, manufacturer, model, accessoryName, versionNumber, url, serialNumber);
    uint8_t buffer[RCVSIZE];
    uint32_t readBytes = 0;

    void setup() {
        // Some setup operations
    }

Arduino and the Android Manifest
--------------------------------

Accessory descriptor defines how your accessory identifies itself with the Android system. These
values should be the same defined in the ``res/xml/usb_accessory_filter.xml`` file otherwise your
accessory will not find a suitable application to communicate with:

* ``versionNumer``
* ``model``
* ``manufacturer``

You can use ``url`` variable to open an external link when any application capable to manage
this accessory is found. In this web page you can provide more information about how to configure
your accessory.

.. note::
    You can also target a ``.apk`` package or a Google Play Store URL where users can download
    and install your app.

Interacting with Android
------------------------

When you're connecting your accessory to Android you can have the following scenarios:

* You want to collect data from your accessory (maybe some sensors) and send them back to your
Android application
* You want to provide to users an interaction, using the Android user interface, and do some actions
with accessory actuators

Despite what is the scope of your accessory, the following are brief examples how you can read and
write data from your Arduino sketch.

Reading
~~~~~~~

Within your ``loop()`` function, add the following code to read data from the ADK buffer:

.. code-block:: c

    // readBytes, RCVSIZE and buffer are declared in the first snippet

    void loop() {
        Usb.Task();

        if (adk.isReady()){
            adk.read(&readBytes, RCVSIZE, buffer);
            if (readBytes > 0){
                // Do something with buffer
            }
        }
        // Don't forget a delay in your sketch :)
    }

Writing
~~~~~~~

If you want to send data to your Android device, use the following snippet:

.. code-block:: c

    // buffer is already declared in the first snippet

    void loop() {
        Usb.Task();

        if (adk.isReady()){
            adk.write(sizeof(buffer), buffer);
        }
        // Don't forget a delay in your sketch :)
    }

Remember that if the accessory needs both the writing and the reading phase, it could be a good idea
to use two different ``buffer`` objects; for this reason you may want to declare two ``uint8_t``
buffers.

Simple echo sketch
------------------

You can use this sketch to create an echo accessory which sends received characters back to Android:

.. code-block:: c

    uint8_t readingBuffer[RCVSIZE];
    uint8_t writingBuffer[RCVSIZE];

    void setup() {
        // Nothing to do with this sketch
    }

    void loop() {
        Usb.Task();

        if (adk.isReady()){
            adk.read(&readBytes, RCVSIZE, readingBuffer);
            if (readBytes > 0){
                adk.write(readBytes, writingBuffer);
            }
        }
    }
