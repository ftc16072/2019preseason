package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test Wiring")
//@Disabled
public class TestWiringOpMode extends OpMode {
    private Robot robot;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot = new Robot();
        robot.init(hardwareMap);
        telemetry.setMsTransmissionInterval(100);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        robot.test(gamepad1, telemetry);
    }
}
