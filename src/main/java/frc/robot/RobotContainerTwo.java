// package frc.robot;

// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import frc.robot.commands.DriveFromControllerCommand;
// import frc.robot.commands.FlipperCommand;
// import frc.robot.subsystems.FlipperSubsystem;
// import frc.robot.subsystems.drive.DriveSubsystem;

// // REWRITTEN

// public class RobotContainerTwo {
//     // Setting controller ports
//     private final XboxController driverController = new XboxController(0);
//     private final XboxController operatorController = new XboxController(1);
//     // Initializing subsystems?
//     private final FlipperSubsystem flipperSubsystem = new FlipperSubsystem();
//     private final DriveSubsystem driveSubsystem = new DriveSubsystem();

//     private final DriveFromControllerCommand driveFromController = new DriveFromControllerCommand(driveSubsystem,
//             driverController::getRightX,
//             driverController::getRightY,
//             driverController::getRightTriggerAxis,
//             driverController::getLeftTriggerAxis);
//     // Will using the same controller for different tasks cause an error? (no lol)
//     private final FlipperCommand flipperJoystick = new FlipperCommand(flipperSubsystem, driverController::getLeftBumperButton, driverController::getRightBumperButton);

//     public RobotContainerTwo() {
//         // Putting commands in the right subsystem
//         driveSubsystem.setDefaultCommand(driveFromController);
//         flipperSubsystem.setDefaultCommand(flipperJoystick);

//         configureTriggerBindings();
//     }

//     private void configureTriggerBindings() {
//         // Resets encoder position when "start" is pressed
//         JoystickButton resetButton = new JoystickButton(operatorController, XboxController.Button.kStart.value);
//         resetButton.onTrue(new InstantCommand(() -> flipperSubsystem.resetPivotEncoder()));
//     }
// }