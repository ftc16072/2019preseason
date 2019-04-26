package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Tank Drive")
//@Disabled
public class TankDriveOpMode extends OpMode {
    private Robot robot;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot = new Robot();
        robot.init(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        double leftSpeed = -gamepad1.left_stick_y;
        double rightSpeed = -gamepad1.right_stick_y;
        telemetry.addData("Speeds", "%.2f %.2f", leftSpeed, rightSpeed);
        robot.drive(leftSpeed, rightSpeed);
    }
}
