// Copilot code
package frc.robot.subsystems.flipper;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;
import frc.robot.subsystems.flipper.Flipper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlipperSim extends SubsystemBase implements Flipper {
    private double simulatedAngle = 90.0; // Initial position
    private double speed = 0.0;

    private static final double LOWER_ENDPOINT = 45.0;
    private static final double UPPER_ENDPOINT = 135.0;
    private static final double STEP_PER_CYCLE = 0.5; // degrees per cycle

    private final LoggedMechanism2d flipper = new LoggedMechanism2d(3, 3);
    private final LoggedMechanismRoot2d base = flipper.getRoot("base", 1.5, 0);
    private final LoggedMechanismLigament2d arm = base.append(new LoggedMechanismLigament2d("Arm", 1, simulatedAngle, 6, new Color8Bit()));

    public FlipperSim() {
        Logger.recordOutput("FlipperMechanism", flipper);
        SmartDashboard.putData("Flipper", flipper);
    }
    @Override
    public void setSpeed(double newSpeed) {
        this.speed = newSpeed * 3;
    }

    public double getAngle() {
        return simulatedAngle;
    }

    public void stop() {
        speed = 0.0;
    }

    @Override
    public void periodic() {
        // Simulate movement based on speed
        simulatedAngle += speed * STEP_PER_CYCLE;

        // Clamp to endpoints
        if ((simulatedAngle <= LOWER_ENDPOINT && speed < 0) ||
            (simulatedAngle >= UPPER_ENDPOINT && speed > 0)) {
            System.out.println("Sim Flipper endpoint reached");
            simulatedAngle = Math.max(LOWER_ENDPOINT, Math.min(simulatedAngle, UPPER_ENDPOINT));
            stop();
        }

        arm.setAngle(simulatedAngle);
        Logger.recordOutput("SimFlipper/Angle", simulatedAngle);
        Logger.recordOutput("SimFlipper/Speed", speed);
    }

}

