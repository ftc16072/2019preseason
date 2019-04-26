package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class Robot {
    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;

    void init(HardwareMap hwMap) {
        leftDriveMotor = hwMap.get(DcMotor.class, "left_drive");
        rightDriveMotor = hwMap.get(DcMotor.class, "right_drive");
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void drive(double leftSpeed, double rightSpeed) {
        leftDriveMotor.setPower(leftSpeed);
        rightDriveMotor.setPower(rightSpeed);
    }

    private void testMotor(DcMotor motor, String name, double speed, Telemetry telemetry) {
        motor.setPower(speed);
        telemetry.addData(name, "Speed: %.02f Count: %d", motor.getPower(), motor.getCurrentPosition());
    }

    void test(Gamepad gamepad, Telemetry telemetry) {
        double left_speed = 0.0;
        double right_speed = 0.0;

        if (gamepad.left_bumper) {
            left_speed = 1.0;
        }
        if (gamepad.right_bumper) {
            right_speed = 1.0;
        }
        testMotor(leftDriveMotor, "left_motor", left_speed, telemetry);
        testMotor(rightDriveMotor, "right_motor", right_speed, telemetry);
    }

    void stop() {
        drive(0.0, 0.0);
    }
}
