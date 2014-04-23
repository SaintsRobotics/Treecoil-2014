package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive system for the robot.
 *
 * @author Saints Robotics
 */
public class Drive implements RobotComponent {

    // Constants
    private static final int TALON_LEFT_ID = 1;
    private static final int TALON_LEFT_2_ID = 2;
    private static final int TALON_RIGHT_ID = 3;
    private static final int TALON_RIGHT_2_ID = 4;

    private static final boolean TALON_LEFT_INVERTED = true;
    private static final boolean TALON_RIGHT_INVERTED = false;

    private static final int SLOW_MODE_FACTOR = 3;

    private final JoystickControl controller;

    // Instance variables
    private final Motor leftMotor;
    private final Motor leftMotor2;
    private final Motor rightMotor;
    private final Motor rightMotor2;
    private int cycleCounts;
    
    public Drive(JoystickControl controller) {
        leftMotor = new Motor(TALON_LEFT_ID,Motor.TALON,TALON_LEFT_INVERTED);
        leftMotor2 = new Motor(TALON_LEFT_2_ID,Motor.TALON,TALON_LEFT_INVERTED);
        rightMotor = new Motor(TALON_RIGHT_ID,Motor.TALON,TALON_RIGHT_INVERTED);
        rightMotor2 = new Motor(TALON_RIGHT_2_ID,Motor.TALON,TALON_RIGHT_INVERTED);
        this.controller = controller;
    }
    
    public void act() {
        arcadeDrive(controller.getArcadeValues());
        report();
    }
    
    // Index 0: left side motor value
    // Index 1: right side motor value
    public void tankDrive(double[] motorValues) {
        tankDrive(motorValues[0],motorValues[1]);
    }
    
    public void tankDrive(double leftValue,double rightValue) {
        leftValue = limit(leftValue);
        rightValue = limit(rightValue);
        
        leftMotor.motor.set(leftMotor.invert()*leftValue);
        leftMotor2.motor.set(leftMotor2.invert()*leftValue);
        rightMotor.motor.set(rightMotor.invert()*rightValue);
        rightMotor2.motor.set(rightMotor2.invert()*rightValue);
    }
    
    // Index 0: throttle value
    // Index 1: turning value
    public void arcadeDrive(double[] motorValues) {
        arcadeDrive(motorValues[0],motorValues[1]);
    }
    
    public void arcadeDrive(double moveValue,double rotateValue) {
        double leftValue = moveValue-rotateValue;
        double rightValue = moveValue+rotateValue;
        double[] motorValues = scale(new double[]{leftValue,rightValue});
        
        leftMotor.motor.set(leftMotor.invert()*motorValues[0]);
        leftMotor2.motor.set(leftMotor2.invert()*motorValues[0]);
        rightMotor.motor.set(rightMotor.invert()*motorValues[1]);
        rightMotor2.motor.set(rightMotor2.invert()*motorValues[1]);
    }
    
    public double limit(double value) {
        if (value>1)
            return 1;
        if (value<-1)
            return -1;
        return value;
    }
    
    public double[] scale(double[] values) {
        if (controller.getSlowButton()) {
            slowMode(values);
        }
        
        double scale = 1.0;
        double newValues[] = new double[values.length];
        
        for (int i=0;i<values.length;i++) {
            double currentScale = 1/Math.abs(values[i]);
            scale = scale>currentScale ? currentScale : scale;
        }
        
        if (scale<1.0) {
            for (int i=0;i<values.length;i++)
                newValues[i] = scale*values[i];
            return newValues;
        } else {
            return values;
        }
    }
    
    public void robotDisable() {
        leftMotor.motor.disable();
        leftMotor2.motor.disable();
        rightMotor.motor.disable();
        rightMotor2.motor.disable();
        cycleCounts = 0;
    }

    public void robotEnable() {
        cycleCounts = 0;
    }

    public void robotAuton() {
        double[] autonArcadeVals = new double[2];
        if (cycleCounts>=0&&cycleCounts<=130) {
            autonArcadeVals[0] = -0.30;
            autonArcadeVals[1] = 0.0;
        } else if (cycleCounts>130) {
            autonArcadeVals[0] = 0.0;
            autonArcadeVals[1] = 0.0;
        }
        cycleCounts++;
        arcadeDrive(autonArcadeVals);
        report();
    }

    private void report() {
        SmartDashboard.putNumber("Arcade Throttle",controller.getArcadeValues()[0]);
        SmartDashboard.putNumber("Arcade Turn",controller.getArcadeValues()[1]);
    }

    private double[] slowMode(double[] values) {
        for (int i=0;i<values.length;i++)
            values[i] /= SLOW_MODE_FACTOR;
        return values;
    }
}
