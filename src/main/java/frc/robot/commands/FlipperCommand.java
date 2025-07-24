package frc.robot.commands;

import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.flipper.Flipper;
import frc.robot.subsystems.flipper.FlipperSim;
import frc.robot.subsystems.flipper.FlipperSubsystem;

public class FlipperCommand extends Command {
    private FlipperSubsystem flipperSubsystem;
    private FlipperSim flipperSim;
    private Flipper flipper;
    private final BooleanSupplier leftBumper;
    private final BooleanSupplier rightBumper;

    public FlipperCommand(Flipper flipper, BooleanSupplier leftBumper, BooleanSupplier rightBumper) {
        this.flipper = flipper;
        this.leftBumper = leftBumper;
        this.rightBumper = rightBumper;
        
        if (flipper instanceof Subsystem) {
            addRequirements((Subsystem) flipper);
        }
        
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (leftBumper != null && leftBumper.getAsBoolean()) {
            double flipperSpeed = 0.8;
            flipper.setSpeed(flipperSpeed);
        } else if (rightBumper != null && rightBumper.getAsBoolean()) {
            double flipperSpeed = -0.8;
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