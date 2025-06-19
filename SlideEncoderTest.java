package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Linear Slide TeleOp using Encoders
 *
 * Controls a dual-motor linear slide using RUN_TO_POSITION mode with encoder feedback.
 * The slide moves smoothly to preset positions controlled by the gamepad D-pad.
 *
 * - D-pad up increases target position (moves slide up)
 * - D-pad down decreases target position (moves slide down)
 * 
 * Target positions are clamped between MIN_POSITION and MAX_POSITION to prevent damage.
 *
 * Make sure the hardware config names for the motors are:
 * - "leftSlideMotor"
 * - "rightSlideMotor"
 *
 * Adjust MAX_POSITION based on your actual slide's max encoder ticks.
 */
@TeleOp(name = "Linear Slide TeleOp Encoder", group = "TeleOp")
public class LinearSlideTeleOp extends LinearOpMode {

    // Declare slide motors
    private DcMotor leftSlideMotor;
    private DcMotor rightSlideMotor;

    // Current target position for the slide (encoder ticks)
    private int currentTarget = 0;

    // Define minimum and maximum slide positions to avoid overextending
    private final int MAX_POSITION = 300;  // Change this based on your slide's max encoder ticks
    private final int MIN_POSITION = 0;    // Typically zero at bottom position

    @Override
    public void runOpMode() {
        // Initialize hardware by mapping motors using configured names
        leftSlideMotor = hardwareMap.get(DcMotor.class, "leftSlideMotor");
        rightSlideMotor = hardwareMap.get(DcMotor.class, "rightSlideMotor");

        // Reset encoders to zero to start fresh
        leftSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor directions so the slide moves up/down correctly
        // Right motor is reversed because it's mounted opposite the left motor
        leftSlideMotor.setDirection(DcMotor.Direction.FORWARD);
        rightSlideMotor.setDirection(DcMotor.Direction.REVERSE);

        // Configure motors to brake when power is zero to hold position
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set motors to RUN_TO_POSITION mode for encoder-based movement
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Telemetry to indicate we are ready to start
        telemetry.addData("Status", "Ready");
        telemetry.update();

        // Wait for the start button to be pressed on the Driver Station
        waitForStart();

        // Main control loop runs while the OpMode is active
        while (opModeIsActive()) {
            // Increase target position when D-pad up is pressed
            if (gamepad1.dpad_up) {
                currentTarget += 10; // Increment target by 10 encoder ticks
            }
            // Decrease target position when D-pad down is pressed
            else if (gamepad1.dpad_down) {
                currentTarget -= 10; // Decrement target by 10 encoder ticks
            }

            // Clamp the target position to stay within min and max limits
            currentTarget = Math.min(MAX_POSITION, Math.max(MIN_POSITION, currentTarget));

            // Set the target position for both motors
            leftSlideMotor.setTargetPosition(currentTarget);
            rightSlideMotor.setTargetPosition(currentTarget);

            // Apply a low power to move the slide smoothly to the target
            leftSlideMotor.setPower(0.3);
            rightSlideMotor.setPower(0.3);

            // Send telemetry data back to Driver Station for debugging and monitoring
            telemetry.addData("Target Position", currentTarget);
            telemetry.addData("Left Motor Position", leftSlideMotor.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightSlideMotor.getCurrentPosition());
            telemetry.update();

            // Small delay to prevent spamming inputs and to debounce the buttons
            sleep(100);
        }

        // Stop motors when the OpMode is no longer active
        leftSlideMotor.setPower(0);
        rightSlideMotor.setPower(0);
    }
}
