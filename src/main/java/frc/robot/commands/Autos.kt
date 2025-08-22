package frc.robot.commands

import frc.robot.lib.command
import frc.robot.subsystems.Drivetrain

object Autos {
    val exampleAuto by command {
        // Example autonomous command which drives forward for 1 second.
        Drivetrain.driveArcade({ 0.5 }, { 0.0 }).withTimeout(1.0)
    }
}
