/********************************************************************************
* Author: Tauqeer Khan                                                          *
* Purpose: To take input from the user, validate it and write it in a csv file. *
* Date: 5/11/2023                                                               *
*********************************************************************************/ 
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

    public class dataInputProgram 
    {
        public static void main(String[] args) 
        {
          // Scanner to read input from user.
          Scanner scanner = new Scanner(System.in);
          System.out.println("Welcome to the WHO Data Entry Program");
          System.out.println("How many countries' data are you looking to enter?");
            int numCountries = getValidNumber(scanner); // get number of countries to enter data for.

            Country[] countries = new Country[numCountries]; // array to hold objects.

        for (int i = 0; i < numCountries; i++) // for loop to get data
        {
          System.out.println("Please enter the data:");
          System.out.print("Country Name:");
          String name = getValidString(scanner, "", false);
          System.out.print("National code: ");
          String nationalCode = getValidString(scanner, "", false);
          System.out.print("Detected COVID cases: ");
          long cases = getValidLong(scanner, "");
          System.out.print("Deaths from COVID: ");
          long deaths = getValidLong(scanner, "");
          System.out.print("Continent: ");
          String continent = getValidContinent(scanner, "Continent (EU, AF, AS, ME, NA, SA, AU, OT):");

          countries[i] = new Country(name, nationalCode, cases, deaths, continent);
        }
         
        // Display the data entered by the user.

        System.out.println("The current data looks like this:");
        for (Country country : countries)
        {
            System.out.println(country);
        }
        
        // Prompt user for name of CSV file
    
        System.out.println("What would you like to name your CSV file? ");
        String csvFileName = scanner.nextLine();
        writeDataToCSV(countries, csvFileName);
        System.out.println("Thank you and have a great day.");
    
        scanner.close(); //Closing Scanner
    }

    // Method for getting valid integer from user.

    private static int getValidNumber(Scanner scanner) 
    {
      int number;
      while (true) 
      {
        try 
        {
          System.out.println("Please enter a valid positive number:");
          number = Integer.parseInt(scanner.nextLine());
          if (number > 0) 
          {
            return number;
          } 
          else 
          {
            System.out.println("The number must be positive.");
          }
        } 
        catch(NumberFormatException e) 
        {
         System.out.println("Invalid input. Please enter a valid number.");
        }
      }
    }

    // Method to get valid String from user.

    private static String getValidString(Scanner scanner, String prompt, boolean allowDigits) 
    {
      String input;
      while (true) 
      {
        System.out.println(prompt);
        input = scanner.nextLine();
        if (!input.trim().isEmpty() && (allowDigits || !input.matches("\\d+")))
        {
            return input;
        }

        System.out.println("Invalid input. Please enter a valid string.");
      }
    }
   
    // Method for getting valid long from user.

    private static long getValidLong(Scanner scanner, String prompt) 
    {
       long number;

       while (true) 
       {
         System.out.println(prompt);
         try 
         {
             number = Long.parseLong(scanner.nextLine());
            if (number >= 0) 
            {
              return number;
            }
            System.out.println("Invalid input. Please enter a non-negative number.");
         } 
         catch (NumberFormatException e) 
         {
          System.out.println("Invalid input. Please enter a valid number:");
         }
       }
    }

    // Method for valid continent.
    
    private static String getValidContinent(Scanner scanner, String prompt)     {  
        String input;
 
        while (true) 
        {
          System.out.println(prompt);
          input = scanner.nextLine().toUpperCase();
          if (input.equals("EU") || input.equals("AF") || input.equals("AS") ||
          input.equals("ME") || input.equals("NA") || input.equals("SA") || input.equals("AU") || input.equals("OT")) 
          {
            return input;
          }
          System.out.println("Invalid continent. Please enter one of the following: EU, AF, AS, ME, NA, SA, AU, OT.");
        }
    }
    
    //Method to Print data of countries into CSV file.
    
    private static void writeDataToCSV(Country[] countries, String fileName) 
    {
      try (PrintWriter writer = new PrintWriter(new FileWriter("countries_data.csv"))) 
      {
       writer.println("Country Name,National Code,Detected COVID Cases,Deaths from COVID,Continent");
          
            for (Country country : countries) 
            {
                writer.println(country.toCSVFormat());
            }    
        
        System.out.println("Data successfully written to " + fileName);
      } 
        catch (IOException e) 
        {
        System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}

// Country class with fields, constrcutors and methods.

    class Country 
    {
    private String name;
    private String nationalCode;
    private long cases;
    private long deaths;
    private String continent;

    // Constructor for new country object

    public Country(String name, String nationalCode, long cases, long deaths, String continent) 
    {
        this.name = name;
        this.nationalCode = nationalCode;
        this.cases = cases;
        this.deaths = deaths;
        this.continent = continent;
    }

    // Getter for country name

    public String getName() 
    {
        return name;
    }

    // Setter for country name

    public void setName(String name) 
    {
        this.name = name;
    }

    // getter for National code

    public String getNationalCode() 
    {
        return nationalCode;
    }

    // Setter for National code

    public void setNationalCode(String nationalCode) 
    {
        this.nationalCode = nationalCode;
    }
    
    // Getter for detected cases
    
    public long getCases() 
    {
        return cases;
    }

    // Setter for detected cases

    public void setCases(long cases) 
    {
        this.cases = cases;
    }

    // Getter for deaths

    public long getDeaths() 
    {
        return deaths;
    }
    
    // Setter for deaths

    public void setDeaths(long deaths) 
    {
        this.deaths = deaths;
    }

    // Getter for continent

    public String getContinent() 
    {
        return continent;
    }

    // Setter for continent

    public void setContinent(String continent) 
    {
        this.continent = continent;
    }

    // Method to return a CSV format string of a Country's data

    public String toCSVFormat() 
    {
        // Concatenate fields with commas for CSV

        String csvFormat =  String.join(",", name, nationalCode, String.valueOf(cases), String.valueOf(deaths), continent);

        return csvFormat;
    }

    // Method to calculate and return death percentage
    
    public double getDeathPercentage()
    {
        double deathPercentage = 0;

        if(cases > 0)
        {
            // Calculate death percentage; confirm no divison by zero
            
            deathPercentage = (double) deaths / cases * 100;
        }
        return deathPercentage;
    }

    public String toString() 
    {
        return name + " (" + nationalCode + ") - Cases: " + cases + " , Deaths: " + deaths + ", Death Percentage: " + String.format("%.2f", getDeathPercentage()) + "%";
    }
}   
