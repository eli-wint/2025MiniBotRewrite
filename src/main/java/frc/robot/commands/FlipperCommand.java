package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.flipper.FlipperSubsystem;
import frc.robot.subsystems.flipper.FlipperIO;

public class FlipperCommand extends Command {
    private FlipperSubsystem flipperSubsystem;
    private final BooleanSupplier leftBumper;
    private final BooleanSupplier rightBumper;

    public FlipperCommand(FlipperSubsystem flipperSubsystem, BooleanSupplier leftBumper, BooleanSupplier rightBumper) {
        this.flipperSubsystem = flipperSubsystem;
        this.leftBumper = leftBumper;
        this.rightBumper = rightBumper;

        addRequirements(RobotContainer.flipperSubsystem);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (leftBumper != null && leftBumper.getAsBoolean() && !flipperSubsystem.isAtUpperEndpoint()) {
            double flipperSpeed = 0.2;
            flipperSubsystem.setVelocity(flipperSpeed);
        } else if (rightBumper != null && rightBumper.getAsBoolean() && !flipperSubsystem.isAtLowerEndpoint()) {
            double flipperSpeed = -0.2;
            flipperSubsystem.setVelocity(flipperSpeed);
        } else {
            flipperSubsystem.setVelocity(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        flipperSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}