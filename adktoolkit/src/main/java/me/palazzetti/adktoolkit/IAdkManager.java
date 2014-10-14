package me.palazzetti.adktoolkit;

/**
 * Defines ADK interfaces
 */

public interface IAdkManager {
    String readSerial();
    void writeSerial(String text);
    void writeSerial(int value);

    // Activity related interfaces
    void close();
    void open();
}
