/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;

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
    
    private final int PICKUP_RETRACTOR_MOTOR_ID = 10;
    private final int PICKUP_RETRACTOR_MOTOR_TYPE = Motor.VICTOR;
    private final boolean PICKUP_RETRACTOR_MOTOR_INVERTED = true;
    
    private final int PICKUP_UP_SWITCH_CHANNEL = 4;
    private final DigitalInput pickupUpSwitch;
    private final int PICKUP_DOWN_SWITCH_CHANNEL = 3;
    private final DigitalInput pickupDownSwitch;
    
    private final Motor pickupMotor;
    
    private final Motor pickupRetractorMotor;
    
    public Pickup(JoystickControl controller) {
        this.controller = controller;
        
        pickupMotor = new Motor(PICKUP_MOTOR_ID,PICKUP_MOTOR_TYPE,PICKUP_MOTOR_INVERTED);
        pickupRetractorMotor = new Motor(PICKUP_RETRACTOR_MOTOR_ID, PICKUP_RETRACTOR_MOTOR_TYPE, PICKUP_RETRACTOR_MOTOR_INVERTED);
        pickupSwitch = new DigitalInput(PICKUP_SWITCH_CHANNEL);
        pickupUpSwitch = new DigitalInput(PICKUP_UP_SWITCH_CHANNEL);
        pickupDownSwitch = new DigitalInput(PICKUP_DOWN_SWITCH_CHANNEL);
    }
    
    public void robotDisable() {
        pickupMotor.motor.disable();
    }
    
    public void robotEnable() {
        
    }
    
    public void robotAuton() {
    }
    
    public void act() {
        double pickupSpd;
        
        pickupSpd = -controller.getPickupValue();
        pickupMotor.motor.set(pickupSpd);
        
        double pickupRetractSpd = 0;
        
        
        if(controller.getPickupDownValue() && pickupDownSwitch.get())
            pickupRetractSpd -= 1.0;
        
        
        if(controller.getPickupUpValue() && pickupUpSwitch.get())
        {
            pickupRetractSpd += 1.0;
            pickupRetractSpd *= .5;
        }
        
        pickupRetractorMotor.motor.set(pickupRetractSpd);
    }
}
