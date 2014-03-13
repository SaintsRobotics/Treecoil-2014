package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 * @author Saints Robotics
 */
public class LightShow {
    
    private static final int MODULE_NUMBER = 1;
    
    private static final int PIN1 = 12;
    private static final int PIN2 = 13;
    private static final int PIN3 = 14;
    
    private static final DigitalOutput LIGHT1 = new DigitalOutput(MODULE_NUMBER,PIN1);
    private static final DigitalOutput LIGHT2 = new DigitalOutput(MODULE_NUMBER,PIN2);
    private static final DigitalOutput LIGHT3 = new DigitalOutput(MODULE_NUMBER,PIN3);
    
    public LightShow() {
        
    }
    
    public static void setAuton() {
        setPins(false,false,false);
    }
    
    public static void setTeleop() {
        setPins(false,false,true);
    }
    
    public static void setShootStandby() {
        setPins(false,true,false);
    }
    
    public static void setShoot() {
        setPins(false,true,true);
    }
    
    public static void setDisabled() {
        setPins(true,false,false);
    }
    
    public static void setPins(boolean p1, boolean p2, boolean p3) {
        LIGHT1.set(p1);
        LIGHT2.set(p2);
        LIGHT3.set(p3);
    }
}
