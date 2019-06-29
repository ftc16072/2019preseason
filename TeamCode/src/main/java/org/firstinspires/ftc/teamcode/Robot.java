package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {
    private LynxEmbeddedIMU imu;
    private int quackID;
    private Context appContext;

    void init(HardwareMap hwMap) {
        imu = hwMap.get(LynxEmbeddedIMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
        quackID = hwMap.appContext.getResources().getIdentifier("quack", "raw", hwMap.appContext.getPackageName());
        appContext = hwMap.appContext;

    }

    double getHeading() {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    void quack() {
        SoundPlayer.getInstance().startPlaying(appContext, quackID);

    }

}
