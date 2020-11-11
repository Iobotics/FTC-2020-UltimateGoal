
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Test Auto", group = "auto")
public class Auto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);

        waitForStart();

        // encoderDrive ex: bot.encoderDrive(1,10,10,3);
        bot.setIntake(1);
        bot.encoderDrive(0.75, 83, 83, 5);
        bot.setIntake(0);

    }
}
