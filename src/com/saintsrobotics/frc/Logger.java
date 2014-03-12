package com.saintsrobotics.frc;

/**
 * Logs any messages, warnings, and errors while running the robot.
 * @author Saints Robotics
 */
public class Logger
{
    /**
     * Disallow instantiation.
     */
    private Logger() {}
    
    /**
     * Log the exception by converting the exception to a string.
     * @param exception
     */
    public static void log(Exception exception)
    {
        String message = exception.toString();
        Logger.log(message);
    }
    
    /**
     * Log the message by printing it out to the output panel.
     * @param message 
     */
    public static void log(String message)
    {
        System.out.println(message);
    }
}
