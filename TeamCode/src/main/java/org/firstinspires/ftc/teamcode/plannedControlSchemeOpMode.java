package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class plannedControlSchemeOpMode extends OpMode {
    private Robot robot = new Robot();
    private JewelDropper jewelDropper = new JewelDropper();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
        jewelDropper.init(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        double forward = gamepad1.left_stick_y * -1; //The y direction on the gamepad is reversed idk why
        double strafe = gamepad1.left_stick_x;
        Polar rightJoystick = Polar.fromCartesian(gamepad1.right_stick_x, -gamepad1.right_stick_y);
        telemetry.addData("Gyro Heeading", robot.getHeadingRadians());
        // mecanumDrive.driveMecanum(forward, strafe, rotate);

        double r = rightJoystick.getR();
        telemetry.addData("r", r);
        if (gamepad1.right_trigger >= 0.05) {
            robot.strafe(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger >= 0.05) {
            robot.strafe(-gamepad1.left_trigger);
        } else if (gamepad1.right_bumper) {
            robot.driveFieldRelative(0, 0, 0.2);
        } else if (gamepad1.left_bumper) {
            robot.driveFieldRelative(0, 0, -0.2);
        } else {

            if (r >= 0.8) {
                telemetry.addData("joystick angle", rightJoystick.getDegrees());
                robot.driveFieldRelativeAngle(strafe, forward, rightJoystick.getTheta());
            } else {
                robot.driveFieldRelative(strafe, forward, 0.0);
            }

            if (gamepad1.a) {
                robot.quack();
            }
        }
        if (gamepad1.dpad_up) {
            jewelDropper.up();
            telemetry.addData("jewel", "up");
        }
        if (gamepad1.dpad_down) {
            jewelDropper.down();
            telemetry.addData("jewel", "down");
        }
    }
}
