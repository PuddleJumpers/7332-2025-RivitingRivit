// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Timer;


/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 1;
  private static final int kRearLeftChannel = 0;
  private static final int kFrontRightChannel = 2;
  private static final int kRearRightChannel = 3;

  XboxController m_controller = new XboxController(0);

  Spark m_intakeLeft = new Spark(0);
  Spark m_intakeRight = new Spark(1);
  Spark m_deepcage = new Spark(5);

  PWMSparkMax m_elevator1 = new PWMSparkMax(3);
  PWMSparkMax m_elevator2 = new PWMSparkMax(4);

  WPI_VictorSPX a = new WPI_VictorSPX(kRearRightChannel); //rear right, 3
  WPI_VictorSPX b = new WPI_VictorSPX(kFrontRightChannel); //front right, 2
  WPI_VictorSPX c = new WPI_VictorSPX(kFrontLeftChannel); // front left, 1
  WPI_VictorSPX d = new WPI_VictorSPX(kRearLeftChannel); //rear left, 0

  private final MecanumDrive m_robotDrive = new MecanumDrive(a, b, c, d);

  Timer timer = new Timer();

  /** Called once at the beginning of the robot program. */
  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);
   // d.setInverted(true);
  }


  @Override
  public void autonomousInit(){
    a.setInverted(true);
    c.setInverted(true);
    d.setInverted(false);
    b.setInverted(false);
  /*   frontLeft.setInverted(true);
    rearLeft.setInverted(true);
    frontRight.setInverted(false); */
    timer.restart();
    timer.start();


  }


  @Override
  public void autonomousPeriodic(){
    double time = timer.get();
       if (time < 2.0){
      m_robotDrive.driveCartesian(0.0, -0.4, 0.0);
    }  
     else if (time > 2.0 && time < 3){
      m_robotDrive.driveCartesian(0, 0, 0);
      m_elevator1.set(-.5);
      m_elevator2.set(-.5); 
    } 
        else if (time > 3 && time < 5){
        m_elevator1.set(0);
        m_elevator2.set(0);
        m_intakeLeft.set(0.6);
        m_intakeRight.set(0.25);
      } 
    else {
      m_robotDrive.driveCartesian(0.0, 0.0, 0.0);
      m_elevator1.set(0);
      m_elevator2.set(0);
      m_intakeLeft.set(0);
      m_intakeRight.set(0);
    } 
  }


  @Override
  public void teleopInit(){
    //what do i even put here ;-; 
    b.setInverted(false);
    d.setInverted(true);
    a.setInverted(false);
    c.setInverted(true);

  }


  @Override
  public void teleopPeriodic() {
    // Use the joystick Y axis for forward movement, X axis for lateral
    // movement, and Z axis for rotation.
    double xSpeed = m_controller.getRightY();
    double ySpeed = m_controller.getRightX();
    double zRotation = m_controller.getLeftX();

    boolean revIntake = m_controller.getXButton();
    boolean intakeButton = m_controller.getYButton(); 
    boolean elevatorUp = m_controller.getAButton();
    boolean elevatorDown = m_controller.getBButton();

   // boolean troughButton = m_controller.getRightBumperButton();

    double deepCageUp = m_controller.getLeftTriggerAxis();
    double deepCageDown = m_controller.getRightTriggerAxis();

  //  int deepcageButton = m_controller.getPOV(); //manual elevator up
  //  int revdeepcageButton = m_controller.getPOV(); //manual elevator down

    //boolean elevatorUp = m_controller.povUp(null).getAsBoolean();
    //boolean elevatorDown = m_controller.povDown(null).getAsBoolean();

    m_robotDrive.driveCartesian(xSpeed * 0.6, -ySpeed * 0.6, zRotation * 0.6);

    /*if (revIntake == 1){
      m_intakeLeft.set(-0.5);
      m_intakeRight.set(0.5);
    } else  {
      m_intakeLeft.set(0);
      m_intakeRight.set(0);
    } */

   /*  if (manElevatorDown == 180){
      m_elevator1.set(0.5);
      m_elevator2.set(0.5);
    } else {
      m_elevator1.set(0);
      m_elevator2.set(0);
    }
    if (manElevatorUp == 0){
      m_elevator1.set(-0.5);
      m_elevator2.set(-0.5);
    } else{
     m_elevator1.set(0);
     m_elevator2.set(0);
     }   */
    
    
    if (intakeButton) { //might need to change direction
      m_intakeLeft.set(0.4);  
      m_intakeRight.set(0.4);
    }
    else if (revIntake) { //might need to change direction
      m_intakeLeft.set(0.6);  
      m_intakeRight.set(0.25);
    }
    else {
      m_intakeLeft.set(0);
      m_intakeRight.set(0);
    }
   
     if (elevatorUp){ 
      m_elevator1.set(-0.5);
      m_elevator2.set(-0.5);
    } else if (elevatorDown){
      m_elevator1.set(0.5);
      m_elevator2.set(0.5);
    } else {
      m_elevator1.set(0);
      m_elevator2.set(0);
    } 

     /*   if (troughButton = true){
     timer.reset();
     timer.start();
      if (timer.get() < 3.0 && timer.get() > 1.0){
      m_elevator1.set(-0.5);
      m_elevator2.set(-0.5);
  }
      else if (timer.get() > 2.0 && timer.get() < 4.0){
      m_elevator1.set(0);
      m_elevator2.set(0);
      m_intakeRight.set(.4);
      m_intakeLeft.set(.4);
      } else {
       m_elevator1.set(0);
       m_elevator2.set(0);
       m_intakeRight.set(0);
       m_intakeLeft.set(0);
       }
  } */
     
 //DEEPCAGE NEGATIVE NUMBERS = UP
      if (deepCageDown == 1){
      m_deepcage.set(0.25);
    } else if (deepCageUp == 1){
      m_deepcage.set(-0.25);
    } else {
      m_deepcage.set(0);
    }  
  }
  /*  if (elevatorButton1) {
      timer.reset();
      timer.start();
      if (timer.get() < 3.0) {
      m_elevator1.set(-0.75);
      m_elevator2.set(-0.75);
      }
    }  else if (elevatorButton2){
      timer.reset();
      timer.start();
      if (timer.get() < 3.5 ){
        m_elevator1.set(-0.75); 
        m_elevator2.set(-0.75);
      }
    }  else if (troughButton){
      timer.reset();
      timer.start();
      if(timer.get() < 2.0){
        m_elevator1.set(-0.75);
        m_elevator2.set(-0.75); 
      
     else {
        m_elevator1.set(0);
        m_elevator2.set(0); }*/
    
    
  
    } 
  