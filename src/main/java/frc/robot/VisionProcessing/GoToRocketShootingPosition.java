/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.VisionProcessing;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Starts {@link RotateToPanelSubsystem} and {@link TargetTrackingSubsystem} PID loops and initializes
 * the limelight. Once finished, this will bring the robot to a shooting position for a rocket port.
 * 
 * @see RotateToPanelSubsystem 
 * @see TargetTrackingSubsystem
 * @see LimelightSubsystem
 */
public class GoToRocketShootingPosition extends Command {
  public GoToRocketShootingPosition() {
    // Use requires() here to declare subsystem dependencies
    
    requires(Robot.drivetrain);

    requires(Robot.panelRotation);

    requires(Robot.limelight);

    requires(Robot.visionTracker);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.panelRotation.dependent = true;

    Robot.drivetrain.stop(); // makes sure nothing is moving before the correction begins

    Robot.limelight.lightOn();
    Robot.limelight.visionMode();

    Robot.visionTracker.enable();
    Robot.panelRotation.enable();

    Robot.visionTracker.setSetpoint(Constants.ROCKET_SHOOTING_AREA);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.visionTracker.onTarget() && Robot.panelRotation.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // disabling pid subsystems - they have finished their jobs, no need to continue moving
    Robot.panelRotation.disable();
    Robot.visionTracker.disable();

    Robot.drivetrain.stop(); // stops any last motion from drivetrain to keep the target position
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
