package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;


@I2cDeviceType()
@DeviceProperties(name = "QWIIC LED Strip", description = "Sparkfun QWIIC LED Strip", xmlTag = "QWIIC_LED_STRIP")
public class QwiicLEDStrip extends I2cDeviceSynchDevice<I2cDeviceSynch> {

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
    void setLEDColor(byte number, byte red, byte green, byte blue) {
        byte[] data = new byte[4];
        data[0] = number;
        data[1] = red;
        data[2] = green;
        data[3] = blue;
        deviceClient.write(Commands.WRITE_SINGLE_LED_COLOR.bVal, data);
    }

    /*
     * Change the color of all LEDs to a single color
     * @param red    what the red value should be (0-255)
     * @param green  what the green value should be (0-255)
     * @param blue   what the blue value should be (0-255)
     */
    void setAllLEDColor(byte red, byte green, byte blue) {
        byte[] data = new byte[3];
        data[0] = red;
        data[1] = green;
        data[2] = blue;
        deviceClient.write(Commands.WRITE_ALL_LED_COLOR.bVal, data);
    }

    /*
     * Change the color of all LEDs using arrays
     * @param redArray    what the red value should be (0-255)
     * @param greenArray  what the green value should be (0-255)
     * @param blueArray   what the blue value should be (0-255)
     * @param length      the size of the array
     */
    void setLEDColor(byte[] redArray, byte[] greenArray, byte[] blueArray, byte length) {
        //TODO: implement
    }

    /*
     *  Set the brightness of an individual LED
     *
     *  @param number which LED to change (1-255)
     *  @param brightness brightness level (0-31)
     */
    void setLEDBrightness(byte number, byte brightness) {
        byte[] data = new byte[2];
        data[0] = number;
        data[1] = brightness;
        deviceClient.write(Commands.WRITE_SINGLE_LED_BRIGHTNESS.bVal, data);
    }

    /*
     *  Set the brightness of all LEDs
     *
     *  @param brightness brightness level (0-31)
     */
    void setLEDBrightness(byte brightness) {
        byte[] data = new byte[1];
        data[0] = brightness;
        deviceClient.write(Commands.WRITE_ALL_LED_BRIGHTNESS.bVal, data);
    }

    //Turn all LEDS off by setting color to 0
    void LEDOff() {
        byte value = 0;
        setLEDBrightness(value);
    }

    /*
     * Change the length of the LED strip
     *
     * @param newLength - 1 to 100 (longer than 100 not supported)
     */
    void changeLength(byte newLength) {
        byte[] data = new byte[1];
        data[0] = newLength;
        deviceClient.write(Commands.CHANGE_LED_LENGTH.bVal, data);
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


    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x23);

    public QwiicLEDStrip(I2cDeviceSynch deviceClient) {
        super(deviceClient, true);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

}
