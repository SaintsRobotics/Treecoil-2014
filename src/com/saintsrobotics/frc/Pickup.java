/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Saints Robotics
 */
public class Pickup implements RobotComponent {
    
    private final int PICKUP_MOTOR_ID = 9;
    private final int PICKUP_MOTOR_TYPE = Motor.VICTOR;
    private final boolean PICKUP_MOTOR_INVERTED = false;
    
    private final int PICKUP_SWITCH_CHANNEL = 2;
    private final DigitalInput pickupSwitch;
    private final JoystickControl controller;
    
    private final Motor pickupMotor;
    
    public Pickup(JoystickControl controller) {
        this.controller = controller;
        
        pickupMotor = new Motor(PICKUP_MOTOR_ID,PICKUP_MOTOR_TYPE,PICKUP_MOTOR_INVERTED);
        pickupSwitch = new DigitalInput(PICKUP_SWITCH_CHANNEL);
    }
    
    public void robotDisable() {
        pickupMotor.motor.disable();
    }
    
    public void robotEnable() {
        
    }
    
    public void robotAuton() {
        
    }
    
    public void act() {
        double pickupSpd = 0.0;
        
        if (controller.getPickupButton())
            pickupSpd += 1.0;
        if (controller.getReleasePickupButton())
            pickupSpd -= 1.0;
        
        pickupMotor.motor.set(pickupSpd);
    }
}
