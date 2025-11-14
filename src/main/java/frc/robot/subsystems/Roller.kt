package frc.robot.subsystems

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Roller : SubsystemBase() {
    private val motor = SparkMax(5, SparkLowLevel.MotorType.kBrushless)

    init {
        val config =
            SparkMaxConfig().apply {
                voltageCompensation(10.0)
                smartCurrentLimit(60)
            }
        motor.configure(
            config,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters,
        )
    }

    fun runRoller(forward: () -> Double, reverse: () -> Double): Command {
        return this.run { motor.set(forward() - reverse()) }
    }
}
