package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Bot {

    public static String VUFORIA_KEY = "AV3lYM//////AAABmfq5toISukEQl7LvHDdcDIIdzi/lqSOdWCwANsoz1Tynuz1awZxXJLeIkKP5OQJjS+9JhucNr+3eDAGA3/+2qEGKB1ErWZ1ed2Stdm/RXAcr77ke/AwU74amlh8/7cct14n3UPxXjSZM/mOOs3ot5ZfxRu042CTRF3kWHXSmzaWeeBLEFzsmTBYf+bMeRq+Sify7f7evkuxe/6VGl62tg13wE6lstmPQjw4M7HjrZcBrJs4q9hf/QLbLgx0YNEIy2pr51Sq2MUXpZDdFYSuXdXLcFTlYhuFjTRBnKu237NnSryNVG6yP776HHtxL4NKGVLRw1IBLJCkX+xm7Di5NoVa11Rl5JjhfLRR6Ukdbsa4M";

    private LinearOpMode opMode;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private DcMotor intake;

    private DcMotor liftMotor;

    private DcMotor leftShooter;
    private DcMotor rightShooter;

    private Servo movingServo;
    private Servo grabbingServo;

    private BNO055IMU imu = null;
    private Orientation angles = null;
    private Acceleration gravity = null;


    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 2.95;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    private ElapsedTime runtime = new ElapsedTime();

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }

    public void init(HardwareMap map) {
        frontLeftMotor = map.get(DcMotor.class, "front-left");
        frontRightMotor = map.get(DcMotor.class, "front-right");
        backLeftMotor = map.get(DcMotor.class, "back-left");
        backRightMotor = map.get(DcMotor.class, "back-right");

        intake = map.get(DcMotor.class, "intake");
        // rightIntake = map.get(DcMotor.class, "right-intake");

        leftShooter = map.get(DcMotor.class, "left-shooter");
        rightShooter = map.get(DcMotor.class, "right-shooter");

        // movingServo = map.get(Servo.class, "moving-servo");
        // grabbingServo = map.get(Servo.class, "grabbing-servo");

        imu = map.get(BNO055IMU.class, "imu");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDrive(double left, double right) {
        frontLeftMotor.setPower(left);
        frontRightMotor.setPower(right);
        backLeftMotor.setPower(left);
        backRightMotor.setPower(right);
    }

    public void setIntake(double power) {
        intake.setPower(power);
    }



    public void setShooter(double power) {
        leftShooter.setPower(power);
        rightShooter.setPower(power);
    }
    /*

    public void setMovingServo(double position) {
        movingServo.setPosition(position);
    }

    public void setGrabbingServo(double position) {
        grabbingServo.setPosition(position);
    }

    public double getMovingServoPos() {
        return movingServo.getPosition();
    }

    public double getGrabbingServoPos() {
        return grabbingServo.getPosition();
    }

*/
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
            newFrontLeftTarget = frontLeftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = frontRightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newBackLeftTarget = backLeftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newBackRightTarget = backRightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            frontLeftMotor.setTargetPosition(newFrontLeftTarget);
            frontRightMotor.setTargetPosition(newFrontRightTarget);
            backLeftMotor.setTargetPosition(newBackLeftTarget);
            backRightMotor.setTargetPosition(newBackRightTarget);


            // Turn On RUN_TO_POSITION
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontLeftMotor.setPower(Math.abs(speed));
            frontRightMotor.setPower(Math.abs(speed));
            backLeftMotor.setPower(Math.abs(speed));
            backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy())) {

                // Display it for the driver.
                //opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newFrontLeftTarget,  newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                opMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        frontLeftMotor.getCurrentPosition(),
                        frontRightMotor.getCurrentPosition(),
                        backLeftMotor.getCurrentPosition(),
                        backRightMotor.getCurrentPosition());
                opMode.telemetry.update();
            }



            // Stop all motion;
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //sleep(250);   // optional pause after each move
        }
    }
    public double getGyroHeading() {
        // Update gyro
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity = imu.getGravity();

        double heading = AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
        return heading;
    }
    public void gyroTurn(double target, double speed){
        while((!(getGyroHeading() < target + 1 && getGyroHeading() > target -1))&& opMode.opModeIsActive()) {
            setDrive(absRange(0.01 *(target - getGyroHeading()), speed), -absRange(0.01 *(target - getGyroHeading()), speed));
            opMode.telemetry.addData("Gyro", getGyroHeading());
            opMode.telemetry.update();
        }
    }

    public double absRange(double input, double range){
        if (input <= range || input >= -range){
            return input;
        }
        else return 1 * ((Math.abs(input))/ input);
    }
}
