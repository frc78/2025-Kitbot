package frc.robot

import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.util.WPILibVersion
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler

object Robot : TimedRobot() {

    private var autonomousCommand: Command? = null

    init {
        HAL.report(
            tResourceType.kResourceType_Language,
            tInstances.kLanguage_Kotlin,
            0,
            WPILibVersion.Version,
        )
    }

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }

    override fun disabledInit() {}

    override fun disabledPeriodic() {}

    override fun autonomousInit() {
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
