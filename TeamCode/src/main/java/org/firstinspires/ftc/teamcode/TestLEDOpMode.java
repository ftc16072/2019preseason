package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp()
// @Disabled
public class TestLEDOpMode extends OpMode {
    private QwiicLEDStrip ledStrip;
    private ElapsedTime elapsedTime = new ElapsedTime();
    private int pattern = 0;
    private int[] redArray = new int[]{148, 75, 0, 0, 255, 255, 255, 0, 0, 0};
    private int[] greenArray = new int[]{0, 0, 0, 255, 255, 127, 0, 0, 0, 0};
    private int[] blueArray = new int[]{211, 130, 255, 0, 0, 0, 0, 0, 0, 0};

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        ledStrip = hardwareMap.get(QwiicLEDStrip.class, "led_strip");
        ledStrip.setLEDBrightness(4);
        ledStrip.setAllLEDColor(0x255, 0x0, 0x0);
    }

    @Override
    public void start() {
        elapsedTime.reset();
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        if (elapsedTime.milliseconds() >= 500) {
            if (pattern == 8) {
                ledStrip.setLEDColor(redArray, greenArray, blueArray, 10);
                pattern = 0;
            } else {
                ledStrip.setAllLEDColor(redArray[pattern], greenArray[pattern], blueArray[pattern]);
                pattern++;
            }
            elapsedTime.reset();
        }
    }

    @Override
    public void stop() {
        ledStrip.LEDOff();
    }
}
