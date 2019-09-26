package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp()
// @Disabled
public class TestLEDOpMode extends OpMode {
    private QwiicLEDStrip ledStrip;
    private ElapsedTime elapsedTime = new ElapsedTime();
    private int pattern = 0;
    private @ColorInt
    int[] colors = new int[]{
            Color.rgb(148, 0, 211),
            Color.rgb(75, 0, 130),
            Color.rgb(0, 0, 255),
            Color.rgb(0, 255, 0),
            Color.rgb(255, 0, 0),
            Color.rgb(255, 255, 0),
            Color.parseColor("purple"),
            Color.parseColor("teal"),
            Color.parseColor("silver"),
            Color.rgb(0, 0, 0)
    };

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        ledStrip = hardwareMap.get(QwiicLEDStrip.class, "led_strip");
        ledStrip.setLEDBrightness(4);
        ledStrip.setAllLEDColor(Color.parseColor("red"));
        ledStrip.setLEDColor(5, Color.parseColor("blue"));
    }

    @Override
    public void start() {
        elapsedTime.reset();
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        if (elapsedTime.milliseconds() >= 500) {
            if (pattern == colors.length) {
                ledStrip.setLEDColor(colors);
                pattern = 0;
            } else {
                ledStrip.setAllLEDColor(colors[pattern]);
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
