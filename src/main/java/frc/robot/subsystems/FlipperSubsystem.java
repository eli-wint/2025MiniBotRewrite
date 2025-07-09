package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FlipperSubsystem  extends SubsystemBase {
    private final SparkMax flipperMotor;
    double speed = 0;

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
        flipperMotor.getEncoder().setPosition(0);
    }

    public void stop() {
        flipperMotor.set(0);

    }

    public double getFlipperPosition() {
        return flipperMotor.getEncoder().getPosition();
    }

    @Override
    public void periodic() {

        if ((getFlipperPosition() <= Constants.LOWER_ENDPOINT &&
                speed < 0) || (getFlipperPosition() >= Constants.UPPER_ENDPOINT && speed > 0)) {
            System.out.println("Flipper ENDPOINT reached!!!");
            speed = 0;
        }

        SmartDashboard.putNumber("Flipper Encoder", getFlipperPosition());

        flipperMotor.set(speed);
    }
}