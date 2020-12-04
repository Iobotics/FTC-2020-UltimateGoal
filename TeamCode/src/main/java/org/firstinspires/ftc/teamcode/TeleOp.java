package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="teleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);
        int intakeStatus = 0;
        waitForStart();

        while(opModeIsActive()) {
            bot.setDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

            if (gamepad1.left_bumper && intakeStatus != 1) {
                intakeStatus = 1;
            }
            else if ((gamepad1.left_bumper && intakeStatus == 1) || (gamepad1.right_bumper && intakeStatus == -1)) {
                intakeStatus = 0;
            }
            else if(gamepad1.right_bumper && intakeStatus != -1) {
                intakeStatus = -1;
            }

            bot.setIntake(intakeStatus);
            /*
            if (gamepad1.left_bumper) {
                bot.setIntake(1);
            }
            else if (gamepad1.right_bumper) {
                bot.setIntake(-1);
            }
            else bot.setIntake(0);

             */

            if (gamepad1.a) {
                bot.setLift(0.75);
            }
            else if (gamepad1.b) {
                bot.setLift(-0.75);
            }
            else bot.setLift(0);

            bot.setShooter(gamepad1.right_trigger > 0.2 ? 1 : 0);
        }
    }
}
