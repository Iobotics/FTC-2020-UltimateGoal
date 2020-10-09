package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private Servo testServo = null;
    private Servo testTestServo = null;

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        leftMotor = map.get(DcMotor.class, "left-motor");
        rightMotor = map.get(DcMotor.class, "right-motor");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        testServo = map.get(Servo.class,"testServo");
        testTestServo = map.get(Servo.class,"testTestServo");
    }

    public void setDriveTrain(double left, double right) {
        leftMotor.setPower(left);
        rightMotor.setPower(right);
    }
    public void setTestServo(double position) {
        testServo.setPosition(position);
    }
    public void setTestTestServo(double position) {
        testTestServo.setPosition(position);
    }
}
