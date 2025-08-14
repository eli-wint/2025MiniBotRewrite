// Copilot code
package frc.robot.subsystems.flipper;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;
import frc.robot.subsystems.flipper.FlipperIO;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlipperIOSim implements FlipperIO {
    private double simulatedAngle = 0.0; // Initial position
    private double speed = 0.0;

    private static final double LOWER_ENDPOINT = 0.0;
    private static final double UPPER_ENDPOINT = 100.0;
    private static final double STEP_PER_CYCLE = 0.5; // degrees per cycle

    @Override
    public void setSpeed(double newSpeed) {
        this.speed = newSpeed * 3;
    }

    @Override
    public double getFlipperPosition() {
        return simulatedAngle;
    }

    public void stop() {
        speed = 0.0;
    }

    // @Override
    // public void updateInputs() {
    //     // Simulate movement based on speed
    //     simulatedAngle += speed * STEP_PER_CYCLE;

    //     // Clamp to endpoints
    //     if ((simulatedAngle <= LOWER_ENDPOINT && speed < 0) ||
    //         (simulatedAngle >= UPPER_ENDPOINT && speed > 0)) {
    //         System.out.println("Sim Flipper endpoint reached");
    //         simulatedAngle = (Math.max(LOWER_ENDPOINT, Math.min(simulatedAngle, UPPER_ENDPOINT)));
    //         stop();
    //     }

    //     arm.setSpeed(simulatedAngle);
    //     Logger.recordOutput("SimFlipper/Angle", simulatedAngle);
    //     Logger.recordOutput("SimFlipper/Speed", speed);
    // }

}

