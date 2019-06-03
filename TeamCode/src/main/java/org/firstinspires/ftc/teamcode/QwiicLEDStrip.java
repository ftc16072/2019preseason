package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;


@I2cDeviceType()
@DeviceProperties(name = "QWIIC LED Strip", description = "Sparkfun QWIIC LED Strip", xmlTag = "QWIIC_LED_STRIP")
public class QwiicLEDStrip extends I2cDeviceSynchDevice<I2cDeviceSynchSimple> {

    public enum Commands {
        CHANGE_LED_LENGTH(0x70),
        WRITE_SINGLE_LED_COLOR(0x71),
        WRITE_ALL_LED_COLOR(0x72),
        WRITE_RED_ARRAY(0x73),
        WRITE_GREEN_ARRAY(0x74),
        WRITE_BLUE_ARRAY(0x75),
        WRITE_SINGLE_LED_BRIGHTNESS(0x76),
        WRITE_ALL_LED_BRIGHTNESS(0x77),
        WRITE_ALL_LED_OFF(0x78);
        public int bVal;

        Commands(int bVal) {
            this.bVal = bVal;
        }
    }

    /*
     * Change the color of a specific LED
     * @param number which LED to change (1 - 255)
     * @param red    what the red value should be (0-255)
     * @param green  what the green value should be (0-255)
     * @param blue   what the blue value should be (0-255)
     */
    void setLEDColor(int number, int red, int green, int blue) {
        byte[] data = new byte[4];
        data[0] = (byte) number;
        data[1] = (byte) red;
        data[2] = (byte) green;
        data[3] = (byte) blue;
        deviceClient.write(Commands.WRITE_SINGLE_LED_COLOR.bVal, data, I2cWaitControl.WRITTEN);
    }

    /*
     * Change the color of all LEDs to a single color
     * @param red    what the red value should be (0-255)
     * @param green  what the green value should be (0-255)
     * @param blue   what the blue value should be (0-255)
     */
    void setAllLEDColor(int red, int green, int blue) {
        byte[] data = new byte[3];
        data[0] = (byte) red;
        data[1] = (byte) green;
        data[2] = (byte) blue;
        deviceClient.write(Commands.WRITE_ALL_LED_COLOR.bVal, data, I2cWaitControl.WRITTEN);
    }

    private void sendSegment(Commands cmd, int[] array, int offset, int length) {
        byte[] data = new byte[length + 2];
        data[0] = (byte) length;
        data[1] = (byte) offset;

        for (int i = 0; i < length; i++) {
            data[2 + i] = (byte) array[offset + i];
        }
        deviceClient.write(cmd.bVal, data, I2cWaitControl.WRITTEN);
    }

    /*
     * Change the color of an LED color segment
     * @param redArray    what the red value should be (0-255)
     * @param greenArray  what the green value should be (0-255)
     * @param blueArray   what the blue value should be (0-255)
     * @param offset      where in the array to start
     * @param length      length to send (limited to 12)
     */
    private void setLEDColorSegment(int[] redArray, int[] greenArray, int[] blueArray, int offset, int length) {
        sendSegment(Commands.WRITE_RED_ARRAY, redArray, offset, length);
        sendSegment(Commands.WRITE_GREEN_ARRAY, greenArray, offset, length);
        sendSegment(Commands.WRITE_BLUE_ARRAY, blueArray, offset, length);
    }

    /*
     * Change the color of all LEDs using arrays
     * @param redArray    what the red value should be (0-255)
     * @param greenArray  what the green value should be (0-255)
     * @param blueArray   what the blue value should be (0-255)
     * @param length      the size of the array
     */
    void setLEDColor(int[] redArray, int[] greenArray, int[] blueArray, int length) {
        int numInLastSegment = length % 12;
        int numSegments = length / 12;
        for (int i = 0; i < numSegments; i++) {
            setLEDColorSegment(redArray, greenArray, blueArray, i * 12, 12);
        }
        setLEDColorSegment(redArray, greenArray, blueArray, numSegments * 12, numInLastSegment);
    }

    /*
     *  Set the brightness of an individual LED
     *
     *  @param number which LED to change (1-255)
     *  @param brightness brightness level (0-31)
     */
    void setLEDBrightness(int number, int brightness) {
        byte[] data = new byte[2];
        data[0] = (byte) number;
        data[1] = (byte) brightness;
        deviceClient.write(Commands.WRITE_SINGLE_LED_BRIGHTNESS.bVal, data, I2cWaitControl.WRITTEN);
    }

    /*
     *  Set the brightness of all LEDs
     *
     *  @param brightness brightness level (0-31)
     */
    void setLEDBrightness(int brightness) {
        byte[] data = new byte[1];
        data[0] = (byte) brightness;
        deviceClient.write(Commands.WRITE_ALL_LED_BRIGHTNESS.bVal, data, I2cWaitControl.WRITTEN);
    }

    //Turn all LEDS off by setting color to 0
    void LEDOff() {
        this.setAllLEDColor(0x0, 0x0, 0x0);
    }

    /*
     * Change the length of the LED strip
     *
     * @param newLength - 1 to 100 (longer than 100 not supported)
     */
    void changeLength(int newLength) {
        byte[] data = new byte[1];
        data[0] = (byte) newLength;
        deviceClient.write(Commands.CHANGE_LED_LENGTH.bVal, data, I2cWaitControl.WRITTEN);
    }


    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Other;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "Qwiic LED Strip";
    }


    private final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x23);

    public QwiicLEDStrip(I2cDeviceSynchSimple deviceClient) {
        super(deviceClient, true);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);
        super.registerArmingStateCallback(false);
    }

}
