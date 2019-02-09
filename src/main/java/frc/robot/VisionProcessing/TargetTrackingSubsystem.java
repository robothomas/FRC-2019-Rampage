/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.VisionProcessing;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Utilizing the PID loop from the {@link RotateToPanelSubsystem} and its own PID loop judging how fast
 * to go with the given distance, this subsystem moves towards the target until it is at a precise theshold
 * that the robot can place the hatch. 
 * 
 * @see RotateToPanelSubsystem 
 * @see CalculateTargetDistance 
 * @see GoToPanel
 */
public class TargetTrackingSubsystem extends PIDSubsystem {
  
  //double area = Constants.ta.getDouble(0.0);

  public TargetTrackingSubsystem() {

    // Insert a subsystem name and PID values here
    super("Hatch Tracker", 1, 2, 3);

    // Use these to get going:
    setSetpoint(Constants.HATCH_CONNECTION_DISTANCE); // System starts with hatch connection by default

    setInputRange(0f, 10f);

    setOutputRange(-1, 1);

    setAbsoluteTolerance(Constants.fowardErrorTolerance);

    enable(); // Enables the PID controller.

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    return CalculateTargetDistance.getDistance();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use both outputs in drive
    Robot.drivetrain.ArcadeDrive(output, Robot.panelRotation.PIDOut);
  }
}
