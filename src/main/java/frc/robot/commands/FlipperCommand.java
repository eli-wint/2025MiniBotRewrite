package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.flipper.Flipper;
import frc.robot.subsystems.flipper.FlipperIO;

public class FlipperCommand extends Command {
    private FlipperIO flipper;
    private final BooleanSupplier leftBumper;
    private final BooleanSupplier rightBumper;

    public FlipperCommand(FlipperIO flipper2, BooleanSupplier leftBumper, BooleanSupplier rightBumper) {
        this.flipper = flipper2;
        this.leftBumper = leftBumper;
        this.rightBumper = rightBumper;

        addRequirements(RobotContainer.flipper);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (leftBumper != null && leftBumper.getAsBoolean()) {
            double flipperSpeed = 0.2;
            flipper.setSpeed(flipperSpeed);
        } else if (rightBumper != null && rightBumper.getAsBoolean()) {
            double flipperSpeed = -0.2;
            flipper.setSpeed(flipperSpeed);
        } else {
            flipper.setSpeed(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        flipper.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}