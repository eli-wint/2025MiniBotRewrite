package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlipperSubsystem;

public class FlipperCommand extends Command {
    private final FlipperSubsystem flipperSubsystem;
    private final DoubleSupplier leftJoystick;

    public FlipperCommand(FlipperSubsystem flipperSubsystem, DoubleSupplier leftJoystick) {
        this.flipperSubsystem = flipperSubsystem;
        this.leftJoystick = leftJoystick;
        addRequirements(flipperSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double flipperSpeed = -leftJoystick.getAsDouble() * .8; 
        if (flipperSpeed < 0) flipperSpeed *= .2;

        if (Math.abs(flipperSpeed) < 0.05) { // dead-zone to prevent controller drift
            flipperSpeed = 0;
        }

        flipperSubsystem.setSpeed(flipperSpeed);
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