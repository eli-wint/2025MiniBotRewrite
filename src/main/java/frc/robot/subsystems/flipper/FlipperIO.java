// Copilot suggestion
package frc.robot.subsystems.flipper;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

// Interface between SIM and REAL

public interface FlipperIO {

    void setSpeed(double newSpeed);

    void stop();
    
    // void updateInputs();

    double getFlipperPosition();
    
}
