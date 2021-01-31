package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Test Auto", group = "auto")
public class Auto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);

        waitForStart();


        bot.encoderDrive(0.5, 25, 25, 5);
        //bot.gyroTurn(90, 0.25);
        //bot.setDriveTrain(1, -1);
        //sleep(500);
        //Test
        bot.setDriveTrain(0, 0);
    }
}
