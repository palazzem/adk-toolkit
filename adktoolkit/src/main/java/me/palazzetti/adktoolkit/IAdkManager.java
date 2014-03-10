package me.palazzetti.adktoolkit;

/**
 * Defines ADK interfaces
 */

public interface IAdkManager {
    String readText();
    void sendText(String text);

    // Activity related interfaces
    void closeAdk();
    void resumeAdk();
}
