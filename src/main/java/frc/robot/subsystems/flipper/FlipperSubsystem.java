package frc.robot.subsystems.flipper;

import com.revrobotics.spark.SparkMax;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// REWRITTEN

public class FlipperSubsystem extends SubsystemBase implements Flipper{
    private final SparkMax flipperMotor;
    private double speed;
    private SparkMaxConfig flipperMotorConfig = new SparkMaxConfig();
    private Mechanism2d flipper = new Mechanism2d(1, 3);
    private MechanismRoot2d base = flipper.getRoot("base", 1.5, 1.5);
    private MechanismLigament2d flipperLigament = base.append(new MechanismLigament2d("Flipper Arm", 2, 90));
    

    public FlipperSubsystem() {
        flipperMotor = new SparkMax(FlipperConstants.IDs.FLIPPER_MOTOR, MotorType.kBrushless);
        flipperMotorConfig
                .inverted(true)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(10);
        flipperMotor.configure(flipperMotorConfig, null, null);

        SmartDashboard.putData("Flipper", flipper);
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

        flipperLigament.setAngle(getFlipperPosition());

        // Checks if the "getFlipperPosition()" value has reached either the low
        // endpoint, or upper endpoint and stops the motor
        if ((getFlipperPosition() <= FlipperConstants.LOWER_ENDPOINT &&
                speed < 0) || (getFlipperPosition() >= FlipperConstants.UPPER_ENDPOINT && speed > 0)) {
            System.out.println("Flipper ENDPOINT reached!!!");
            speed = 0;
        }

        SmartDashboard.putNumber("Flipper Encoder", getFlipperPosition());

        flipperMotor.set(speed);
    }

}