package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Tele Op")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Bot bot = new Bot(this.hardwareMap);
        waitForStart();
        while(opModeIsActive()){

            bot.setDrive(-gamepad1.left_stick_y,-gamepad1.right_stick_y);

        }
    }
}
