package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Constants {
  // ControllerPorts
  public static final int CONTROLLER_DRIVE_PORT = 0;
  public static final int CONTROLLER_OPERATOR_PORT = 1;

  public final class IDs {
    // Drivetrain
    public static final int DRIVE_LEFT_LEAD_MOTOR = 10;
    public static final int DRIVE_LEFT_FOLLOW_MOTOR = 11;
    public static final int DRIVE_RIGHT_LEAD_MOTOR = 12;
    public static final int DRIVE_RIGHT_FOLLOW_MOTOR = 13;

    // Flipper
    public static final int FLIPPER_MOTOR = 14;

  }

  public static final Mode simMode = Mode.SIM;
    public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

    public static enum Mode {
      /** Running on a real robot. */
      REAL,

      /** Running a physics simulator. */
      SIM,

      /** Replaying from a log file. */
      REPLAY
    }

  // Flipper Constants
  public static final double UPPER_ENDPOINT = -7;
  public static final double LOWER_ENDPOINT = -17;
  public static final double FLIPPER_PARKED = 1;
  public static final double FLIPPER_TO_CLOSE = -10;
  public static final double FLIPPER_DOWN = -16.5;
}