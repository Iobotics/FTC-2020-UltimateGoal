package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Tele Op", group = "Test")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Bot bot = new Bot(this.hardwareMap);
        waitForStart();
        while(opModeIsActive()){

            bot.setDrive(-gamepad1.left_stick_y,-gamepad1.right_stick_y);

            if (gamepad1.left_bumper) {

                bot.setIntake(1);

            }else if(gamepad1.right_bumper){

                bot.setIntake(-1);

            }else{
                bot.setIntake(0);
            }
            if (gamepad1.a) {

                bot.setShooter(1);

            }else if(gamepad1.b){

                bot.setShooter(-1);

            }else{
                bot.setShooter(0);
            }
            if (gamepad1.x) {

                bot.setRamp(1);

            }else if(gamepad1.y){

                bot.setShooter(-1);

            }else{
                bot.setShooter(0);
            }


        }
    }
}
