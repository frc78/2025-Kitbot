package frc.robot.subsystems

import com.revrobotics.spark.SparkBase.PersistMode
import com.revrobotics.spark.SparkBase.ResetMode
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Subsystem

object Drivetrain : Subsystem {
    private val leftLeader = SparkMax(1, SparkLowLevel.MotorType.kBrushless)
    private val leftFollower = SparkMax(2, SparkLowLevel.MotorType.kBrushless)
    private val rightLeader = SparkMax(3, SparkLowLevel.MotorType.kBrushless)
    private val rightFollower = SparkMax(4, SparkLowLevel.MotorType.kBrushless)

    val drive = DifferentialDrive(leftLeader, rightLeader)

    init {
        // Create the configuration to apply to motors. Voltage compensation
        // helps the robot perform more similarly on different
        // battery voltages (at the cost of a little bit of top speed on a fully charged
        // battery). The current limit helps prevent tripping
        // breakers.
        val config =
            SparkMaxConfig().apply {
                voltageCompensation(10.0)
                smartCurrentLimit(60)
            }

        // Set configuration to follow leader and then apply it to corresponding
        // follower. Resetting in case a new controller is swapped
        // in and persisting in case of a controller reset due to breaker trip
        config.follow(leftLeader)
        leftFollower.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters,
        )
        config.follow(rightLeader)
        rightFollower.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters,
        )

        // Remove following, then apply config to right leader
        config.disableFollowerMode()
        rightLeader.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters,
        )

        // Set config to inverted and then apply to left leader. Set Left side inverted
        // so that positive values drive both sides forward
        config.inverted(true)
        leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }

    fun driveArcade(xSpeed: () -> Double, zRot: () -> Double): Command {
        return this.run { drive.arcadeDrive(xSpeed(), zRot()) }
    }
}
