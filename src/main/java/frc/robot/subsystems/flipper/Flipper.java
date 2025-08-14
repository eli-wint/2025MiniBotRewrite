package frc.robot.subsystems.flipper;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flipper extends SubsystemBase{
    private final FlipperIO io;
    private LoggedMechanism2d flipper;
    private LoggedMechanismRoot2d base;
    private LoggedMechanismLigament2d arm;
    private int speed;

    public Flipper(FlipperIO io) {
        this.io = io;
        speed = 0;

        flipper = new LoggedMechanism2d(3, 3);
        base = flipper.getRoot("base", 1.5, 0.2);
        arm = base
                .append(new LoggedMechanismLigament2d("Arm", 1, io.getFlipperPosition(), 3, new Color8Bit()));

        Logger.recordOutput("FlipperMechanism", flipper);
        SmartDashboard.putData("Flipper", flipper);

    }

    @Override
    public void periodic() {
        if ((io.getFlipperPosition() <= FlipperConstants.LOWER_ENDPOINT &&
                speed < 0) || (io.getFlipperPosition() >= FlipperConstants.UPPER_ENDPOINT && speed > 0)) {
            System.out.println("Flipper ENDPOINT reached!!!");
            speed = 0;
        }

        io.setSpeed(speed);

        SmartDashboard.putNumber("Flipper Encoder", io.getFlipperPosition());
        Logger.recordOutput("SimFlipper/Angle", io.getFlipperPosition());
        Logger.recordOutput("SimFlipper/Speed", speed);
    }

}
