package org.firstinspires.ftc.teamcode;

import android.view.Display;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Bot {

    final static String VUFIORA_KEY = "AQwllDj/////AAABmc0Q/e36d0XUjP85h//kkdwaGRB3ld7y4r8Cb/kughMy9TMAxuVabjFMTTNs+BFt+JBZkrkES77uXzOxw2CZ8iPLR9Kjme2QfKKTBsgsxi852PAaujjCTBAIwXYm4ityat/3eRGNd13dHGQljgL3vpRVj5ItpXiyPOl6Vr/Skpt/UUoG57PKrbuKPMvY1sFb8pDXjqYdtb5b2bWQ+Bh9uHhQbWGtouGwZC3i7cFdteMKaqlVIjR9DhDIMicNCAbGYmhL9oELfFTtvoLwW2mzR9fe/tda/AaZenFqauu1evtHRjeEwyLzi6maxF0fJMuwo1pPQznPY1mWt+QXmdmvCvTZuTZ48eJu93pqmY55Wi8c";
// adds the vufiora key

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotor intake;

    private DcMotor shooterLeft;
    private DcMotor shooterRight;

    private DcMotor ramp;

    //All of the motors

    public Bot(HardwareMap hwMap) {

        frontLeft = hwMap.get(DcMotor.class,"front-left");
        frontRight = hwMap.get(DcMotor.class,"front-right");
        backLeft = hwMap.get(DcMotor.class,"back-left");
        backRight = hwMap.get(DcMotor.class,"back-right");
        //defines the wheels
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //Reverses the wheels

        intake = hwMap.get(DcMotor.class,"intake-left");
        //defines the intakes
        
        shooterLeft = hwMap.get(DcMotor.class,"shooter-left");
        shooterRight = hwMap.get(DcMotor.class,"shooter-right");
        //defines the shooters
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //reverses the left shooter

    }

    public void setDrive(double left,double right){

        frontLeft.setPower(left);
        frontRight.setPower(right);
        backLeft.setPower(left);
        backRight.setPower(right);
        //sets the power of the motors

    }

    public void setIntake(double power){

        intake.setPower(power);
        //sets power to intake

    }

    public void setShooter(double power){

        shooterLeft.setPower(power);
        shooterRight.setPower(power);
        //sets power to the shooter

    }

    public void setRamp(double power){

        ramp.setPower(power);
        //sets power to ramp

    }

}
