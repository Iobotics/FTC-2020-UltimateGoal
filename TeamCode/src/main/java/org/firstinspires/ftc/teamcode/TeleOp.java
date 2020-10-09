package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);
        long millisTimer1 = 0;
        boolean timerStart1 = false;
        long millisTimer2 = 0;
        boolean timerStart2 = false;

        waitForStart();

        while(opModeIsActive()) {
            bot.setDriveTrain(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

            if(gamepad1.x) {
                bot.setTestServo1(1);
                millisTimer1 = System.currentTimeMillis() + 1000;
                timerStart1 = true;
            }
            if(millisTimer1 <= System.currentTimeMillis() && timerStart1) {
                bot.setTestServo1(0);
                timerStart1 = false;
            }
            if(gamepad1.y) {
                bot.setTestServo1(1);
                millisTimer1 = System.currentTimeMillis() + 2000;
                timerStart1 = true;
            }
            if(millisTimer2 <= System.currentTimeMillis() && timerStart2) {
                bot.setTestServo2(0);
                timerStart2 = false;
            }
            if(gamepad1.a) {
                bot.setTestServo2(1);
                millisTimer2 = System.currentTimeMillis() + 1000;
                timerStart2 = true;
            }
            if(gamepad1.b) {
                bot.setTestServo2(1);
                bot.setTestServo1(1);
                millisTimer1 = System.currentTimeMillis() + 2000;
                millisTimer2 = System.currentTimeMillis() + 2000;
            }
        }
    }
}