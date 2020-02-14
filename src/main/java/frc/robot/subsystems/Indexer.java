package frc.robot.subsystems;

import frc.robot.Constants.INDEXER;
import frc.robot.lib.drivers.Photoeye;
import frc.robot.lib.drivers.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Indexer extends SubsystemBase {

    // Hardware
    private CANSparkMax mMaster;
    //private Photoeye mIndexerPhotoeye = new Photoeye( INDEXER.PHOTOEYE_DIO_CHANNEL );

    // Logging
    private final Logger mLogger = LoggerFactory.getLogger( Indexer.class );

    public Indexer ( CANSparkMax master ) {
        mMaster = master;
    }

    public static Indexer create () {
        CANSparkMax master =  SparkMax.CreateSparkMax( new CANSparkMax( INDEXER.MASTER_ID, MotorType.kBrushless ) );
        return new Indexer( master );
    }

    @Override
    public void periodic () {

    }

}

