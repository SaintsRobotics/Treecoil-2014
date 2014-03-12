package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/**
 * The shooter for the robot.
 *
 * @author Saints Robotics
 */
public class Shooter implements IRobotComponent
{
    private final int WINCH_RELAY_CHANNEL = 1;
    private final int WINCH_DIGITAL_SIDECAR_SLOT = 1;
    private final int WINCH_DIGITAL_CHANNEL = 1;
    private final Relay WINCH;
    private final DigitalInput shooterSwitch;
    private final JoystickControl controller;
    
    private final static int STOP_MODE = 0;
    private final static int MOMENT_MODE = 1;
    private final static int RESET_MODE = 2;
    
    private boolean lastSwitched;
    private int cycleCounts;
    private double lastShotCount;
    private boolean autoShoot;
    private int shootMode;

    public Shooter(JoystickControl controller)
    {
        this.controller = controller;

        WINCH = new Relay(WINCH_RELAY_CHANNEL);
        WINCH.setDirection(Relay.Direction.kForward);
        shooterSwitch = new DigitalInput(WINCH_DIGITAL_SIDECAR_SLOT, WINCH_DIGITAL_CHANNEL);

        cycleCounts = 0;
    }

    public void robotDisable()
    {
        cycleCounts = 0;
        lastShotCount = 0;
    }

    public void robotEnable()
    {
        cycleCounts = 0;
        lastSwitched = shooterSwitch.get();
        SmartDashboard.putBoolean("Limit", lastSwitched);
    }
    
    public void robotAuton()
    {
        if (cycleCounts >= 120 && cycleCounts <= 130)
        {
            autoShoot = true;
            lastShotCount = Timer.getFPGATimestamp();
        }
        else if (cycleCounts > 130)
        {
            autoShoot = false;
        }
        
        cycleCounts++;
        
        if (!shooterSwitch.get() && !lastSwitched)
        {
            WINCH.set(Relay.Value.kOff);
//            LightShow.SetShootStandby();
        }
        else if (autoShoot)
        {
            WINCH.set(Relay.Value.kOn);
//            LightShow.SetShoot();
        }

        lastSwitched = !shooterSwitch.get();
        report();
    }

    public void act()
    {
        if(controller.getWinchButton())
            shootMode = RESET_MODE;
        else if(controller.getWinchMomentButton())
            shootMode = MOMENT_MODE;
        else if(controller.getWinchStopButton())
            shootMode = STOP_MODE;
        
        if(shootMode == RESET_MODE)
        {
            if (!shooterSwitch.get() && !lastSwitched)
            {
                WINCH.set(Relay.Value.kOff);
            }
            else if (controller.getWinchButton())
            {
                WINCH.set(Relay.Value.kOn);
                lastShotCount = Timer.getFPGATimestamp();
            }

            lastSwitched = !shooterSwitch.get();
            SmartDashboard.putBoolean("Limit", lastSwitched);
        }
        else if(shootMode == MOMENT_MODE)
        {
            if(controller.getWinchMomentButton())
            {
                WINCH.set(Relay.Value.kOn);
            }
            else
            {
                WINCH.set(Relay.Value.kOff);
            }
        }
        else if(shootMode == STOP_MODE)
        {
            WINCH.set(Relay.Value.kOff);
        }
        report();
    }

    private void report()
    {
//        DriverStationComm.printMessage(DriverStationLCD.Line.kUser2, 1, "Shoot Spd: " + Double.valueOf(currentSpeed).toString());
//        DriverStationComm.printMessage(DriverStationLCD.Line.kUser3, 1, "Shoot Pwr: " + Double.valueOf(controller.getShooterSpeed() * 5000).toString());
//        SmartDashboard.putNumber("Shooter Speed", currentSpeed);
//        SmartDashboard.putNumber("Shooter Power", controller.getShooterSpeed() * 5000);
//        SmartDashboard.putBoolean("Limit Switch", shooterSwitch.get());
//        SmartDashboard.putNumber("Last Shot Time", lastShotCount - Timer.getFPGATimestamp());
    }
}
