package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private Servo testServo1 = null;
    private Servo testServo2 = null;

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        leftMotor = map.get(DcMotor.class, "left-motor");
        rightMotor = map.get(DcMotor.class, "right-motor");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        testServo1 = map.get(Servo.class, "testServo1");
        testServo2 = map.get(Servo.class,"testServo2");
   }

    public void setDriveTrain(double left, double right) {
        leftMotor.setPower(left);
        rightMotor.setPower(right);
    }
    public void setTestServo1(double position) {
        testServo1.setPosition(position);
    }
    public void setTestServo2(double position) {
        testServo2.setPosition(position);
    }

}