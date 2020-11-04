package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Bot {
    private LinearOpMode opMode;
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor leftMotorBack;
    private DcMotor rightMotorBack;
    private DcMotor intake;
    private DcMotor shooterRight;
    private DcMotor shooterLeft;
    final static int ENCODER_TICKS_PER_REV = 1120;

    //TODO Gear ratio
    //square root of 2/2

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI*4);
    private final double RIGHT_SIDE_ADJUST = 1/1.25;
    private ElapsedTime runtime = new ElapsedTime();

    private BNO055IMU imu = null;
    private Orientation angles = null;
    private Acceleration gravity = null;


    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode.hardwareMap);
    }


    public void init(HardwareMap map) {
        leftMotor = map.get(DcMotor.class, "left-motor");
        rightMotor = map.get(DcMotor.class, "right-motor");
        leftMotorBack = map.get(DcMotor.class, "left-motor-back");
        rightMotorBack = map.get(DcMotor.class, "right-motor-back");
        intake = map.get(DcMotor.class, "intake");
        shooterRight = map.get(DcMotor.class, "shooter-right");
        shooterLeft = map.get(DcMotor.class, "shooter-left");

        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = false;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = map.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }


    public void setDriveTrain(double left, double right) {
        leftMotor.setPower(left);
        rightMotor.setPower(right);
        leftMotorBack.setPower(left);
        rightMotorBack.setPower(right);
    }
    public void setIntake(double power) {
        intake.setPower(power);
    }
    public void setFlyWheel(double power) {
        shooterRight.setPower(power);
        shooterLeft.setPower(power);
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
            newFrontLeftTarget = leftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = rightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH*RIGHT_SIDE_ADJUST);
            newBackLeftTarget = leftMotorBack.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newBackRightTarget = rightMotorBack.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH*RIGHT_SIDE_ADJUST);
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
                        opMode.telemetry.update();
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

    public double getGyroHeading() {
        // Update gyro
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity = imu.getGravity();

        double heading = AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
        return heading;
    }
    public void gyroTurn(double target, double speed){
        while((!(getGyroHeading() < target + 1 && getGyroHeading() > target -1))&& opMode.opModeIsActive()) {
            setDriveTrain(absRange(0.01 *(target - getGyroHeading()), speed), -absRange(0.01 *(target - getGyroHeading()), speed));
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
