# FRC-2019-Rampage
Command based code for our 2019 robot, Rampage. 

## [Drive](/src/main/java/frc/robot/Drive)

* ### Drive Subsystem
Accessed through Robot.drivetrain, provides basis for all wheel movements, whether through teleOp with Drive(), or when autonomously moving with the vision tracking system system

* ### Drive (Command)
Utilizing drivetrain (DriveSubsystem), this drives the robot around based on the two joystick inputs from a Logitech Xbox Controller on X mode.

* ### Sensitivity Subsystem
Switches drivetrain between lowGear speed and highGear speed (although highGear is not technically the fastest speed, this
will be updated for consistency later)

* ### Change Sensitivity (Command)
Utilizes the Sensitivity Subsystem to switch sensitivity indirectly, while the Drive Subsystem switches the speed.

* ### Driver Mode Subsystem
Switches a value that can make the drivetrain go max speed (as fast as driver is willing to go) and switch motors to cruise (to reduce tipping), and back to normal again when necessary. This is primarily for defensive purposes, as qualification matches have shown that most alliances have one defensive bot.

* ### Switch Driving Mode (Command)
Utilizes Driver Mode Subsystem to switch between "offensive" and "defensive" mode. Drive Subsystem uses the values this changes to switch the mode.

## [Beak](/src/main/java/frc/robot/Beak)

* ### Beak Subsystem
Controls whether the hatch holding mechanism we call the "beak" is extended out or folded in, and can switch the current state.

* ### Beak In (Command)
Moves the the beak to the folded in state.

* ### Beak Out (Command)
Moves the beak to the extended out state

* ### Move Beak (Command)
Switches the beak's state between folded in and extended out

## [Neck](/src/main/java/frc/robot/Neck)

* ### Neck Subsystem
Controls whether the extension that moves the beak in and out of the robot frame called the "neck" is outside or inside the frame, and can switch current state.

* ### Neck In (Command)
Retracts the neck so that the beak is inside the frame

* ### Neck Out (Command)
Extends the neck so that the beak is outside the frame.

* ### Move Neck (Command)
Switches neck's state between retracted and extended

## [Tail](/src/main/java/frc/robot/Tail)

* ### Tail Subsystem
Controls whether the ramp extension, the "tail", is raised or lowered

* ### Tail In (Command)
If the ramp is already deployed, lowers it to the ground

* ### Tail Out (Command)
Deploys ramp, also can raise ramp after deployment and lowering so it does not count as support during endgame

* ### Move Tail (Command)
Switch tail state between the "out" and "in" states.

## [Vision Processing](/src/main/java/frc/robot/VisionProcessing)

* ### Limelight Subsystem
Accessed through Robot.limelight, this controls the basic systems of the limelight besides the values, such as the ledMode, which configures light settings, the pipline, which tells the limelight what to look for, and camMode, whether the camera is currently being used for vision processing or driver viewing.

* ### Rotate To Panel Subsystem
Accessed through Robot.panelRotation, this subsystem uses a built-in PID controller, which will rotate to the hatch panel using the vision tape as a guide, and Limelight x-angle differences as feedback. This can run alone through the RotateToHatchPanlel() command, or as an extra guidance mechanism through the TargetTrackingSubsystem into the GoToHatchPanel() command, where it can adjust to any small errors while the robot is moving forward.

* ### Rotate To Panel (Command)
Utilizes drivetrain (DriveSubsystem), limelight (LimelightSubsystem), and panelRotation (RotateToPanelSubsystem). This enables the RotateToPanelSubsystem PID loop, initiates limelight settings, and stops the drivetrain once the initialize() method is called. The isFinished() method is called when the RotateToPanelSubsystem declares that it has successfully rotated to the target with the onTarget() method, or found that it lost the target with the noValidTarget() method. After the command has done its job, it stops the drivetrain to keep the position and disables the PID loop since there is no more need for calculations.

* ### Calculate Target Distance
A simple file that calculates distance from a target using known heights and angles taken from Constants and the Limelight. Currently retired due to an inconsistency in distance updating.

* ### Target Tracking Subsystem
Accessed through Robot.visionTracker, this subsystem uses a built-in PID controller, which will move the robot up to the hatch panel using the vision tape area as feedback. This subsystem also utilizes the RotateToPanelSubsystem to keep the robot on track. This passes both of these outputs to curvatureDrive, where quick adjustments can be made with IsRotating as necessary, although there are few cases that would knock the robot askew enough to use IsRotating.

* ### Go To Panel (Command)
Utilizes drivetrain (DriveSubsystem), limelight (LimelightSubsystem), panelRotation (RotateToPanelSubsystem), and visionTracker (TargetTrackingSubsystem). In the initialize() method, the value of dependent is changed to true for the RotateToPanelSubsystem to make it cooperate with the TargetTrackingSubsystem by outputting values rather than sending them to rotation(), the drivetrain is stopped, limelight settings are initialized, and both the TargetTrackingSubsystem and RotateToPanelSubsystem's PID loops are enabled. The isFinished() method is more complicated, as both subsystems need to be on target in order to stop the command. The end() method disables both of the PID loops and stops the drivetrain to keep the position. 

* ### Go To Rocket Shooting Position
Utilizes drivetrain (DriveSubsystem), limelight (LimelightSubsystem), panelRotation (RotateToPanelSubsystem), and visionTracker (TargetTrackingSubsystem). Goes to a position to shoot a ball into the rocket utilizing similar settings to the GoToPanel() command.

## [Other Files](/src/main/java/frc/robot)

* ### Main
Initiates program, something that is not touched

* ### Robot
This is the main controller of all robot behavior through multiple phases - disabled, autonomous, and teleOp. In here is the Scheduler, a WPILib class which determines when commands are active and inactive - integrated mainly with the OI, which calls the majority of commands through buttons.

* ### Constants
Keeps track of limelight values and settings through NetworkTables and constant values, namely heights of various things in the field and the mounted camera angle, for use with the CalculateTargetDistance class.

* ### Robot State
Has multiple variables that track the robot's state - currently just what speed setting it is at or a specific solenoid position.

* ### OI (Operator Interface)
Initializes and maps the xbox controller and its various buttons, along with mapping buttons to specific commands

* ### RobotMap
Initializes and maps all of the motors on the robot
