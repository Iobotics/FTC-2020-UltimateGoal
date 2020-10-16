package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor leftMotorFront;
    private DcMotor rightMotorFront;
    private DcMotor leftMotorBack;
    private DcMotor rightMotorBack;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;
    private DcMotor lifterLeft;
    private DcMotor lifterRight;

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        leftMotorFront = map.get(DcMotor.class, "left-motor-front");
        rightMotorFront = map.get(DcMotor.class, "right-motor-front");
        leftMotorBack = map.get(DcMotor.class, "left-motor-back");
        rightMotorBack = map.get(DcMotor.class, "right-motor-back");
        intakeLeft = map.get(DcMotor.class, "intake-left");
        intakeRight = map.get(DcMotor.class, "intake-right");
        lifterLeft = map.get(DcMotor.class, "lifter-left");
        lifterRight = map.get(DcMotor.class, "lifterRight");


        rightMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        lifterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lifterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setDriveTrain(double left, double right) {
        leftMotorFront.setPower(left);
        rightMotorFront.setPower(right);
        leftMotorBack.setPower(left);
        rightMotorBack.setPower(right);
    }
    public void setIntake(double power) {
        intakeRight.setPower(power);
        intakeLeft.setPower(power);
    }
    public void setLifter(double power) {
        lifterLeft.setPower(power);
        lifterRight.setPower(power);
    }

}