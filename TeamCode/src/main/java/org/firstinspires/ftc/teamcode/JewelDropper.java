package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class JewelDropper {
    private Servo jewelServo;

    void init(HardwareMap hwMap) {
        jewelServo = hwMap.get(Servo.class, "jewel_servo");
        start();

    }

    private void start() {
        jewelServo.setPosition(0.2);
    }

    void up() {
        jewelServo.setPosition(0.0);
    }

    void down() {
        jewelServo.setPosition(1.0);
    }
}
