package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private DcMotor leftIntake;
    private DcMotor rightIntake;

    private DcMotor leftLiftMotor;
    private DcMotor rightLiftMotor;

    private DcMotor leftShooter;
    private DcMotor rightShooter;

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        frontLeftMotor = map.get(DcMotor.class, "front-left");
        frontRightMotor = map.get(DcMotor.class, "front-right");
        backLeftMotor = map.get(DcMotor.class, "back-left");
        backRightMotor = map.get(DcMotor.class, "back-right");

        leftIntake = map.get(DcMotor.class, "left-intake");
        rightIntake = map.get(DcMotor.class, "right-intake");

        leftLiftMotor = map.get(DcMotor.class, "left-lift");
        rightLiftMotor = map.get(DcMotor.class, "right-lift");

        leftShooter = map.get(DcMotor.class, "left-shooter");
        rightShooter = map.get(DcMotor.class, "right-shooter");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDrive(double left, double right) {
        frontLeftMotor.setPower(left);
        frontRightMotor.setPower(right);
        backLeftMotor.setPower(left);
        backRightMotor.setPower(right);
    }

    public void setIntake(double power) {
        leftIntake.setPower(power);
        rightIntake.setPower(power);
    }

    public void setLift(double power) {
        leftLiftMotor.setPower(power);
        rightLiftMotor.setPower(power);
    }

    public void setShooter(double power) {
        leftShooter.setPower(power);
        rightShooter.setPower(power);
    }
}
