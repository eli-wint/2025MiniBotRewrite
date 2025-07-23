package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlipperSubsystem;

public class FlipperCommand extends Command {
    private final FlipperSubsystem flipperSubsystem;
    private final BooleanSupplier leftBumper;
    private final BooleanSupplier rightBumper;

    public FlipperCommand(FlipperSubsystem flipperSubsystem, BooleanSupplier leftBumper, BooleanSupplier rightBumper) {
        this.flipperSubsystem = flipperSubsystem;
        this.leftBumper = leftBumper;
        this.rightBumper = rightBumper;
        addRequirements(flipperSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (leftBumper != null && leftBumper.getAsBoolean()) {
            double flipperSpeed = 0.8;
            flipperSubsystem.setSpeed(flipperSpeed);
        } else if (rightBumper != null && rightBumper.getAsBoolean()) {
            double flipperSpeed = -0.8;
            flipperSubsystem.setSpeed(flipperSpeed);
        } else {
            flipperSubsystem.setSpeed(0);
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