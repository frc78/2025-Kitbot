package frc.robot

import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.util.WPILibVersion
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.commands.exampleAuto
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.Roller

object Robot : TimedRobot() {

  private var autonomousCommand: Command? = null

  // The driver's controller
  private val driveController = CommandXboxController(0)

  // The operator's controller
  private val operatorController = CommandXboxController(1)

  // The autonomous chooser
  private val autoChooser = SendableChooser<Command>()

  init {
    // Access subsystems to initialize them
    Roller
    Drivetrain

    HAL.report(
        tResourceType.kResourceType_Language,
        tInstances.kLanguage_Kotlin,
        0,
        WPILibVersion.Version,
    )

    // Set the options to show up in the Dashboard for selecting auto modes. If you
    // add additional auto modes you can add additional lines here with
    // autoChooser.addOption
    autoChooser.setDefaultOption("Autonomous", exampleAuto())

    configureBindings()
  }

  private fun configureBindings() {
    // Set the A button to run the "runRoller" command from the factory with a fixed
    // value ejecting the gamepiece while the button is held
    operatorController.a().whileTrue(Roller.runRoller({ 0.44 }, { 0.0 }))

    // Set the default command for the drive subsystem to the command provided by
    // factory with the values provided by the joystick axes on the driver
    // controller. The Y axis of the controller is inverted so that pushing the
    // stick away from you (a negative value) drives the robot forwards (a positive
    // value)
    Drivetrain.defaultCommand =
        Drivetrain.driveArcade({ -driveController.leftY }, { -driveController.rightX })

    // Set the default command for the roller subsystem to the command from the
    // factory with the values provided by the triggers on the operator controller
    Roller.defaultCommand =
        Roller.runRoller(
            { operatorController.rightTriggerAxis },
            { operatorController.leftTriggerAxis },
        )
  }

  override fun robotPeriodic() {
    CommandScheduler.getInstance().run()
  }

  override fun disabledInit() {}

  override fun disabledPeriodic() {}

  override fun autonomousInit() {
    autonomousCommand = autoChooser.selected

    // schedule the autonomous command (example)
    autonomousCommand?.schedule()
  }

  override fun autonomousPeriodic() {}

  override fun teleopInit() {
    autonomousCommand?.cancel()
  }

  /** This method is called periodically during operator control. */
  override fun teleopPeriodic() {}

  override fun simulationInit() {}

  override fun simulationPeriodic() {}
}
