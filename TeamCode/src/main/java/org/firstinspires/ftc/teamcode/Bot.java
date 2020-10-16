package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor leftMotorBack;
    private DcMotor rightMotorBack;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;
    private DcMotor liftRight;
    private DcMotor liftLeft;
    final static int ENCODER_TICKS_PER_REV = 1120;

    //TODO Gear ratio
    //square root of 2/2

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 2.95;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    private ElapsedTime runtime = new ElapsedTime();


    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }


    public void init(HardwareMap map) {
        leftMotor = map.get(DcMotor.class, "left-motor");
        rightMotor = map.get(DcMotor.class, "right-motor");
        leftMotorBack = map.get(DcMotor.class, "left-motor-back");
        rightMotorBack = map.get(DcMotor.class, "right-motor-back");
        intakeLeft = map.get(DcMotor.class, "intake-right");
        intakeRight= map.get(DcMotor.class, "intake-left");
        liftRight = map.get(DcMotor.class, "lift-right");
        liftLeft = map.get(DcMotor.class, "lift-left");
        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void setDriveTrain(double left, double right) {
        leftMotor.setPower(left);
        rightMotor.setPower(right);
        leftMotorBack.setPower(left);
        rightMotorBack.setPower(right);
    }
    public void setIntake(double power) {
        intakeLeft.setPower(power);
        intakeRight.setPower(power);
    }
    public void setLift(double power2) {
        liftLeft.setPower(power2);
        liftRight.setPower(power2);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) throws InterruptedException {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = leftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = rightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newBackLeftTarget = leftMotorBack.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newBackRightTarget = rightMotorBack.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            leftMotor.setTargetPosition(newFrontLeftTarget);
            rightMotor.setTargetPosition(newFrontRightTarget);
            leftMotorBack.setTargetPosition(newBackLeftTarget);
            rightMotorBack.setTargetPosition(newBackRightTarget);


            // Turn On RUN_TO_POSITION
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftMotor.setPower(Math.abs(speed));
            rightMotor.setPower(Math.abs(speed));
            leftMotorBack.setPower(Math.abs(speed));
            rightMotorBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftMotor.isBusy() || rightMotor.isBusy() || leftMotorBack.isBusy() || rightMotorBack.isBusy())) {

                // Display it for the driver.
                //opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newFrontLeftTarget,  newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                opMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotor.getCurrentPosition(),
                        rightMotor.getCurrentPosition(),
                        leftMotorBack.getCurrentPosition(),
                        rightMotorBack.getCurrentPosition());
                //opMode.telemetry.update();
            }



            // Stop all motion;
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //sleep(250);   // optional pause after each move
        }
    }

}
