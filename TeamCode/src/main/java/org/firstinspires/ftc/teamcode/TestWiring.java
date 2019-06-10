package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;


@TeleOp()
@Disabled
public class TestWiring extends OpMode {
    private MecanumDrive MecanumDrive = new MecanumDrive();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {

    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

        if (gamepad1.left_bumper) {
            MecanumDrive.setSpeeds(1, 0, 0, 0);

        }
        if (gamepad1.right_bumper) {
            MecanumDrive.setSpeeds(0, 1, 0, 0);

        }
        if (gamepad1.left_trigger > 0.0) {
            MecanumDrive.setSpeeds(0, 0, 1, 0);

        }
        if (gamepad1.right_trigger > 0.0) {
            MecanumDrive.setSpeeds(0, 0, 0, 1);

        }
    }
}
