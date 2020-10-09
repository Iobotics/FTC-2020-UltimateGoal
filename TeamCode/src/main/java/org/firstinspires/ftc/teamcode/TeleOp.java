package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);
        long millisTimer = 0;
        boolean timerStart = false;
        long millisTimerTimer = 0;
        boolean timerTimerStart = false;
        waitForStart();

        while(opModeIsActive()) {
                bot.setDriveTrain(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
                if(gamepad1.x) {
                    bot.setTestServo(1);
                    millisTimer = System.currentTimeMillis() +1000; //wait for 1 second
                    timerStart = true;
                }
                if(millisTimer <= System.currentTimeMillis()) {

                    if (timerStart != false) {
                        bot.setTestServo(0);
                        timerStart = false;
                    }

                } //Timer
                if (gamepad1.y) {
                    bot.setTestServo(1);
                    millisTimer = System.currentTimeMillis() +2000; //wait for 2 seconds
                    timerStart = true;
                }
                if (gamepad1.a) {
                    bot.setTestTestServo(1);
                    millisTimerTimer = System.currentTimeMillis() +1000;
                    timerTimerStart = true;
                }
                if (millisTimerTimer <= System.currentTimeMillis()) {
                    if (timerTimerStart != false) {
                        bot.setTestTestServo(0);
                        timerTimerStart = false;
                    }
                }
                if (gamepad1.b) {
                    bot.setTestTestServo(1);
                    bot.setTestServo(1);
                    millisTimer = System.currentTimeMillis() +1000;
                    millisTimerTimer =System.currentTimeMillis() +1000;
                    timerStart = true;
                    timerTimerStart = true;
                }
        }

    }

}