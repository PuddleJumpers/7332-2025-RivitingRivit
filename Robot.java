// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Timer;


/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 2;
  private static final int kRearLeftChannel = 3;
  private static final int kFrontRightChannel = 1;
  private static final int kRearRightChannel = 0;

  XboxController m_controller = new XboxController(0);

  Spark m_intakeLeft = new Spark(0);
  Spark m_intakeRight = new Spark (1);
  Spark m_deepcage = new Spark (2);

  PWMSparkMax m_elevator1 = new PWMSparkMax(3);
  PWMSparkMax m_elevator2 = new PWMSparkMax(4);

  private final MecanumDrive m_robotDrive;

  Timer timer = new Timer();



  /** Called once at the beginning of the robot program. */
  public Robot() {
    WPI_VictorSPX frontLeft = new WPI_VictorSPX(kFrontLeftChannel);
    WPI_VictorSPX rearLeft = new WPI_VictorSPX(kRearLeftChannel);
    WPI_VictorSPX frontRight = new WPI_VictorSPX(kFrontRightChannel);
    WPI_VictorSPX rearRight = new WPI_VictorSPX(kRearRightChannel);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

  }


  @Override
  public void autonomousInit(){
    timer.restart();
    timer.start();
  }


  @Override
  public void autonomousPeriodic(){
    double time = timer.get();
    if (time < 2.0){
      m_robotDrive.driveCartesian(0.0, 0.5, 0.0);
    }
    /*
     * else if (time < 4.0){
     * m_robotDrive.driveCartesian(xSpeed, ySpeed, zRotation); //turn maybe??
     * }
     */
    else {
      m_robotDrive.driveCartesian(0.0, 0.0, 0.0);
    }
  }


  @Override
  public void teleopInit(){
    //what do i even put here ;-;
  }


  @Override
  public void teleopPeriodic() {
    // Use the joystick Y axis for forward movement, X axis for lateral
    // movement, and Z axis for rotation.
    double xSpeed = m_controller.getRightY();
    double ySpeed = m_controller.getRightX();
    double zRotation = m_controller.getLeftX();
    m_robotDrive.driveCartesian(xSpeed * 0.25, -ySpeed * 0.25, zRotation * 0.25);


    boolean intakeButton = m_controller.getYButton();
    boolean elevatorButton1 = m_controller.getLeftBumperButtonPressed(); //get rid of the Pressed part if changing to button being held
    boolean elevatorButton2 = m_controller.getRightBumperButtonPressed();
    boolean revelevatorButton1 = m_controller.getBButtonPressed();
    boolean revelevatorButton2 = m_controller.getAButtonPressed();
    boolean deepcageButton = m_controller.getXButtonPressed();
    
    if (intakeButton) { //might need to change direction
     m_intakeLeft.set(.75);  
     m_intakeRight.set(-1);
      m_intakeLeft.set(0);
      m_intakeRight.set(0);
    }
   
    if (elevatorButton1) {
      timer.reset();
      timer.start();
      if (timer.get() < 0.5){
      m_elevator1.set(.75);
      m_elevator2.set(-.75);
    }
     else if (revelevatorButton1) {
      timer.reset();
      timer.start();
      if (timer.get() < 0.5){
      m_elevator1.set(-.75);
      m_elevator2.set(-.75);
      }
    } 
    else {
      m_elevator1.set(0);
      m_elevator2.set(0);
    }
  }

    if (elevatorButton2){
      timer.reset();
      timer.start();
    } if (timer.get() < 1 ){
      m_elevator1.set(.75); }
      else if (revelevatorButton2) {
        timer.reset();
        timer.start();
        if (timer.get() < 1 ) {
        m_elevator1.set(-.75);
        m_elevator2.set(-.75);
        }
      }
      else {
        m_elevator1.set(0);
        m_elevator2.set(0);
      }

    if (deepcageButton){
      m_deepcage.set(.75);
    } else {
      m_deepcage.set(0);
    }

    }

}

//test commit