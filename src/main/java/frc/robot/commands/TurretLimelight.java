package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Limelight;
import frc.robot.lib.controllers.Vision.State;
import frc.robot.lib.controllers.Vision.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frc.robot.subsystems.Turret;

// turn turret based off target command
public class TurretLimelight extends CommandBase {

    private boolean mIsFinished = true;
    private Status mStatus;
    // private Limelight mLimelight;
    private final Logger mLogger = LoggerFactory.getLogger( TurretLimelight.class );
    private final Turret mTurret;

    /******************************************************************************************************************************
     ** COMMAND OVERRIDES
     ******************************************************************************************************************************/
    @Override
    public void initialize () {
        mLogger.info( "Starting TurretSpin command" );
        mIsFinished = false;
        // setInterruptible( false );
        mTurret.mVision.setState( State.kTurn );
       // mTurret.setState( mZeroing );
    }

    @Override
    public void execute () {

        // Make sure the vision thread is processing the turning output
        if ( mTurret.mVision.getState() == State.kTurn ) {
            mStatus = mTurret.mVision.getStatus();
            // Check the status of the controller
            if ( mStatus == Status.kTargeting ) {
                mTurret.setOpenLoop( mTurret.mVision.getOutput() );
                // mLogger.info( "Target at: [{}]", mLimelight.horizontalToTargetDeg()); 
            } else if ( mStatus == Status.kLostTarget ) {
                // mIsFinished = true;
                mLogger.info( "Lost target" );
            } else if ( mStatus == Status.kReachedTarget ) {
                // mIsFinished = true;
                mLogger.info( "Reached target" );
            } else {
                mTurret.setOpenLoop( 0.0 );
                mLogger.warn( "Unknown status: [{}]", mStatus );
            }
        }

    }

    @Override
    public void end ( boolean interrupted ) {
        mLogger.info( "Finished TurnToTarget command" );
        mTurret.Stop();
    }

    @Override
    public boolean isFinished() {
        return false;
        // return mIsFinished;
    }

    /******************************************************************************************************************************
     ** CONSTRUCTOR
     ******************************************************************************************************************************/
    public TurretLimelight ( Turret turret ) {
        mTurret = turret;
       // mLimelight = limelight;
        addRequirements( mTurret );
        //addRequirements( mLimelight );
    }

}