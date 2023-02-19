README ROBOCODE :HAMZAH VAHED

Installation instructions:

To upload this program into the Robocode environment, you can follow these steps:
1.	Open the Robocode application on your computer.
2.	Click the "Robot" menu and select "Import Robot".
3.	In the "Import Robot" dialog box, click "Browse" and navigate to the folder containing the HamzahVahed.java file.
4.	Select the HamzahVahed.java file and click "Open".
5.	The HamzahVahed class should now be displayed in the "Import Robot" dialog box. Click "Compile".
6.	Once the robot is compiled, click "Close".
7.	In the Robocode application, click the "Battle" menu and select "New".
8.	In the "New Battle" dialog box, select "HamzahVahed" from the list of robots and click "Add".
9.	You can add other robots to the battle if you wish.
10.	Set the battle options as desired and click "Start Battle".
11.	The battle will begin, and you can watch the HamzahVahed compete against the other robots.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
ROBOT DESIGN: SAMPLE CODE

import robocode.*;
import robocode.util.Utils;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;
public class HamzahVahed extends AdvancedRobot {
    
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

DESIGN EXPLAINATION 

The run() and onScannedRobot() functions of the existing robot are overridden by this code. The robot continuously scans for other robots with its radar by rotating it to the right, and any pending tasks are subsequently carried out.
We begin by altering the color.
The Robocode API's setAdjustGunForRobotTurn(true) function controls whether your robot's gun should turn independently of its body.
The Robocode API's setAdjustRadarForGunTurn(true) function controls whether your robot's radar should rotate separately from its gun.
The Robocode API function setAdjustRadarForRobotTurn(true) controls whether your robot's radar should turn independently of the robot's body.
The Robocode API's setTurnRadarRight(Double.POSITIVE INFINITY) function instructs the radar to turn constantly to the right at the quickest speed.
This technique is frequently used to survey the battlefield and look for hostile robots. Your robot can scan a broad region of the battlefield and identify hostile robots that are nearby by continually rotating the radar. 
The robot determines the position of the scanned opponent in relation to its present position using the bearing angle and distance in the onScannedRobot(ScannedRobotEvent e) function.
It then determines the adversary's absolute bearing as well as the bearing to the enemy from the robot's gun and radar.
Using the computed bearings, the robot points its gun and radar in the direction of the enemy.
It shoots a bullet with power 3 if the absolute value of the gun's bearing is less than 0.1. 
The robot then advances on the enemy. 
The robot responds to a collision with an enemy robot by rotating its gun to face the enemy and firing a bullet with power 2 in the onHitRobot(HitRobotEvent e) function.
The robot responds to being struck by a bullet by moving forward in the onHitByBullet(HitByBulletEvent e) function. 
The robot responds to striking a wall by 180-degree turning to the right in the onHitWall(HitWallEvent event) function.
Overall, this robot is designed to efficiently detect and attack enemy robots while also avoiding obstacles and responding to collisions and hits.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

STRATEGIES AND TACTICS EMPLOYED

The robot code you provided employs a simple strategy of searching for enemies by constantly rotating the radar and scanning for enemy robots. 
Once an enemy is detected, the robot uses its gun and radar to track the enemy and fires at it when it is in range.
The robot also employs some basic movement tactics to dodge enemy bullets and move toward the detected enemy.
When hit by an enemy bullet, the robot simply moves ahead 100 units, and when it hits a wall, it reverses direction and turns 90 degrees to try and navigate around the obstacle.
The robot's main strategy is to detect and engage enemy robots while trying to avoid taking damage itself.
The tactics employed by the robot are relatively basic, but they are effective enough to enable the robot to survive and potentially win battles against other robots.