package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {
    private LynxEmbeddedIMU imu;
    private MecanumDrive mecanumDrive = new MecanumDrive();
    private int quackID;
    private Context appContext;
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    void init(HardwareMap hwMap) {
        imu = hwMap.get(LynxEmbeddedIMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
        //    colorSensor = hwMap.get(ColorSensor.class, "sensor_color_distance");
        //    distanceSensor = hwMap.get(DistanceSensor.class, "sensor_distance");
        mecanumDrive.init(hwMap);
        quackID = hwMap.appContext.getResources().getIdentifier("quack", "raw", hwMap.appContext.getPackageName());
        appContext = hwMap.appContext;
    }

    double getDistanceCM() {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

    int getGreen() {
        return colorSensor.green();
    }

    int getRed() {
        return colorSensor.red();
    }

    int getBlue() {
        return colorSensor.blue();
    }

    double getHeadingRadians() {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return -angles.firstAngle;   // Not sure why this is negative, but philip guessed it :)

    }

    double getTiltRadians() {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return -angles.secondAngle;   // Not sure why this is negative, but philip guessed it :)

    }

    double getYawRadians() {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return -angles.thirdAngle;   // Not sure why this is negative, but philip guessed it :)

    }

    double degreeFromRadians(double theta) {
        return theta * 360 / (2 * Math.PI);
    }


    void driveFieldRelative(double x, double y, double rotate) {
        Polar drive = Polar.fromCartesian(x, y);
        double heading = getHeadingRadians();

        drive.subtractAngle(heading);
        mecanumDrive.driveMecanum(drive.getY(), drive.getX(), rotate);
    }

    void quack() {
        SoundPlayer.getInstance().startPlaying(appContext, quackID);

    }

    void strafe(double speed) {
        mecanumDrive.driveMecanum(0, speed, 0);
    }

    void driveFieldRelativeAngle(double x, double y, double angle) {
        double delta = angle - getHeadingRadians();
        if (delta >= Math.PI) {
            delta = delta - (2 * Math.PI);
        } else if (delta <= -Math.PI) {
            delta = delta + (2 * Math.PI);
        }
        double MAX_ROTATE = 0.7; //This is to shrink how fast we can rotate so we don't fly past the angle
        delta = Range.clip(delta, -MAX_ROTATE, MAX_ROTATE);
        driveFieldRelative(x, y, delta);
    }

    Polar[] reportEncoders() {
        return mecanumDrive.returnEncoders();
    }

    void resetEncoders() {
        mecanumDrive.setStartPosition();
    }

    Polar getDistanceTraveled() {
        return mecanumDrive.getDistanceTraveled();
    }
}
