package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp()
@Disabled
public class TestWiringOpMode extends OpMode {
    private MecanumDrive mecanumDrive = new MecanumDrive();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        mecanumDrive.init(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

        if (gamepad1.left_bumper) {
            mecanumDrive.setSpeeds(1, 0, 0, 0);

        }
        if (gamepad1.right_bumper) {
            mecanumDrive.setSpeeds(0, 1, 0, 0);

        }
        if (gamepad1.left_trigger > 0.0) {
            mecanumDrive.setSpeeds(0, 0, 1, 0);

        }
        if (gamepad1.right_trigger > 0.0) {
            mecanumDrive.setSpeeds(0, 0, 0, 1);

        }
    }
}
