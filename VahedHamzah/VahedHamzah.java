package VahedHamzah;
import robocode.*;
import robocode.util.Utils;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;


// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * VahedHamzah - a robot by (your name here)
 */
public class VahedHamzah extends AdvancedRobot
{
	/**
	 * run: VahedHamzah's default behavior
	 */
    
    public void run() {
		//Change robot colors
		setBodyColor(Color.black); 
		setGunColor(Color.blue);
		setBulletColor(Color.red);
		setScanColor(Color.blue);
		//Allows gun to move freely from the robots body
        setAdjustGunForRobotTurn(true);
		//Allows radar to move freely from the gun turning
        setAdjustRadarForGunTurn(true);
		//Allows radar move freely from robots body  
        setAdjustRadarForRobotTurn(true);
		//Lets radar turn in order to scan the battlefield and search for enemies
        while (true) {
            setTurnRadarRight(Double.POSITIVE_INFINITY);
            execute();
        }
    }
	/**
	 * onScannedRobot : What to do when an enemy robot  has been detected
	 */
    public void onScannedRobot(ScannedRobotEvent e) {
		//Get and return position of enemy relative current position 
        double bearing = e.getBearingRadians();
		double heading = getHeadingRadians();
        double gunHeading = getGunHeadingRadians();
        double radarHeading = getRadarHeadingRadians();
        double distance = e.getDistance();
       
        double absoluteBearing = heading + bearing;
		//Calculates bearing from robot gun to snanned robot
        double bearingFromGun = Utils.normalRelativeAngle(absoluteBearing - gunHeading);
       //Calculates bearing from robot radar to scanned robot 
 		double bearingFromRadar = Utils.normalRelativeAngle(absoluteBearing - radarHeading);
        //Turn gun to specific angle 
        setTurnGunRightRadians(bearingFromGun);
		//Turn radar to specifc angle
        setTurnRadarRightRadians(bearingFromRadar * 1.5);
        //Checks to see if absolute value of  the bearing from the gun is less than 0.1 
        if (Math.abs(bearingFromGun) < 0.1) {
		//if true then fire with power of 3
            fire(3);
        }
         //Used to move your robot towards the current target
        setAhead(100 * (distance - 100) / distance);
    }
		/**
	 * onHitByBullet: What to do when the robot collides qwith enemy robot
	 */
		public void onHitRobot(HitRobotEvent e) {
		//Calculate angle that the gun needs to turn in order to face enemy
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		//Turns the robot's gun right 		
		turnGunRight(turnGunAmt);
		fire(2);
  }
 	/**
	 * onHitByBullet: What to do when robot hits by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Change direction if we are hit 
		ahead(100);
	}
		/**
	 * onHitWall: What to do when the robot hits a wall?
	 */
    public void onHitWall(HitWallEvent event) {
        // Reverse direction if we hit a wall
        setAhead(-100);
        setTurnRight(90);
    }	
}
