package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// REWRITTEN

public class FlipperSubsystem extends SubsystemBase {
    private final SparkMax flipperMotor;
    // Init a new motor for flipper
    // finish the methods?
    private double speed;

    public FlipperSubsystem() {
            flipperMotor = new SparkMax(Constants.IDs.FLIPPER_MOTOR, MotorType.kBrushless);
            SparkMaxConfig flipperMotorConfig = new SparkMaxConfig();
            flipperMotorConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(10);
            flipperMotor.configure(flipperMotorConfig, null, null);
        }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void resetPivotEncoder() {
        flipperMotor.getEncoder().setPosition(speed);
    }

    public void stop() {
        flipperMotor.set(0);
    }

    public double getFlipperPosition() { // double
        return flipperMotor.getEncoder().getPosition();
    }

    @Override
    public void periodic() {
        // Checks if the "getFlipperPosition()" value has reached either the low endpoint, or upper endpoint and stops the motor
        if ((getFlipperPosition() <= Constants.LOWER_ENDPOINT &&
                speed < 0) || (getFlipperPosition() >= Constants.UPPER_ENDPOINT && speed > 0)) {
            System.out.println("Flipper ENDPOINT reached!!!");
            speed = 0;
        }

        SmartDashboard.putNumber("Flipper Encoder", getFlipperPosition());

        flipperMotor.set(speed);
    }
    
}