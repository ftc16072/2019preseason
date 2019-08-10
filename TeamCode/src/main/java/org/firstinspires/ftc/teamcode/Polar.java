package org.firstinspires.ftc.teamcode;

public class Polar {

    private double theta;
    private double r;

    Polar(double theta, double r) {
        this.theta = theta;
        this.r = r;
    }

    double getTheta() {
        return theta;
    }

    double getDegrees() {
        return theta * 360 / (2 * Math.PI);
    }

    double getR() {
        return r;
    }

    static Polar fromCartesian(double x, double y) {
        double r = Math.hypot(x, y);
        double theta = Math.atan2(x, y);
        return new Polar(theta, r);
    }

    void subtractAngle(double heading) {
        theta = theta - heading;
    }

    double getX() {
        return r * Math.sin(theta);
    }

    double getY() {
        return r * Math.cos(theta);
    }


}

