package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp()
@Disabled
public class JLOpMode extends OpMode {
    private int iterations = 0;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
    }

    // Added some comments about what this does
    @Override
    public void loop() {
        this.telemetry.addData("JL", this.iterations);
        this.iterations = this.iterations + 1;
    }
}
