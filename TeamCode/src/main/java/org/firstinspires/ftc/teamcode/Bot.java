package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        leftMotor = map.get(DcMotor.class, "left-motor");
        rightMotor = map.get(DcMotor.class, "right-motor");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDriveTrain(double left, double right) {
        leftMotor.setPower(left);
        rightMotor.setPower(right);
    }
}