package me.palazzetti.adktoolkit.response;


/**
 * Provides a class to manage given response
 * from ADK read
 */

public class AdkMessage {
    private byte[] mReceivedBuffer = null;
    private String mString = null;
    private Byte mByte = null;
    private Float mFloat = null;
    private Integer mInt = null;

    public AdkMessage(byte[] buffer) {
        this.mReceivedBuffer = buffer;
    }

    public byte[] getBytes() {
        return mReceivedBuffer;
    }

    public String getString() {
        if (mString == null) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < mReceivedBuffer.length; i++) {
                stringBuilder.append((char) mReceivedBuffer[i]);
            }

            if ("".equals(stringBuilder.toString())) {
                mString = null;
            } else {
                mString = stringBuilder.toString();
            }
        }

        return mString;
    }

    public Byte getByte() {
        if (mByte == null) {
            try {
                mByte = mReceivedBuffer[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                mByte = null;
            }
        }

        return mByte;
    }

    public Float getFloat() {
        if (mFloat == null) {
            try {
                mFloat = Float.parseFloat(getString());
            } catch (NumberFormatException e) {
                mFloat = null;
            }
        }

        return mFloat;
    }

    public Integer getInt() {
        if (mInt == null) {
            try {
                mInt = Integer.parseInt(getString());
            } catch (NumberFormatException e) {
                mInt = null;
            }
        }

        return mInt;
    }
}
