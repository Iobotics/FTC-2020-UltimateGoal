package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor motor1;
    private DcMotor motor2;

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        motor1 = map.get(DcMotor.class, "motor1");
        motor2 = map.get(DcMotor.class, "motor2");
   }

    public void setDriveTrain(double left, double right) {
        motor1.setPower(left);
        motor2.setPower(right);
    }

}