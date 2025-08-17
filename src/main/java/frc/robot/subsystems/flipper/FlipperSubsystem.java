package frc.robot.subsystems.flipper;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

import static frc.robot.subsystems.flipper.FlipperConstants.LOWER_ENDPOINT;
import static frc.robot.subsystems.flipper.FlipperConstants.TOLERANCE;
import static frc.robot.subsystems.flipper.FlipperConstants.UPPER_ENDPOINT;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlipperSubsystem extends SubsystemBase {
    private final FlipperIO io;
    private final FlipperIOInputsAutoLogged flipperIOInputs = new FlipperIOInputsAutoLogged();
    private LoggedMechanism2d flipper;
    private LoggedMechanismRoot2d base;
    private LoggedMechanismLigament2d arm;

    public FlipperSubsystem(FlipperIO io) {
        this.io = io;

        flipper = new LoggedMechanism2d(3, 3);
        base = flipper.getRoot("base", 1.5, 0.2);
        arm = base
                .append(new LoggedMechanismLigament2d("Arm", 1, flipperIOInputs.position, 3, new Color8Bit()));

        Logger.recordOutput("FlipperMechanism", flipper);
        SmartDashboard.putData("Flipper", flipper);

    }

    @Override
    public void periodic() {

        io.updateInputs(flipperIOInputs);
        Logger.processInputs("Flipper", flipperIOInputs);

        arm.setAngle(-10.5 * flipperIOInputs.position);

        if (RobotState.isDisabled()) {
            stop();
        }
    }

    public boolean isAtUpperEndpoint() {
        return MathUtil.isNear(UPPER_ENDPOINT, flipperIOInputs.position, TOLERANCE);
    }

    public boolean isAtLowerEndpoint() {
        return MathUtil.isNear(LOWER_ENDPOINT, flipperIOInputs.position, TOLERANCE);
    }

    public void setVelocity(double velocity) {
        io.setVelocity(velocity);
    }

    public void stop() {
        io.setVelocity(0);
    }

}
