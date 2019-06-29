package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
//@Disabled
public class QuackOpMode extends OpMode {
    // Code to run ONCE when the driver hits INIT
    private Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.quack();
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        if (gamepad1.a) {
            robot.quack();
        }
    }
}
