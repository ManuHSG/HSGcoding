import java.util.*; 
import java.io.*;

// the main class starts the programm and initalizes a instance of Car_Workshop. The programm simulates a Workkshop for Cars and Motorcycle and is called Car_Workshop. Yyou can bring a Car or a Motorcycle to the workshop and chose between wash,repair and paint. The programm simulates then the amout of time the service takes and gives feedback if the job is done. The Car_workshop has a jobList and is able to store multiple jobs and executes the one after another. There is also an option to exit the programm
class Main{
  
    public static void main(String[] args) {
    Car_Workshop carWorkshop = new Car_Workshop();
    carWorkshop.getJob();
    
  }
}

// the class Car_workshop takes userinput and creates the workshopManager.
class Car_Workshop {
  private LinkedList<Vehicle> jobList;
  private Workshop_Manager workshopManager;
  private int orderNumber;
  private int waitingTime;

  // the constructor starts the thread workshopManager
  public Car_Workshop(){
    jobList = new LinkedList<Vehicle>();
    workshopManager = new Workshop_Manager(this);
    workshopManager.start();
    orderNumber = 100000;
    waitingTime = 0;
  }
  
//The getJob method is used to ask the user for the type of vehicle and the type of service that they want the workshop to perform. It then calls the acceptJob method to add the job to the jobList and pass it on to the workshopManager.
  public void getJob(){
    int action = 0;
    while (action != 3){
        System.out.println();
        System.out.println("Thank you that you choose our Workshop ");
        System.out.println("What type of vehicle are you bringing to the workshop?");
        System.out.println("\t1\tCar");
        System.out.println("\t2\tMotorcylce");
        System.out.println("\t3\tExit Workshop");
        int vehicleType = Toolbox.integerInput(1, 3);
        action = vehicleType;
        if (action == 1) {
            System.out.println("What service can we provide for your Car? ");
        }
        else if (action == 2) {
            System.out.println("What service can we provide for your motorcycle?");
        }
        else if (action == 3){
          System.out.println("Thank you for coming by, have a good day");
          System.exit(0);
      }
        System.out.println("\t1\tWash it");
        System.out.println("\t2\tRepair it");
        System.out.println("\t3\tPaint it");
        int jobType = Toolbox.integerInput(1, 3);
          acceptJob(vehicleType,jobType);
          
         
        
      }
  }
//The acceptJob method calculates the waiting time for the job using the calculateWaitingTime method and then adds a new Car or Motorcycle object to the jobList, depending on the type of vehicle that was specified. It also adds the new vehicle object to the vehiclesToDo list of the workshopManager.
  public void acceptJob(int vehicleType,int jobType){
    calculateWaitingTime();
    if (vehicleType == 1){
      jobList.add(new Car(orderNumber, jobType, waitingTime ));
      
      workshopManager.addToVehiclesToDo(jobList.getLast());
      orderNumber++;
    }
    else if (vehicleType == 2) {
      jobList.add(new Motorcycle(orderNumber, jobType, waitingTime ));
      
      workshopManager.addToVehiclesToDo(jobList.getLast());
      orderNumber++;
    }
    
  }
  //The calculateWaitingTime method iterates through the jobList and calculates the total waiting time for all the jobs by calling the allocateJob method on each Vehicle object in the list and adding the returned value to a running total. It then sets the waitingTime field to the calculated value.
  public void calculateWaitingTime(){
    int time = 0;
    for (Vehicle vehicle1 : jobList){
      time = time + vehicle1.alocateJob(vehicle1);
    }
    this.waitingTime = time;
  }
  public LinkedList<Vehicle> getJobList(){
    return jobList;
  }
}
// the class vehicle is a superclass to the classes Car and Motorcycle. It
class Vehicle{
  int orderNumber;
  int jobType;
  int waitingTime;
  int serviceTime;

  //The constructor takes the parameter into the vehicle class and saves them. Then the method confirmOrder will be executed
    public Vehicle(int orderNumber,int jobType, int waitingTime){
      this.orderNumber = orderNumber;
      this.jobType = jobType;
      this.waitingTime = waitingTime;
      confirmOrder();
    }

