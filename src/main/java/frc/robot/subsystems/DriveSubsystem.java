package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
    private static final double DRIVESTRAIGHT_PID_P = 0.8;
    private static final double DRIVESTRAIGHT_PID_I = 0.0;
    private static final double DRIVESTRAIGHT_PID_D = 0.06;

    public static final double DRIVEROTATE_PID_P = 0.0037;
    public static final double DRIVEROTATE_PID_I = 0.0;
    public static final double DRIVEROTATE_PID_D = 0.0;

    public static final double WHEEL_DIAMETER_METERS = 0.1524;
    public static final double ENCODER_POSITION_CONVERSION_FACTOR = 0.1 * WHEEL_DIAMETER_METERS * Math.PI;
    public static final double ENCODER_VELOCITY_CONVERSION_FACTOR = ENCODER_POSITION_CONVERSION_FACTOR * 60.0;
    public static final double ENCODER_TICKS_PER_ROTATION = 42; // TO-DO See if this is right

    public final PIDController driveDistancePidController = new PIDController(
            DRIVESTRAIGHT_PID_P,
            DRIVESTRAIGHT_PID_I,
            DRIVESTRAIGHT_PID_D);

    public final PIDController driveRotatePidController = new PIDController(
            DRIVEROTATE_PID_P,
            DRIVEROTATE_PID_I,
            DRIVEROTATE_PID_D);

    private final SparkMax leftLeadMotor = new SparkMax(Constants.IDs.DRIVE_LEFT_LEAD_MOTOR,
            MotorType.kBrushless);
    private final SparkMax rightLeadMotor = new SparkMax(Constants.IDs.DRIVE_RIGHT_LEAD_MOTOR,
            MotorType.kBrushless);
    private final SparkMax leftFollowMotor = new SparkMax(Constants.IDs.DRIVE_LEFT_FOLLOW_MOTOR,
            MotorType.kBrushless);
    private final SparkMax rightFollowMotor = new SparkMax(Constants.IDs.DRIVE_RIGHT_FOLLOW_MOTOR,
            MotorType.kBrushless);

    public DriveSubsystem() {
        SparkMaxConfig leftFollowMotorConfig = new SparkMaxConfig();
        leftFollowMotorConfig
        .follow(leftLeadMotor)
        .idleMode(IdleMode.kCoast).
        inverted(false);
        leftFollowMotor.configure(leftFollowMotorConfig, null, null);

        SparkMaxConfig rightFollowMotorConfig = new SparkMaxConfig();
        rightFollowMotorConfig
        .follow(rightLeadMotor)
        .idleMode(IdleMode.kCoast).
        inverted(false);
        rightFollowMotor.configure(rightFollowMotorConfig, null, null);

        SparkMaxConfig leftLeadMotorConfig = new SparkMaxConfig();
        leftLeadMotorConfig
        .idleMode(IdleMode.kCoast)
        .inverted(true);
        leftLeadMotor.configure(leftLeadMotorConfig, null, null);

        SparkMaxConfig rightLeadMotorConfig = new SparkMaxConfig();
        rightLeadMotorConfig
        .idleMode(IdleMode.kCoast)
        .inverted(true);
        rightLeadMotor.configure(rightLeadMotorConfig, null, null);

    }

    public void arcadeDrive(double forward, double rotate) {
        // If forward is 1 or -1, we want to slow down a bit so that we can still turn
        if (forward + rotate > 1.0) {
            forward = forward - rotate;
        } else if (forward - rotate < -1.0) {
            forward = forward + rotate;
        }

        leftLeadMotor.set(forward + rotate);
        rightLeadMotor.set(forward - rotate);
    }

    public void autoDrive(double forward) {
        rightLeadMotor.set(forward);
        leftLeadMotor.set(forward);
    }

    public double getLeftEncoderPosition() {
        return leftLeadMotor.getEncoder().getPosition() / ENCODER_TICKS_PER_ROTATION
                * ENCODER_POSITION_CONVERSION_FACTOR;
    }

    public double getRightEncoderPosition() {
        return rightLeadMotor.getEncoder().getPosition() / ENCODER_TICKS_PER_ROTATION
                * ENCODER_POSITION_CONVERSION_FACTOR;
    }

    public double getAverageEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }

    public void resetEncoders() {
        leftLeadMotor.getEncoder().setPosition(0);
        rightLeadMotor.getEncoder().setPosition(0);
    }
}