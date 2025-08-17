// Copilot suggestion
package frc.robot.subsystems.flipper;

import org.littletonrobotics.junction.AutoLog;

// Interface between SIM and REAL

public interface FlipperIO {

    @AutoLog
    public static class FlipperIOInputs {
        public double position = 0.0;
        public double velocity = 0.0;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
        public double tempCelsius = 0.0;
        public boolean isForwardLimit = false;
        public boolean isReverseLimit = false;
    }

    default void updateInputs(FlipperIOInputs inputs) {
    }

    default void setVelocity(double velocity) {
    }

    default void setPosition(double position) {
    }
}
