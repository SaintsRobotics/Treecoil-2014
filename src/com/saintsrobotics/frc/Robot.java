package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.io.IOException;

public class Robot extends IterativeRobot {
    
    private Ultrasonic ultrasonic;
    private JoystickControl controlSystem;
    private Drive drive;
    private Shooter shooter;
    private Pickup pickup;
    
    private NetworkTable networkTable;
    
    private RobotComponent[] components;
    
    private int autonCycles;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        ultrasonic = new Ultrasonic();
        controlSystem = new JoystickControl();
        drive = new Drive(controlSystem);
        shooter = new Shooter(controlSystem);
        pickup = new Pickup(controlSystem);
        
        components = new RobotComponent[]{ultrasonic,controlSystem,drive,shooter,pickup};
    }
    
    /** Called at beginning of auton. */
    public void autonomousInit() {
        LightShow.setAuton();
        Logger.log("Autonomous has begun!");
        enabledRoutine();
        autonCycles = 0;
    }
    
    /** Called throughout auton. */
    public void autonomousPeriodic() {
        autonomousRoutine();
        printUltrasonic();
        
        DriverStationComm.printMessage(DriverStationLCD.Line.kUser5,1,
                "Auton Cycle: " + autonCycles);
        autonCycles++;
    }
    
    /** Called at beginning of teleop. */
    public void teleopInit() {
        LightShow.setTeleop();
        Logger.log("Teleop has begun!");
        enabledRoutine();
    }
    
    /** Called throughout teleop. */
    public void teleopPeriodic() {
        actionRoutine();
        printUltrasonic();
    }
    
    /** Called at beginning of disable phase. */
    public void disabledInit() {
        LightShow.setDisabled();
        Logger.log("The robot has been disabled :(");
        disabledRoutine();
    }
    /** Called throughout disable phase. */
    public void disabledPeriodic() { }
    
    /** Called at the beginning of test mode. */
    public void testInit() {
        Logger.log("Test mode has begun.");
    }
    
    /** Called throughout test phase. */
    public void testPeriodic() { }
                
    /**
     * Setup Network Tables, and get the NetworkTable for the SmartDashboard.
     *
     * @return The network table for the SmartDashboard.
     */
    private NetworkTable getNetworkTable() {
        NetworkTable.setTeam(1899);
        NetworkTable.setServerMode();
        try {
            NetworkTable.initialize();
        } catch (IOException exception) {
            Logger.log(exception);
        }
        return NetworkTable.getTable("SmartDashboard");
    }
    
    private void disabledRoutine() {
        for (int i=0;i<components.length;i++) {
            components[i].robotDisable();
        }
    }
    
    private void enabledRoutine() {
        for (int i=0;i<components.length;i++) {
            components[i].robotEnable();
        }
    }
    
    private void actionRoutine() {
        for (int i=0;i<components.length;i++) {
            components[i].act();
        }
    }
    
    private void autonomousRoutine() {
        for (int i=0;i<components.length;i++) {
            components[i].robotAuton();
        }
    }
    
    private void printUltrasonic() {
        DriverStationComm.printMessage(DriverStationLCD.Line.kUser3,1,
                "Range: " + Double.toString(Math.floor(ultrasonic.getDistance()*100.0)/100.0));
    }
}
