package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class FieldRelativeTurnMecanumDriveOpMode extends OpMode {
    private Robot robot = new Robot();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        double forward = gamepad1.left_stick_y * -1; //The y direction on the gamepad is reversed idk why
        double strafe = gamepad1.left_stick_x;

        telemetry.addData("Gyro Heeading", robot.getHeadingRadians());
        // mecanumDrive.driveMecanum(forward, strafe, rotate);

        double r = Math.sqrt(gamepad1.right_stick_x * gamepad1.right_stick_x + gamepad1.right_stick_y * gamepad1.right_stick_y);
        telemetry.addData("r", r);

        if (r >= 0.8) {
            double theta = Math.atan2(gamepad1.right_stick_x, -gamepad1.right_stick_y);
            telemetry.addData("joystick angle", robot.degreeFromRadians(theta));
            robot.driveFieldRelativeAngle(forward, strafe, theta);
        } else {
            robot.driveFieldRelative(forward, strafe, 0.0);
        }

    }
}
