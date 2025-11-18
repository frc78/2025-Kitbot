package frc.robot

import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.util.WPILibVersion
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.Roller

object Robot : TimedRobot() {

    // The driver's controller
    private val driveController = CommandXboxController(0)

    // The operator's controller
    private val operatorController = CommandXboxController(1)

    init {

        HAL.report(
            tResourceType.kResourceType_Language,
            tInstances.kLanguage_Kotlin,
            0,
            WPILibVersion.Version,
        )

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

        // Example auto to drive forward for 0.5 seconds when auto starts
        RobotModeTriggers.autonomous()
            .onTrue(
                Drivetrain.driveArcade({ 0.5 }, { 0.0 })
                    .withTimeout(3.0)
                    .andThen(Roller.runRoller({ 0.5 }, { 0.0 }).withTimeout(2.0))
            )
    }

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }
}
