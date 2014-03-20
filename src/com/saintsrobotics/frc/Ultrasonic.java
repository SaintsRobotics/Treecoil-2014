package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.AnalogChannel;

public class Ultrasonic implements RobotComponent {
    
    // Volts per inch.
    public static final double scalingFactor = 0.009765625;
    
    // Port.
    private static final int ULTRASONIC_CHANNEL = 2;
    
    private AnalogChannel ultrasonic;
    private double measuredVoltage = 0.0;
    
    public void robotDisable() {
        ultrasonic.free();
    }

    public void robotEnable() {
        ultrasonic = new AnalogChannel(ULTRASONIC_CHANNEL);
    }

    public void robotAuton() {
        measuredVoltage = ultrasonic.getVoltage();
    }
    
    public void act() {
        measuredVoltage = ultrasonic.getVoltage();
    }
    
    /** @return Distance to target IN FEET. */
    public double getDistance() {
        return (measuredVoltage/scalingFactor)*12;
    }
}