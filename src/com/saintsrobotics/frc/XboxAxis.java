package com.saintsrobotics.frc;

/**
 * A type-safe enum for the Xbox axes.
 * @author Saints Robotics
 */
public final class XboxAxis {
    public final int value;
    
    protected static final int LEFT_THUMB_X_VAL = 3;
    protected static final int LEFT_THUMB_Y_VAL = 2;
    protected static final int RIGHT_THUMB_X_VAL = 4;
    protected static final int RIGHT_THUMB_Y_VAL = 5;
    
    public static final XboxAxis LEFT_THUMB_X = new XboxAxis(LEFT_THUMB_X_VAL);
    public static final XboxAxis LEFT_THUMB_Y = new XboxAxis(LEFT_THUMB_Y_VAL);
    public static final XboxAxis RIGHT_THUMB_X = new XboxAxis(RIGHT_THUMB_X_VAL);
    public static final XboxAxis RIGHT_THUMB_Y = new XboxAxis(RIGHT_THUMB_Y_VAL);
    
    private XboxAxis(int value) {
        this.value = value;
    }
    
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.value;
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        final XboxAxis other = (XboxAxis) obj;
        return this.value == other.value;
    }
}