  //The method confirmOrder confirms the order via String for the user
    public void confirmOrder(){
      System.out.print("Your order was confirmed and your order number is ");
      System.out.println(orderNumber);
      System.out.println("You can pick up your " + CarOrMotor() + " in " +                      Toolbox.calculatesMinutesToString(waitingTime + this.alocateJob(this)));
    
  }
  //the method CarOrMotor return a String with the type of instance the object is.
  public String CarOrMotor(){
    if (this instanceof Car){
      return "car";
    }
    else if (this instanceof Motorcycle){
      return "motorcycle";
    }
    else{
      return "unknow vehicle";
    }
  }

  //The method alocateJob calculates the serviceTime an for a vehicle/job combo and returns it
  public int alocateJob(Vehicle vehicle){
    if (vehicle instanceof Car){
    if (jobType == 1){
      serviceTime = Car.getWashServiceTime();
    return serviceTime;
    }                     
    else if (jobType == 2){
      serviceTime = Car.getRepairServiceTime();
      return serviceTime;
    }
    else if (jobType == 3){
      serviceTime = Car.getPaintServiceTime();
      return serviceTime;
    }
     else {
       return 0;
     }
    }
      else{
    
      if (jobType == 1){
        serviceTime = Motorcycle.getWashServiceTime(); 
    return serviceTime;
    }                     
    else if (jobType == 2){
      serviceTime = Motorcycle.getRepairServiceTime();
      return serviceTime;
    }
    else if (jobType == 3){
      serviceTime = Motorcycle.getPaintServiceTime();
      return serviceTime;
    }
     else {
       return 0;
     }
    }
  }

  //return jobType
  public int getJobType(){
    return jobType;
  }
  //returns servicetime
  public int getServiceTime(){
    return serviceTime;
  }
  
}

// The class Motorcycle is a subclas to the class vehicle and is used to store the servicetimes of object from the class Motorcycle
class Motorcycle extends Vehicle{
  private final static int WASHSERVICETIME = 3000;
   private final static int REPAIRSERVICETIME = 12000;
  private final static int PAINTSERVICETIME = 6000;
 int jobType;
  
  
  public Motorcycle(int orderNumber,int jobType, int waitingTime){
    super(orderNumber, jobType, waitingTime);
    this.jobType = jobType;
    
  }
  //returns the washTime
  public final static int getWashServiceTime(){
    return WASHSERVICETIME;
  }
  //returns the repairTime
  public final static int getRepairServiceTime(){
    return REPAIRSERVICETIME;
  }
  //returns the paintTime
  public final static int getPaintServiceTime(){
    return PAINTSERVICETIME;
  }
  

  
}

// The class Car is a subclas to the class vehicle and is used to store the servicetimes of object from the class Car
class Car extends Vehicle {
   private final static int WASHSERVICETIME = 4000;
   private final static int REPAIRSERVICETIME = 15000;
  private final static int PAINTSERVICETIME = 9000;
 int jobType;
  
  public Car(int orderNumber,int jobType, int waitingTime){
    super(orderNumber, jobType, waitingTime);
  }
  //returns the washtime
  public final static int getWashServiceTime(){
    return WASHSERVICETIME;
  }
  // returns the repairtime
  public final static int getRepairServiceTime(){
    return REPAIRSERVICETIME;
  }
  //returns the painttime
  public final static int getPaintServiceTime(){
    return PAINTSERVICETIME;
  }
 
}

// THe class Workshop_manager works as a Thread and works the jobs that is given to him by the Car_Workshop
class Workshop_Manager extends Thread{
private LinkedList<Vehicle> vehiclesToDo = new LinkedList<Vehicle>(); 
private LinkedList<Vehicle> vehiclesInService = new LinkedList<Vehicle>();
private LinkedList<Vehicle> vehiclesDone = new LinkedList<Vehicle>();
private Vehicle nextVehicle;
private Car_Workshop carWorkshop;
  
  public Workshop_Manager(Car_Workshop carWorkshop){
  this.carWorkshop =  carWorkshop;
     }

