package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive system for the robot.
 * @author Saints Robotics
 */
public class Drive implements IRobotComponent
{
    // Constants
    private static final int TALON_LEFT_ID = 1;
    private static final int TALON_LEFT_2_ID = 2;
    private static final int TALON_RIGHT_ID = 3;
    private static final int TALON_RIGHT_2_ID = 4;
    
    private static final boolean TALON_LEFT_INVERTED = true;
    private static final boolean TALON_RIGHT_INVERTED = false;
    
    private static final int SLOW_MODE_FACTOR = 3;
    
//    private static final int ENCODER_DIGITAL_SIDECAR_CHANNEL = 1;
//    private static final int ENCODER_LEFT_CHANNEL = 3;
//    private static final int ENCODER_RIGHT_CHANNEL = 4;
//    
//    private static final double ENCODER_CODES_PER_REV = 10;
//    private static final double ENCODER_GEARING_RATIO = 10;
    
    private final JoystickControl controller;
    
    // Instance variables
    private final Motor leftMotor;
    private final Motor leftMotor2;
    private final Motor rightMotor;
    private final Motor rightMotor2;
    private int cycleCounts;
    
//    private final Encoder leftEncoder;
//    private final DigitalInput leftEncoderInput;
//    private final Encoder rightEncoder;
//    private final DigitalInput rightEncoderInput;
    
    public Drive(JoystickControl controller)
    {
        leftMotor = new Motor(TALON_LEFT_ID, Motor.TALON, TALON_LEFT_INVERTED);
        leftMotor2 = new Motor(TALON_LEFT_2_ID, Motor.TALON, TALON_LEFT_INVERTED);
        rightMotor = new Motor(TALON_RIGHT_ID, Motor.TALON, TALON_RIGHT_INVERTED);
        rightMotor2 = new Motor(TALON_RIGHT_2_ID, Motor.TALON, TALON_RIGHT_INVERTED);
        
        
//        leftEncoderInput = new DigitalInput(ENCODER_DIGITAL_SIDECAR_CHANNEL, ENCODER_LEFT_CHANNEL);
//        rightEncoderInput = new DigitalInput(ENCODER_DIGITAL_SIDECAR_CHANNEL, ENCODER_RIGHT_CHANNEL);
        
//        leftEncoder = new Encoder(leftEncoderInput, leftEncoderInput, false, EncodingType.k1X);
//        rightEncoder = new Encoder(rightEncoderInput, rightEncoderInput, false, EncodingType.k1X);
        
        
        this.controller = controller;
    }
    
    public void act()
    {
        arcadeDrive(controller.getArcadeValues());
                
        report();
    }
    
    //Index 0: left side motor value
    //Index 1: right side motor value
    public void tankDrive(double[] motorValues)
    {
        tankDrive(motorValues[0], motorValues[1]);
    }
    
    public void tankDrive(double leftValue, double rightValue)
    {
        leftValue = limit(leftValue);
        rightValue = limit(rightValue);
        
        try
        {
            leftMotor.motor.set(leftMotor.invert() * leftValue);
            leftMotor2.motor.set(leftMotor2.invert() * leftValue);
            rightMotor.motor.set(rightMotor.invert() * rightValue);
            rightMotor2.motor.set(rightMotor2.invert() * rightValue);
        }
        catch (Exception exception)
        {
            Logger.log(exception);
        }
    }
    
    //Index 0: throttle value
    //Index 1: turning value
    public void arcadeDrive(double[] motorValues)
    {
        arcadeDrive(motorValues[0], motorValues[1]);
    }
    
    public void arcadeDrive(double moveValue, double rotateValue)
    {
        double leftValue = moveValue - rotateValue;
        double rightValue = moveValue + rotateValue;
        double[] motorValues = scale( new double[]{ leftValue, rightValue } );
        
        //System.out.println(leftValue + " : " + rightValue + " :: " + motorValues);
        
        try
        {
            leftMotor.motor.set(leftMotor.invert() * motorValues[0]);
            leftMotor2.motor.set(leftMotor2.invert() * motorValues[0]);
            rightMotor.motor.set(rightMotor.invert() * motorValues[1]);
            rightMotor2.motor.set(rightMotor2.invert() * motorValues[1]);
        }
        catch (Exception e)
        {
            Logger.log(e);
        }
    }
    
    public void stopDrive()
    {
        try
        {
            leftMotor.motor.set(0);
            leftMotor2.motor.set(0);
            rightMotor.motor.set(0);
            rightMotor2.motor.set(0);
        }
        catch (Exception exception)
        {
            Logger.log(exception);
        }
    }
    
    public double limit(double value)
    {
        if (value > 1)
        {
            return 1;
        }
        if (value < -1)
        {
            return -1;
        }
        
        return value;
    }
    
    public double[] scale(double[] values)
    {
        if(controller.getSlowButton())
            slowMode(values);
            
        double scale = 1.0;
        double newValues[] = new double[values.length];
        
        for (int i = 0; i < values.length; i++)
        {
            double currentScale = 1 / Math.abs(values[i]);
            scale = scale > currentScale ? currentScale : scale;
        }
        
        if (scale < 1.0)
        {
            for (int i = 0; i < values.length; i++)
            {
                newValues[i] = scale * values[i];
            }
            
            return newValues;
        }
        else
        {
            return values;
        }
    }

    public void robotDisable()
    {
//        leftEncoder.stop();
//        rightEncoder.stop();
        
        leftMotor.motor.disable();
        leftMotor2.motor.disable();
        rightMotor.motor.disable();
        rightMotor2.motor.disable();
        
        cycleCounts = 0;
    }

    public void robotEnable()
    {
        cycleCounts = 0;
//        leftEncoder.reset();
//        leftEncoder.start();
        
//        rightEncoder.reset();
//        rightEncoder.start();
    }

    public void robotAuton()
    {
        double[] autonArcadeVals = new double[2];
        if (cycleCounts >= 0 && cycleCounts <= 110)
        {
            autonArcadeVals[0] = -0.30;
            autonArcadeVals[1] = 0.0;
        }
        else if (cycleCounts > 110)
        {
            autonArcadeVals[0] = 0.0;
            autonArcadeVals[1] = 0.0;
        }
        
        cycleCounts++;
        
        arcadeDrive(autonArcadeVals);
        
        report();
    }
    
    private void report()
    {
        SmartDashboard.putNumber("Arcade Throttle", controller.getArcadeValues()[0]);
        SmartDashboard.putNumber("Arcade Turn", controller.getArcadeValues()[1]);
    }

    private double[] slowMode(double[] values) {
        for(int i = 0; i < values.length; i++)
        {
            values[i] /= SLOW_MODE_FACTOR;
        }
        
        return values;
    }
}
