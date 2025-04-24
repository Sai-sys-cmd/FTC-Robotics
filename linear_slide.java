package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class GobildaLinearSlides {

    public void extendVerticalSlides(int verticalSlideExtendPos) {
    verticalLeftSlide.setTarget(verticalSlideExtendPos); // the position you want the slides to reach
    verticalLeftSlide.retMotorEx().setTargetPositionTolerance(1); // set accuracy to 1 tick
    verticalLeftSlide.toPosition();
    verticalLeftSlide.setPower(1); // raise at some power

    verticalRightSlide.setTarget(verticalSlideExtendPos);
    verticalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalRightSlide.toPosition();
    verticalRightSlide.setPower(1);
 }
}
 
 
