package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);
        waitForStart();

        while(opModeIsActive()) {
                bot.setDriveTrain(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
                if(gamepad1.left_bumper) {
                    bot.setIntake(1);
                }
                else {
                    bot.setIntake(0);
                }
                if(gamepad1.right_bumper) {
                    bot.setFlyWheel(1);
                }
                else {
                    bot.setFlyWheel(0);
                }
        }

    }

}