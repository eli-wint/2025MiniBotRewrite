package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.FlipperCommand;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.drive.DriveIO;
import frc.robot.subsystems.drive.DriveIOSim;
import frc.robot.subsystems.drive.DriveIOSpark;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.flipper.FlipperIO;
import frc.robot.subsystems.flipper.FlipperConstants;
import frc.robot.subsystems.flipper.FlipperIOSim;
import frc.robot.subsystems.flipper.FlipperIOSpark;
import frc.robot.subsystems.flipper.FlipperSubsystem;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // Subsystems
  public static FlipperSubsystem flipperSubsystem;
  private final DriveSubsystem drive;

  // Controller
  private final XboxController controller = new XboxController(0);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    switch (DriveConstants.currentMode) {
      case REAL:
        flipperSubsystem = new FlipperSubsystem(new FlipperIOSpark()); // hardware
        drive = new DriveSubsystem(new DriveIOSpark(), new GyroIONavX());
        break;

      case SIM:
        flipperSubsystem = new FlipperSubsystem(new FlipperIOSim()); // simulation
        drive = new DriveSubsystem(new DriveIOSim(), new GyroIO() {
        });
        break;

      default:
        flipperSubsystem = new FlipperSubsystem(new FlipperIO() {
        }); // fallback
        drive = new DriveSubsystem(new DriveIO() {
        }, new GyroIO() {
        });
        break;
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default drive command, normal arcade drive
    drive.setDefaultCommand(
        DriveCommands.arcadeDrive(
            drive, () -> -controller.getLeftY(), () -> -controller.getRightX()));

    if (flipperSubsystem != null) {
      flipperSubsystem.setDefaultCommand(new FlipperCommand(flipperSubsystem, controller::getLeftBumperButton,
          controller::getRightBumperButton));
    }

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}