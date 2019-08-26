package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class MecanumDrive {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    private int frontLeftOffset;
    private int frontRightOffset;
    private int backLeftOffset;
    private int backRightOffset;

    void init(HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotor.class, "front_left");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight = hwMap.get(DcMotor.class, "front_right");
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft = hwMap.get(DcMotor.class, "back_left");
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight = hwMap.get(DcMotor.class, "back_right");
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setSpeeds(double flSpeed, double frSpeed, double blSpeed, double brSpeed) {
        double largest = 1.0;
        largest = Math.max(largest, Math.abs(flSpeed));
        largest = Math.max(largest, Math.abs(frSpeed));
        largest = Math.max(largest, Math.abs(blSpeed));
        largest = Math.max(largest, Math.abs(brSpeed));

        frontLeft.setPower(flSpeed / largest);
        frontRight.setPower(frSpeed / largest);
        backLeft.setPower(blSpeed / largest);
        backRight.setPower(brSpeed / largest);
    }

    void driveMecanum(double forward, double strafe, double rotate) {
        double frontLeftSpeed = forward + strafe + rotate;
        double frontRightSpeed = forward - strafe - rotate;
        double backLeftSpeed = forward - strafe + rotate;
        double backRightSpeed = forward + strafe - rotate;

        setSpeeds(frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed);


    }

    void reportEncoders(Telemetry telemetry) {
        telemetry.addData("Encoders", "%d %d %d %d",
                frontLeft.getCurrentPosition(),
                frontRight.getCurrentPosition(),
                backLeft.getCurrentPosition(),
                backRight.getCurrentPosition());


    }

    Polar[] returnEncoders() {
        Polar frontLeftPolar = new Polar(-Math.PI / 4, frontLeft.getCurrentPosition() - frontLeftOffset);
        Polar frontRightPolar = new Polar(Math.PI / 4, frontRight.getCurrentPosition() - frontRightOffset);
        Polar backLeftPolar = new Polar(Math.PI / 4, backLeft.getCurrentPosition() - backLeftOffset);
        Polar backRightPolar = new Polar(-Math.PI / 4, backRight.getCurrentPosition() - backRightOffset);
        Polar[] motorPolars = new Polar[4];
        motorPolars[0] = frontLeftPolar;
        motorPolars[1] = frontRightPolar;
        motorPolars[2] = backLeftPolar;
        motorPolars[3] = backRightPolar;
        return motorPolars;
    }

    Polar getDistanceTraveled() {
        Polar[] motors = returnEncoders();
        double newX = motors[0].getX() + motors[1].getX() + motors[2].getX() + motors[3].getX();
        double newY = motors[0].getY() + motors[1].getY() + motors[2].getY() + motors[3].getY();
        Polar ticks = Polar.fromCartesian(newX, newY);
        Polar rotations = new Polar(ticks.getTheta(), ticks.getR() / 383.6);
        return rotations;
    }

    void setStartPosition() {
        frontLeftOffset = frontLeft.getCurrentPosition();
        frontRightOffset = frontRight.getCurrentPosition();
        backLeftOffset = backLeft.getCurrentPosition();
        backRightOffset = backRight.getCurrentPosition();
    }
}
