package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.AnalogChannel;

public class Ultrasonic implements RobotComponent {
    
    // Volts per inch.
    public static final double scalingFactor = 0.009765625;
    
    // Port.
    private static final int ULTRASONIC_CHANNEL = 2;
    
    private final AnalogChannel ultrasonic;
    private double measuredVoltage = 0.0;
    
    public Ultrasonic() {
        ultrasonic = new AnalogChannel(ULTRASONIC_CHANNEL);
    }
    
    public void robotDisable() { }
    
    public void robotEnable() { }
    
    public void robotAuton() {
        measuredVoltage = ultrasonic.getVoltage();
    }
    
    public void act() {
        measuredVoltage = ultrasonic.getVoltage();
    }
    
    /** @return Distance to target IN FEET. */
    public double getDistance() {
        return (measuredVoltage/9800)/12;
    }
}