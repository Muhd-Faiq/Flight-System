import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

abstract class Passenger implements DiscConsiderable
{
	protected String name;
	protected int age;

	public Passenger(){};
	public Passenger(String name,int age)
	{
		this.name=name;
		this.age=age;
	}
	abstract void displayDetails();
}

class Flight
{
	private double price;
	private String id;
	private String from;
	private String to;
	private int numAdults;
	private int numKids;
	private Passenger[] passengerList;

	public Flight(){};
	public  Flight(String id, String to, String from, double price)
	{
		this.id=id;
		this.price=price;
		this.from=from;
		this.to=to;
		numAdults=0;
		numKids=0;
		passengerList=new Passenger[10];
	}
	public String  getFlightId()
	{
		return id;
	}
	public void addPassenger(Passenger pass)
	{
		if(pass instanceof Adults)
		{
			passengerList[numAdults]=pass;
			numAdults++;
		}
		else if(pass instanceof Kids)
		{
			passengerList[numKids]=pass;
			numKids++;
		}
	}
	public void displayInfoFlight()
	{
		System.out.println("Flight Id: "+id);
		System.out.println("From     : "+from);
		System.out.println("To       : "+to);
	}
	public void displayInfoPassengers()
	{
		int num=numAdults+numKids;
		double[] pr=new double[num];
		int p=0;
		if(passengerList.length<=0)
		{
			System.out.println("No passenger");
		}
		else
		{
			String format="%-4s %-20s %-6s %-20s %-15s\n";
			System.out.println("Number of Passengers : "+passengerList.length);
			System.out.println("Number of Adults: "+numAdults);
			System.out.println("Number of Kids: "+numKids);
			System.out.printf(format,"No","Name","Age","Parent Name","Ticket (RM)");
			for(int i=0;i<passengerList.length;i++)
			{
				System.out.printf("%-4s",i);
				passengerList[i].displayDetails();
				pr[i]=price-(passengerList[i].calcDisc()*price);
				System.out.printf("%-15s",pr[i]);
				System.out.println();
				p+=pr[i];
			}
			System.out.println("Total ticket price = RM"+p);
		}
	}
}

class Adults extends Passenger
{
	public Adults(){};
	public Adults(String name,int age)
	{
		this.name=name;
		this.age=age;
	}
	public double calcDisc()
	{
		double disc;
		if(age>=60)
		{
			disc=DISC_SENIOR/100;
		}
		else
		{
			disc=0;
		}
		return disc;
	}
	public void displayDetails()
	{
		String format1="%-20s %-6s %-20s";
		System.out.printf(format1,name,age,"-");
	}
}

class Kids extends Passenger
{
	private String parentName;
	public Kids(){};
	public Kids(String name,int age, String parentName)
	{
		this.name=name;
		this.age=age;
		this.parentName=parentName;
	}
	public double calcDisc()
	{
		double disc;
		if(age<=2)
		{
			disc=DISC_KIDS/100;
		}
		else
		{
			disc=0;
		}
		return disc;
	}
	public void displayDetails()
	{
			String format2="%-20s %-6s %-20s";
			System.out.printf(format2,name,age,parentName);
	}
}

interface DiscConsiderable
{
	double DISC_KIDS= 100;
	double DISC_SENIOR =10;
	double calcDisc();
}

