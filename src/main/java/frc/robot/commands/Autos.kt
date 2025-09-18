package frc.robot.commands

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.Drivetrain

fun exampleAuto(): Command {
  return Drivetrain.driveArcade({ 0.5 }, { 0.0 }).withTimeout(1.0)
}
