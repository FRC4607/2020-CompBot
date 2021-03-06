package frc.robot.subsystems;

import frc.robot.Constants.CLIMBER;
import frc.robot.Constants.CURRENT_LIMIT;
import frc.robot.Constants.GLOBAL;
import frc.robot.lib.drivers.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends SubsystemBase {

    // Hardware
    private final CANSparkMax mMaster; 
    // private final CANSparkMax mFollower; 
    private final DoubleSolenoid mShifter; 

    private boolean mIsLocked; 
    // private boolean mIsInverted; 

    // shift climber between locked and unlocked 
    // shift pnematics to put the climber up 
    public void SetLocked ( boolean wantsLocked ) {
        if ( wantsLocked && !mIsLocked ) {
            mIsLocked = wantsLocked;
            mShifter.set( DoubleSolenoid.Value.kForward );
        } else if ( !wantsLocked && mIsLocked ) {
            mIsLocked = wantsLocked; 
            mShifter.set( DoubleSolenoid.Value.kReverse );
        }
    }

    public boolean IsLocked () { 
        return mIsLocked; 
    } 

    public void OpenLoop () {
        mMaster.set( CLIMBER.SPEED );
        mMaster.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
        // mFollower.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
    }

    // stop for deadband
    public void Stop () {
        mMaster.set( 0.0 );
        mMaster.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
        // mFollower.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
    }

    // public void SetInverted () {
    //         mMaster.setInverted( false );
    //         mFollower.setInverted( true ); 
    //         mMaster.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
    //         mFollower.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
        
    // }


    private void Initialize () {
        
        // follower is opposite master 
        mMaster.setInverted( false );
        // mFollower.setInverted( false ); 

        // mFollower.follow( mMaster );
    }

    public Climber ( CANSparkMax master, /*CANSparkMax follower,*/ DoubleSolenoid shifter /* CANPIDController PidController*/ ) {

        mMaster = master;
        // mFollower = follower;
        mShifter = shifter;

        Initialize();
    
        // current limiting for sparks by zero rpm, max rpm, and inbetween rpm current limits
        mMaster.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
        // mFollower.setSmartCurrentLimit( CURRENT_LIMIT.SPARK_ZERO_RPM_LIMIT, CURRENT_LIMIT.SPARK_FREE_RPM_LIMIT, CURRENT_LIMIT.SPARK_RPM_LIMIT );
    }

    public static Climber create () {
        CANSparkMax master =  SparkMax.CreateSparkMax( new CANSparkMax( CLIMBER.MASTER_ID, MotorType.kBrushless ) );
        // CANSparkMax follower =  SparkMax.CreateSparkMax( new CANSparkMax( CLIMBER.FOLLOWER_ID, MotorType.kBrushless ) );
        DoubleSolenoid shifter = new DoubleSolenoid( GLOBAL.PCM_ID, CLIMBER.LOCKED_SOLENOID_ID, CLIMBER.UNLOCKED_SOLENOID_ID );
        return new Climber( master, /*follower,*/ shifter /*PidController*/ ); 
    } 

    @Override
    public void periodic () {
    }

}

