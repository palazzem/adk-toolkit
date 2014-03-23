=====================
Arduino communication
=====================

Your Android app needs an Arduino sketch as a counterpart. Here you can find a basic template:

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
        Serial.begin(115200);
        Serial.println("Ready to listen!");
        delay(2000);
    }

Arduino and Android Manifest
----------------------------

Accessory descriptor defines how your accessory identifies itself in your Android app. These values
should be the same as ones in ``res/xml/usb_accessory_filter.xml`` file otherwise your accessory will not
find a suitable app to communicate with:

* ``versionNumer``
* ``model``
* ``manufacturer``

You can use ``url`` variable to open an external link when any suitable application is found.
There you can provide more information about how to configure your accessory.
You can also target an apk or a Google Play Store URL where users can download and install your app.

Write and read serial text
--------------------------

You can use this snippet function to read text sent by your Android device:

.. code-block:: c

    void readFromAdk() {
        Usb.Task();

        if (adk.isReady()){
            adk.read(&readBytes, RCVSIZE, buffer);
            if (readBytes > 0){
                // Do something with buffer
            }
        }
    }

If you want to write something to your Android device, use this snippet:

.. code-block:: c

    void writeToAdk(char textToSend[]) {
        adk.write(sizeof(textToSend), (uint8_t*)textToSend);
    }

Then you can write text to Android device like this:

.. code-block:: c

    void loop() {
        Usb.Task();

        if (adk.isReady()){
            writeToAdk("Hello world!");
            delay(1000);
        }
    }

Simple echo sketch
------------------

You can use this sketch to create an echo accessory which resend to Android every received characters:

.. code-block:: c

    void setup() {
        Serial.begin(115200);
        Serial.println("Ready to listen!");
        delay(2000);
    }

    void loop() {
        Usb.Task();

        if (adk.isReady()){
            adk.read(&readBytes, RCVSIZE, buffer);
            if (readBytes > 0){
                adk.write(readBytes, buffer);
            }
        }
    }
