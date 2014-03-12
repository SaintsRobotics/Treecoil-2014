/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saintsrobotics.frc;

/**
 *
 * @author huadianz
 */
public interface IRobotComponent
{
    public void robotDisable();
    public void robotEnable();
    public void robotAuton();
    public void act();
}
