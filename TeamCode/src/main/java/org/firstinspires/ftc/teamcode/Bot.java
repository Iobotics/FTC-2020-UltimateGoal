package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    private LinearOpMode opMode;

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI*4);
    private final double RIGHT_SIDE_ADJUST = 1/1.25;
    private ElapsedTime runtime = new ElapsedTime();

    //All of the motors

    public Bot(LinearOpMode opMode) {

        this.opMode = opMode;

        frontLeft = opMode.hardwareMap.get(DcMotor.class,"front-left");
        frontRight = opMode.hardwareMap.get(DcMotor.class,"front-right");
        backLeft = opMode.hardwareMap.get(DcMotor.class,"back-left");
        backRight = opMode.hardwareMap.get(DcMotor.class,"back-right");
        //defines the wheels
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //Reverses the wheels

        intake = opMode.hardwareMap.get(DcMotor.class,"intake-left");
        //defines the intakes
        
        shooterLeft = opMode.hardwareMap.get(DcMotor.class,"shooter-left");
        shooterRight = opMode.hardwareMap.get(DcMotor.class,"shooter-right");
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


    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = frontLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = frontRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH*RIGHT_SIDE_ADJUST);
            newBackLeftTarget = backLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newBackRightTarget = backRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH*RIGHT_SIDE_ADJUST);
            frontLeft.setTargetPosition(newFrontLeftTarget);
            frontRight.setTargetPosition(newFrontRightTarget);
            backLeft.setTargetPosition(newBackLeftTarget);
            backRight.setTargetPosition(newBackRightTarget);


            // Turn On RUN_TO_POSITION
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontLeft.setPower(Math.abs(speed));
            frontRight.setPower(Math.abs(speed));
            backLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy())) {

                // Display it for the driver.
                //opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newFrontLeftTarget,  newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                opMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(),
                        backLeft.getCurrentPosition(),
                        backRight.getCurrentPosition());
                opMode.telemetry.update();
            }



            // Stop all motion;
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //sleep(250);   // optional pause after each move
        }
    }

}

//Get Prank'd
//With Love: Wednesday