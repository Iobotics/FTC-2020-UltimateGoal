 package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);

        waitForStart();

        int intakeVal = 0;
        while (opModeIsActive()) {
            ///bot.setLauncher(gamepad1.right_trigger);

            if (gamepad1.right_trigger > 0.2) {
                bot.setLauncher(1);
            } else if (gamepad1.right_trigger < 0.2) {
                bot.setLauncher(0);
            }
            bot.setDriveTrain(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

            if (gamepad1.left_bumper) {
                if (intakeVal != 1) {
                    intakeVal = 1;
                    while (gamepad1.left_bumper) { }
                } else {
                    intakeVal = 0;
                    while (gamepad1.left_bumper) { }
                }
            } else if (gamepad1.right_bumper) {
                if (intakeVal != -1) {
                    intakeVal = -1;
                    while (gamepad1.right_bumper) { }
                } else {
                    intakeVal = 0;
                    while (gamepad1.right_bumper) { }
                }
                if (gamepad1.left_trigger > 0.2){
                    bot.setUpRamp(1);
                }else if (gamepad1.left_trigger < 0.2){
                    bot.setUpRamp(0);
                }
            }

        }
    }
}