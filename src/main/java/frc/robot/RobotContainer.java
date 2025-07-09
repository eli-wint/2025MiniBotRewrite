package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveFromControllerCommand;
import frc.robot.commands.FlipperCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FlipperSubsystem;

public class RobotContainer {
    private final XboxController driverController = new XboxController(Constants.CONTROLLER_DRIVE_PORT);
    private final XboxController operatorController = new XboxController(Constants.CONTROLLER_OPERATOR_PORT);

    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final FlipperSubsystem flipperSubsystem = new FlipperSubsystem();

    private final DriveFromControllerCommand driveFromController = new DriveFromControllerCommand(
            driveSubsystem,
            driverController::getLeftY,
            driverController::getRightX,
            driverController::getLeftTriggerAxis,
            driverController::getRightTriggerAxis);
        
    private final FlipperCommand manual = new FlipperCommand(flipperSubsystem, operatorController::getLeftY);


    public RobotContainer() {
        driveSubsystem.setDefaultCommand(driveFromController);
        flipperSubsystem.setDefaultCommand(manual);

        configureTriggerBindings();
    }

    private void configureTriggerBindings() {
        JoystickButton resetButton = new JoystickButton(operatorController, XboxController.Button.kStart.value);
        resetButton.onTrue(new InstantCommand(() -> flipperSubsystem.resetPivotEncoder()));
    }
}