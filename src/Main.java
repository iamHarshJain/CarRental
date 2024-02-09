import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carID;
    private String carModel;
    private String carBrand;
    private double basePrice; //which is on per day basis
    private boolean isAvlbl;

    public Car(String carID,String carBrand,String carModel,double basePrice){
        this.carID=carID;
        this.carBrand=carBrand;
        this.carModel=carModel;
        this.basePrice=basePrice;
        this.isAvlbl=true;
    }
    //getter
    public String getCarID(){
        return carID;
    }
    public String getCarModel(){
        return carModel;
    }
    public String getCarBrand(){
        return carBrand;
    }
    public double getBasePrice(){
        return basePrice;
    }
    public double findPrice(int daysForRent){
        return basePrice*daysForRent;
    }
    public boolean isAvlbl() {
        return isAvlbl;
    }
    public void rented(){
        isAvlbl=false;
    }
    public void car_return(){
        isAvlbl=true;
    }
}

class Customer{
    private String customerID;
    private String name;
    public Customer(String customerID,String name){
        this.customerID=customerID;
        this.name=name;
    }

    public String getCustomerID() {
        return customerID;
    }
    public String getName(){
        return name;
    }
}

class Rent{
    private Car car;
    private Customer customer;
    private int number_Days;
    public Rent(Car car,Customer customer,int number_Days){
        this.car=car;
        this.customer=customer;
        this.number_Days=number_Days;
    }
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return number_Days;
    }

}

class CarSystem{
    private List<Car> cars;
    private List<Customer> list_customers;
    private List<Rent> rentals_details;
    public CarSystem(){
        cars = new ArrayList<>();
        list_customers = new ArrayList<>();
        rentals_details = new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        list_customers.add(customer);
    }
    public void rentCar(Car car,Customer customer,int days){
        if(car.isAvlbl()){
            car.rented();
            rentals_details.add(new Rent(car,customer,days));
        }else{
            System.out.println("Car is not available for rent");
        }
    }
    public void returnCar(Car car){
        car.car_return();
        Rent remove_rent = null;
        for(Rent rental : rentals_details){
            if(rental.getCar()==car){
                remove_rent=rental;
                break;
            }
        }
        if(remove_rent!=null){
            rentals_details.remove(remove_rent);
        }else{
            System.out.println("Details Not found ! Car was never rented");
        }
    }

    public void menu(){
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println("Hello there! Welcome to CarRental Services. Please choose from below");
            System.out.println("1. Rent a Car ?");
            System.out.println("2. Return a Car !");
            System.out.println("3. Exit X");
            int choice = sc.nextInt();
            sc.nextLine();
            if(choice==1){
                System.out.println("Rent a Car");
                System.out.println("Enter name : ");
                String customerName=sc.nextLine();
                System.out.println("Hello "+ customerName+" Here are the available cars : ");
                for(Car car:cars){
                    if(car.isAvlbl()){
                        System.out.println(car.getCarID()+ "-" + car.getCarBrand()+"-"+car.getCarModel()+"-"+car.getBasePrice()+"per day (Prices are subjected to change)");
                    }
                }
                System.out.println("Enter the car Id you want to rent");
                String carID=sc.nextLine();
                System.out.println("Enter the number of days for which you want to rent the car");
                int days=sc.nextInt();
                sc.nextLine(); //for new line

                Customer newCustomer = new Customer("CUST"+(list_customers.size()+1),customerName);
                addCustomer(newCustomer);

                Car selected=null;
                for(Car car:cars){
                    if(car.getCarID().equals(carID) && car.isAvlbl()){
                        selected=car;
                        break;
                    }
                }

                if(selected!=null){
                    double totalPrice=selected.findPrice(days);
                    System.out.println("Rental Information");
                    System.out.println("Customer ID: " + newCustomer.getCustomerID());
                    System.out.println("Customer Name: " + newCustomer.getName());

                    System.out.println("Car: " + selected.getCarBrand() + " " + selected.getCarModel());
                    System.out.println("Rental Days: " + days);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selected, newCustomer, days);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                }else{
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            }else if (choice==2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarID().equals(carId) && !car.isAvlbl()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rent rental : rentals_details) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }else if(choice==3){
                break;
            }else{
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("Thank you for using Car Rental");
    }
}

public class Main {
    public static void main(String args[]){
        CarSystem rentalSystem = new CarSystem();

        Car car1 = new Car("MP09RE0022", "Renault", "Duster", 600.0); // Different base price per day for each car
        Car car2 = new Car("RJ01CK5522", "Honda", "City", 750.0);
        Car car3 = new Car("HR02GH0011", "Tata", "Safari", 1500.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}

