package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {
    private LynxEmbeddedIMU imu;
    private MecanumDrive mecanumDrive = new MecanumDrive();
    private int quackID;
    private Context appContext;

    void init(HardwareMap hwMap) {
        imu = hwMap.get(LynxEmbeddedIMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
        mecanumDrive.init(hwMap);
        quackID = hwMap.appContext.getResources().getIdentifier("quack", "raw", hwMap.appContext.getPackageName());
        appContext = hwMap.appContext;
    }

    double getHeadingRadians() {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return -angles.firstAngle;   // Not sure why this is negative, but philip guessed it :)

    }

    double degreeFromRadians(double theta) {
        return theta * 360 / (2 * Math.PI);
    }


    void driveFieldRelative(Telemetry telemetry, double x, double y, double rotate) {
        //Convert x, y to theta, r

        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);

        //Get modified theta, r based off gyro heading
        double heading = getHeadingRadians();

        double modifiedTheta = theta - heading;

        //Convert theta and r back to a forward and strafe
        double forward = r * Math.cos(modifiedTheta);
        double strafe = r * Math.sin(modifiedTheta);

        mecanumDrive.driveMecanum(forward, strafe, rotate);
    }

    void quack() {
        SoundPlayer.getInstance().startPlaying(appContext, quackID);

    }

}
