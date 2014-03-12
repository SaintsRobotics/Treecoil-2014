package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * An abstraction over using motors.
 * @author Saints Robotics
 */
public class Motor
{
    public final SpeedController motor;
    private final boolean isInverted;
    
    public final static int TALON = 0;
    public final static int VICTOR = 1;
    
    public Motor(int motorID, int controlType, boolean isInverted)
    {
        SpeedController newMotor = null;
        
        try
        {
            if(controlType == TALON)
                newMotor = new Talon(motorID);
            else if(controlType == VICTOR)
                newMotor = new Victor(motorID);
        }
        catch (Exception exception)
        {
            Logger.log(exception);
        }
        
        this.motor = newMotor;        
        this.isInverted = isInverted;
    }
    
    public int invert()
    {
        return (isInverted ? -1 : 1);
    }
}
