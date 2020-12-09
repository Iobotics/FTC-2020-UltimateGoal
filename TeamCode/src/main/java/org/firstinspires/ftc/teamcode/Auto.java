package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    @Override
    public void runOpMode() throws InterruptedException {

        initVuforia();
        initTfod();

        waitForStart();

        Bot bot = new Bot(this);

        bot.setMovingServo(0.3);

        bot.encoderDrive(0.5, 20, 20, 10);


        long millis = System.currentTimeMillis();

        List<Recognition> recognitionList = new ArrayList();
        while (System.currentTimeMillis() - millis < 3000) {
            recognitionList = tfod.getRecognitions();
        }

        Recognition recognition = recognitionList.size() > 0 ? recognitionList.get(0) : null;
        if (recognition == null) {
            telemetry.addData("Found", "nothing");
        }
        else if (recognition.getLabel().equalsIgnoreCase(LABEL_SECOND_ELEMENT)) {
            telemetry.addData("Found", "single");
        }
        else {
            telemetry.addData("Found", "quad");
        }

        telemetry.update();

        while (opModeIsActive()) {
            Thread.yield();
        }

    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = Bot.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
        tfod.activate();
    }
}