public class FlightTest
{
	public static void menu()
	{
		System.out.println("========== Menu ===========");
		System.out.println("[1] Add Flight\n[2] Add Passenger\n[3] Display Flights\n[4] Display Passengers\n[5] Exit");
		System.out.println("===========================\n");
	}
	public static void main(String[] args)
	{
		boolean loop=true;
		ArrayList<Flight> flight=new ArrayList<Flight>();

		while(loop)
		{
		try
		{
		menu();
		System.out.print("Select task:  ");
		Scanner task1=new Scanner(System.in);
		int task=task1.nextInt();
		if(task==1)
		{
			System.out.println("<<< Add Flight >>>>");
			System.out.print("Enter Flight Id    : ");
			Scanner flightid1=new Scanner(System.in);
			String flightid=flightid1.nextLine();
			System.out.print("Enter Flight From  : ");
			Scanner flightfrom1=new Scanner(System.in);
			String flightfrom=flightfrom1.nextLine();
			System.out.print("Enter Flight To    : ");
			Scanner flightto1=new Scanner(System.in);
			String flightto=flightto1.nextLine();
			System.out.print("Enter Ticket Price : RM");
			Scanner flightprice1=new Scanner(System.in);
			int flightprice=flightprice1.nextInt();
			System.out.println();
			Flight flightobj=new Flight(flightid,flightto,flightfrom,flightprice);
			flight.add(flightobj);
			loop=true;
		}
		else if(task==2)
		{
			if(flight.size()<=0)
			{
				System.out.println("Sorry!! No flight, please add flight first...");
			}
			else
			{
				Passenger passenger;
				System.out.println("<<< Add Passenger(s) >>>>\n");
				System.out.println("Flight list");
				for(int i=0;i<flight.size();i++)
				{
					System.out.print((i+1)+") ");
					System.out.println(((Flight)flight.get(i)).getFlightId());
				}
				System.out.print("Select flights:  ");
				Scanner selectflight1=new Scanner(System.in);
				int selectflight=selectflight1.nextInt();
				System.out.println();
				boolean loop2=true;
				while(loop2)
				{
					System.out.println("---Enter Passenger Info ---");
					System.out.print("Enter Name: ");
					Scanner namepass1=new Scanner(System.in);
					String namepass=namepass1.nextLine();
					System.out.print("Enter Age: ");
					Scanner agepass1=new Scanner(System.in);
					int agepass=agepass1.nextInt();
					if(agepass<=12)
					{
						System.out.print("Enter Parent Name: ");
						Scanner nameparent1=new Scanner(System.in);
						String nameparent=nameparent1.nextLine();
						passenger=new Kids(namepass,agepass,nameparent);
					}
					else
					{
						passenger=new Adults(namepass,agepass);
					}
					System.out.print("Press 'Y' to continue >> ");
					Scanner pressy1=new Scanner(System.in);
					String pressy=pressy1.nextLine();
					((Flight)flight.get(selectflight-1)).addPassenger(passenger);
					if(pressy.equals("Y") || pressy.equals("y"))
					{
						loop2=true;
					}
					if(pressy.equals("N") || pressy.equals("n"))
					{
						loop2=false;
						break;
					}
					else
					{
						loop2=false;
					}
				}
			}
			loop=true;

		}
		else if(task==3)
		{
			if(flight.size()<=0)
			{
				System.out.println("Sorry!! No flight data to display....");
			}
			else
			{
				System.out.println("<<< Flight Info >>>");
				System.out.println("Number of Flights: "+flight.size());
				for(int i=0;i<flight.size();i++)
				{
					System.out.println("Flight #"+(i+1));
					((Flight)flight.get(i)).displayInfoFlight();
				}
			}
			loop=true;
		}
		else if(task==4)
		{

			if(flight.size()<=0)
			{
				System.out.println("Sorry!! No flight, please add flight first...");
			}
			else
			{
			for(int i=0;i<flight.size();i++)
			{
				System.out.println("<<< Flight(s) and Passenger(s) Info >>>");
				System.out.println("Number of Flights: "+flight.size());

					System.out.println("Flight #"+(i+1));
					((Flight)flight.get(i)).displayInfoFlight();

				System.out.println();
				((Flight)flight.get(i)).displayInfoPassengers();

			}
			}
		}
		else if(task==5)
		{
			System.out.println( "Thank you! :)");
			loop=false;
		}
		else
		{
			loop=true;
		}


		}//end of try
		catch(InputMismatchException ex)
		{
			System.out.println("Wrong input!!");
		}
		}//end of loop
	}
}
