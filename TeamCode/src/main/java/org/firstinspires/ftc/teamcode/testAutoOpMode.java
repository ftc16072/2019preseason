package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class testAutoOpMode extends OpMode {
    private Robot robot = new Robot();
    private int stage = 0;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.resetEncoders();

    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        switch (stage) {
            case 0:
                if (robot.autoDriving(new Polar(0, 5), 0)) {
                    robot.quack();
                    stage += 1;
                }
            case 1:
                if (robot.autoDriving(new Polar(Math.PI / 2, 5), 0)) {
                    robot.quack();
                    stage += 1;
                }
            case 2:
                if (robot.autoDriving(new Polar(Math.PI, 5), 0)) {
                    robot.quack();
                    stage += 1;
                }
            case 3:
                if (robot.autoDriving(new Polar(-Math.PI / 2, 5), 0)) {
                    robot.quack();
                    stage += 1;
                }
            case 4: {
            }
        }
    }
}