  // the run method keeps the thread alive and polls Jobs form the Linked list. (if the List is empty it pauses the thread for 1000ms ) Then it pauses the Thread fot the serviceTime that is allocated to the vehicle/jobType combo. Afterwards it takes the Job out of the JobList from to Car_workshop to make sure the estimate service time for future service is still reliable. It stores the finisehd jobs in the LinkedList vehiclesDone. 
  public void run(){
    while(true){
     nextVehicle = vehiclesToDo.poll();
      
      if (nextVehicle != null){ 
        vehiclesInService.add(nextVehicle);
        //pauses thread to simulate service time
        Toolbox.pauseThread(nextVehicle.getServiceTime());
        vehiclesInService.removeFirst();
        vehiclesDone.add(nextVehicle);
        carWorkshop.getJobList().removeFirst();
        if (nextVehicle.getJobType() == 1){
          System.out.println("A " + nextVehicle.CarOrMotor() + " was washed.");
        }
        else if (nextVehicle.getJobType() == 2){
          System.out.println("A " + nextVehicle.CarOrMotor() + " was repaired.");
        }
        else if (nextVehicle.getJobType() == 3){
          System.out.println("A " + nextVehicle.CarOrMotor() + " was painted.");
        }
    }
    Toolbox.pauseThread(1000);
  }   
  }

  // the method is used to add vehicles to the LinkedList vehiclesToDo
  public void addToVehiclesToDo(Vehicle vehicle){
    vehiclesToDo.add(vehicle);
}
  
//the Toolbox is used to store Tools that are used often and would take a lot of space otherwise
class Toolbox {
  public Toolbox(){
    
  }
  // Allows reading and checking of integers that the user can enter. If the entered integer is invalid, the user     is notified and prompted to re-enter a number.
  // If the user enters anything other than an integer, an exception is thrown and the user can re-enter a        number.
 
  //@param min smallest vaible number
  //@param max highest viable number
  public static int integerInput(int min, int max)
    {
        try {
            BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));
            int value = Integer.parseInt(infile.readLine());
            while (value < min || value > max) { 
                System.out.println();
                System.out.println("The Input " + value + " is  no viable");
                System.out.println("The only viable input are integers from " + min + " to " + max + ".");
                System.out.println("Please type another integer.");
                value = Integer.parseInt(infile.readLine());
            }
            return value;
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("The input was not viable: " + e);
            System.out.println("The only viable input are Integers from" + min + " to " + max + ".");
            System.out.println("Please type another integer.");
            return integerInput(min, max);
          // User can input value again
        }
    }
    public static void pauseThread(int ms) {
        int waitTime = ms;
        try {
            Thread.sleep(waitTime);
        }
        catch(Exception e){
            System.out.println("Error " + e);
        }
    }
  public static String calculatesMinutesToString(int minutes){
        int timeDays = minutes/60/24;
        int timeHours = minutes/60%24;
        int timeMinutes = minutes%60;
        
        if (timeDays == 1 && timeHours == 1 && timeMinutes == 1) {
            return timeDays + " day, "+ timeHours + " hour and "  + timeMinutes + " minutes";
        }
        else if (timeDays != 1 && timeHours == 1 && timeMinutes == 1) {
            return timeDays + " days, "+ timeHours + " hour and "  + timeMinutes + " minutes";
        }
        else if (timeDays != 1 && timeHours != 1 && timeMinutes == 1) {
            return timeDays + " days, "+ timeHours + " hours and "  + timeMinutes + " minutes";
        }
        else if (timeDays != 1 && timeHours != 1 && timeMinutes != 1) {
            return timeDays + " days, "+ timeHours + " hours and "  + timeMinutes + " minutes";
        }
        else if (timeDays == 1 && timeHours != 1 && timeMinutes == 1) {
            return timeDays + " day, "+ timeHours + " hours and "  + timeMinutes + " minutes";
        }
        else if (timeDays == 1 && timeHours != 1 && timeMinutes != 1) {
            return timeDays + " day, "+ timeHours + " hours and "  + timeMinutes + " minutes";
        }
        else if (timeDays == 1 && timeHours == 1 && timeMinutes != 1) {
            return timeDays + " day, "+ timeHours + " hour and "  + timeMinutes + " minutes";
        }
        else {
            return timeDays + " days, "+ timeHours + " hour and "  + timeMinutes + " minutes";
        }
    }
}
}