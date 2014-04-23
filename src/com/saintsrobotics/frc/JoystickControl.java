package com.saintsrobotics.frc;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The joystick control for the robot.
 *
 * @author Saints Robotics
 */
public class JoystickControl implements RobotComponent {
    
    private final Joystick driveJoystick;
    private final Joystick operatorJoystick;
    
    public static final boolean DRIVE_SQUARED_INPUTS = true;
    
    public static final double XBOX_DEAD_ZONE = 0.13;
    
    public static final double DRIVE_JOYSTICK_DEAD_ZONE = XBOX_DEAD_ZONE;
    public static final int DRIVE_JOYSTICK_PORT = 1;
    
    public static final int OPERATOR_JOYSTICK_PORT = 2;
    
    public static final int DRIVE_SLOW_MODE_FACTOR = 3;
    public static final boolean DRIVE_INVERTED = true;
    
    public static final XboxAxis ARCADE_MOVE_JOYSTICK_AXIS = XboxAxis.LEFT_THUMB_Y;
    public static final boolean ARCADE_MOVE_JOYSTICK_INVERTED = DRIVE_INVERTED;
    
    public static final XboxAxis ARCADE_ROTATE_JOYSTICK_AXIS = XboxAxis.RIGHT_THUMB_X;
    public static final boolean ARCADE_ROTATE_JOYSTICK_INVERTED = true;
    
    public static final XboxAxis TANK_LEFT_JOYSTICK_AXIS = XboxAxis.LEFT_THUMB_Y;
    public static final boolean TANK_LEFT_JOYSTICK_INVERTED = DRIVE_INVERTED;
    
    public static final XboxAxis TANK_RIGHT_JOYSTICK_AXIS = XboxAxis.RIGHT_THUMB_Y;
    public static final boolean TANK_RIGHT_JOYSTICK_INVERTED = DRIVE_INVERTED;
    
    // Button mappings
    public static final XboxButton SLOW_MODE_BUTTON = XboxButton.LEFT_BUMPER;
    public static final XboxButton SHIFT_GEAR_DOWN_BUTTON = XboxButton.RIGHT_BUMPER;
    public static final XboxButton SHIFT_GEAR_UP_BUTTON = XboxButton.Y;
    public static final XboxButton DRIVER_SHOOT_WITH_RESET_BUTTON = XboxButton.A;
    
    public static final XboxButton PICKUP_UP_BUTTON = XboxButton.RIGHT_BUMPER;
    public static final XboxButton PICKUP_DOWN_BUTTON = XboxButton.LEFT_BUMPER;
    public static final XboxButton SHOOT_WITH_RESET_BUTTON = XboxButton.A;
    public static final XboxButton SHOOT_WITHOUT_RESET_BUTTON = XboxButton.X;
    public static final XboxButton STOP_SHOOT_BUTTON = XboxButton.B;
    
    public static final XboxAxis TRIGGER_AXIS = XboxAxis.TRIGGERS;
    
    private ControlMode controlMode;
    
    private double arcadeThrottleValue = 0.0;
    private double arcadeTurnValue = 0.0;
    private double pickupValue = 0.0;
    private boolean slowButton = false;
    private boolean winchButton = false;
    private boolean pickupDownButton = false;
    private boolean winchMomentButton = false;
    private boolean winchStopButton = false;
    private boolean driverWinchButton = false;
    private boolean pickupUpButton = false;
    
    public void robotDisable() {
    }

    public void robotEnable() {
        arcadeThrottleValue = 0.0;
        arcadeTurnValue = 0.0;
        pickupValue = 0.0;
        winchButton = false;
    }
    
    public void act() {
        arcadeThrottleValue = driveJoystick.getRawAxis(ARCADE_MOVE_JOYSTICK_AXIS.value);
        arcadeTurnValue = driveJoystick.getRawAxis(ARCADE_ROTATE_JOYSTICK_AXIS.value);
        pickupValue = operatorJoystick.getRawAxis(TRIGGER_AXIS.value);
        
        slowButton = driveJoystick.getRawButton(SLOW_MODE_BUTTON.value);
        curveTurnValues();
        deadZone();
        
        if (slowButton) {
            //slowDriveValues();
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1,4,"Slow Mode: ON");
        } else {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1,4,"Slow Mode: OFF");
        }
        
        
        winchButton = operatorJoystick.getRawButton(SHOOT_WITH_RESET_BUTTON.value);
        winchMomentButton = operatorJoystick.getRawButton(SHOOT_WITHOUT_RESET_BUTTON.value);
        winchStopButton = operatorJoystick.getRawButton(STOP_SHOOT_BUTTON.value);
        driverWinchButton = driveJoystick.getRawButton(DRIVER_SHOOT_WITH_RESET_BUTTON.value);
        
        pickupUpButton = operatorJoystick.getRawButton(PICKUP_UP_BUTTON.value);
        pickupDownButton = operatorJoystick.getRawButton(PICKUP_DOWN_BUTTON.value);
    }
    
    private void deadZone() {
        if (Math.abs(arcadeThrottleValue)<DRIVE_JOYSTICK_DEAD_ZONE)
            arcadeThrottleValue = 0;
        if (Math.abs(arcadeTurnValue)<DRIVE_JOYSTICK_DEAD_ZONE)
            arcadeTurnValue = 0;
    }
    
    public void robotAuton() {
        
    }
    
    public static class ControlMode {

        public final int value;
        
        public static final ControlMode arcadeDrive = new ControlMode(0);
        
        private ControlMode(int value) {
            this.value = value;
        }
    }
    
    public JoystickControl() {
        driveJoystick = new Joystick(DRIVE_JOYSTICK_PORT);
        operatorJoystick = new Joystick(OPERATOR_JOYSTICK_PORT);

        // Default driving mode
        controlMode = ControlMode.arcadeDrive;
    }
    
    private void curveTurnValues() {
        arcadeTurnValue = 0.5*MathUtils.pow(arcadeTurnValue,3)+0.5*arcadeTurnValue;
    }
    
    public double[] getArcadeValues() {
        return new double[]{arcadeThrottleValue,arcadeTurnValue};
    }
    
    public double getPickupValue() {
        return pickupValue;
    }
    
    public boolean getPickupUpValue() {
        return pickupUpButton;
    }
    
    public boolean getPickupDownValue() {
        return pickupDownButton;
    }
    
    public boolean getSlowButton() {
        return slowButton;
    }
    
    public boolean getWinchButton() {
        return winchButton;
    }
    
    public boolean getWinchMomentButton() {
        return winchMomentButton;
    }
    
    public boolean getWinchStopButton() {
        return winchStopButton;
    }
    
    public boolean getDriverWinchButton(){
        return driverWinchButton;
    }
}
