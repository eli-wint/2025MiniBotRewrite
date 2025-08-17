package frc.robot.subsystems.flipper;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import static frc.robot.subsystems.flipper.FlipperConstants.*;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;

import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// REWRITTEN

public class FlipperIOSpark implements FlipperIO {

    private final SparkMax flipperMotor;
    private SparkMaxConfig flipperMotorConfig = new SparkMaxConfig();
    private RelativeEncoder encoder;
    private SparkLimitSwitch forwardLimitSwitch;
    private SparkLimitSwitch reverseLimitSwitch;
    private Mechanism2d flipper = new Mechanism2d(1, 3);
    private MechanismRoot2d base = flipper.getRoot("base", 1.5, 1.5);
    private MechanismLigament2d flipperLigament = base.append(new MechanismLigament2d("Flipper Arm", 2, 90));

    public FlipperIOSpark() {
        flipperMotor = new SparkMax(FlipperConstants.IDs.FLIPPER_MOTOR, MotorType.kBrushless);
        flipperMotorConfig
                .inverted(true)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(10);
        flipperMotor.configure(flipperMotorConfig, null, null);

        encoder = flipperMotor.getEncoder();
        forwardLimitSwitch = flipperMotor.getForwardLimitSwitch();
        reverseLimitSwitch = flipperMotor.getReverseLimitSwitch();

        flipperMotorConfig.softLimit
                .forwardSoftLimit(UPPER_ENDPOINT)
                .forwardSoftLimitEnabled(true)
                .reverseSoftLimit(LOWER_ENDPOINT)
                .reverseSoftLimitEnabled(true);

        SmartDashboard.putData("Flipper", flipper);
    }

    @Override
    public void updateInputs(FlipperIOInputs inputs) {
        inputs.position = encoder.getPosition();
        inputs.velocity = encoder.getVelocity();
        inputs.appliedVolts = flipperMotor.getAppliedOutput();
        inputs.currentAmps = flipperMotor.getOutputCurrent();
        inputs.tempCelsius = flipperMotor.getMotorTemperature();
        inputs.isForwardLimit = forwardLimitSwitch.isPressed();
        inputs.isReverseLimit = reverseLimitSwitch.isPressed();
    }

    @Override
    public void setVelocity(double velocity) {
        flipperMotor.set(velocity);
    }

    @Override
    public void setPosition(double position) {
        flipperMotor.getEncoder().setPosition(position);
    }

    public void stop() {
        flipperMotor.set(0);
    }
}