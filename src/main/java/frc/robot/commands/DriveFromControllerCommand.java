package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.DriveSubsystem;

public class DriveFromControllerCommand extends Command {
  private static final double SLOW_FORWARD_RATIO = .5;
  private static final double SLOW_FORWARD_CURVE = 2;
  private static final double SLOW_ROTATION_RATIO = .5;
  private static final double SLOW_ROTATION_CURVE = 3;

  private static final double MAX_FOWARD_RATIO = 1.0;// smaller = less power
  private static final double MAX_FORWARD_CURVE = 3; // larger = more controll at small joystick values
  private static final double MAX_ROTATION_RATIO = 0.6;
  private static final double MAX_ROTATION_CURVE = 3;

  private final DriveSubsystem driveSubsystem;
  private final DoubleSupplier forward;
  private final DoubleSupplier rotation;
  private final DoubleSupplier slowModeTrigger;
  private final DoubleSupplier fullSpeedTrigger;

  /**
   * Constructs a new {@link DriveFromControllerCommand} instance.
   * 
   * @param driveSubsystem The drive subsystem to control.
   * @param liftObserver   Used to read information about the lift subsystem.
   * @param forward        Used to read the forward input.
   * @param rotation       Used to read the rotation input.
   */
  public DriveFromControllerCommand(
      DriveSubsystem driveSubsystem,
      DoubleSupplier forward,
      DoubleSupplier rotation,
      DoubleSupplier slowModeTrigger,
      DoubleSupplier fullSpeedTrigger) {
    this.driveSubsystem = driveSubsystem;
    this.forward = forward;
    this.rotation = rotation;
    this.slowModeTrigger = slowModeTrigger;
    this.fullSpeedTrigger = fullSpeedTrigger;
    addRequirements(driveSubsystem);
  }

  @Override
  public void execute() {

    // set dummy values if something fails
    double forwardRatio = .6; // .6
    double forwardCurve = 1.5;
    double rotationRatio = .6;
    double rotationCurve = 2;

    if (slowModeTrigger.getAsDouble() > .3) {
      forwardRatio = SLOW_FORWARD_RATIO;
      forwardCurve = SLOW_FORWARD_CURVE;
      rotationRatio = SLOW_ROTATION_RATIO;
      rotationCurve = SLOW_ROTATION_CURVE;
    } else if (fullSpeedTrigger.getAsDouble() > .3) {
      forwardRatio = MAX_FOWARD_RATIO;
      forwardCurve = MAX_FORWARD_CURVE;
      rotationRatio = MAX_ROTATION_RATIO;
      rotationCurve = MAX_ROTATION_CURVE;
    } else {
      forwardRatio = (MAX_FOWARD_RATIO + SLOW_FORWARD_RATIO) / 2.0 - .05; // Average, in-between (but adjusted).
      forwardCurve = SLOW_FORWARD_CURVE;
      rotationRatio = (MAX_ROTATION_RATIO + SLOW_ROTATION_RATIO) / 2.0; // Average
      rotationCurve = SLOW_ROTATION_CURVE;
    }

    driveSubsystem.arcadeDrive(
        computeInputCurve(forwardRatio * forward.getAsDouble(), forwardCurve),
        computeInputCurve(-rotationRatio * rotation.getAsDouble(), rotationCurve));
  }

  private double computeInputCurve(double rawInput, double power) {
    var sign = rawInput < 0 ? 1 : -1;
    return Math.pow(Math.abs(rawInput), power) * sign;
  }
}