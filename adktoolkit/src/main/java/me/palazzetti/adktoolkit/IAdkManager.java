package me.palazzetti.adktoolkit;

/**
 * Defines ADK interfaces
 */

public interface IAdkManager {
    String readSerial();
    void writeSerial(String text);

    // Activity related interfaces
    void close();
    void open();
}
