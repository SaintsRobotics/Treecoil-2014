package com.saintsrobotics.frc;

/**
 * A type-safe enum for the Xbox buttons.
 *
 * @author Saints Robotics
 */
public final class XboxButton {

    public final int value;
    
    protected static final int A_VAL = 1;
    protected static final int B_VAL = 2;
    protected static final int X_VAL = 3;
    protected static final int Y_VAL = 4;
    protected static final int LEFT_BUMPER_VAL = 5;
    protected static final int RIGHT_BUMPER_VAL = 6;
    protected static final int BACK_VAL = 7;
    protected static final int START_VAL = 8;
    protected static final int LEFT_THUMBSTICK_VAL = 9;
    protected static final int RIGHT_THUMBSTICK_VAL = 10;
    
    public static final XboxButton A = new XboxButton(A_VAL);
    public static final XboxButton B = new XboxButton(B_VAL);
    public static final XboxButton X = new XboxButton(X_VAL);
    public static final XboxButton Y = new XboxButton(Y_VAL);
    public static final XboxButton LEFT_BUMPER = new XboxButton(LEFT_BUMPER_VAL);
    public static final XboxButton RIGHT_BUMPER = new XboxButton(RIGHT_BUMPER_VAL);
    public static final XboxButton BACK = new XboxButton(BACK_VAL);
    public static final XboxButton START = new XboxButton(START_VAL);
    public static final XboxButton LEFT_THUMBSTICK = new XboxButton(LEFT_THUMBSTICK_VAL);
    public static final XboxButton RIGHT_THUMBSTICK = new XboxButton(RIGHT_THUMBSTICK_VAL);
    
    private XboxButton(int value) {
        this.value = value;
    }
    
    public int hashCode() {
        int hash = 3;
        hash = 89*hash+this.value;
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (obj==null)
            return false;
        
        if (getClass()!=obj.getClass())
            return false;
        
        final XboxButton other = (XboxButton) obj;
        return this.value==other.value;
    }
}
