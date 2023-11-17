import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

    public class dataInputProgram 
    {
        public static void main(String[] args) 
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of countries:");
            int numCountries = getValidNumber(scanner);

            Country[] countries = new Country[numCountries];

        for (int i = 0; i < numCountries; i++) 
        {
          System.out.println("Enter details for country " + (i + 1) + ":");

          String name = getValidString(scanner, "Country Name:", false);
          String nationalCode = getValidString(scanner, "National Code:", false);
          long cases = getValidLong(scanner, "Detected COVID cases:");
          long deaths = getValidLong(scanner, "Deaths from COVID:");
          String continent = getValidContinent(scanner, "Continent (EU, AF, AS, NA, SA, AU, OT):");

          countries[i] = new Country(name, nationalCode, cases, deaths, continent);
        }

        writeDataToCSV(countries);
        scanner.close();
    }

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

    private static long getValidLong(Scanner scanner, String prompt) 
    {
       while (true) 
       {
         System.out.println(prompt);
         try 
         {
            long number = Long.parseLong(scanner.nextLine());
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

    private static String getValidContinent(Scanner scanner, String prompt)     {   
        while (true) 
        {
          System.out.println(prompt);
          String input = scanner.nextLine().toUpperCase();
          if (input.equals("EU") || input.equals("AF") || input.equals("AS") ||
          input.equals("NA") || input.equals("SA") || input.equals("AU") ||
          input.equals("OT")) 
          {
            return input;
          }
          System.out.println("Invalid continent. Please enter one of the following: EU, AF, AS, NA, SA, AU, OT.");
        }
    }

    private static void writeDataToCSV(Country[] countries) 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter("countries_data.csv"))) 
        {
            writer.println("Country Name,National Code,Detected COVID Cases,Deaths from COVID,Continent");
            for (Country country : countries) 
            {
                writer.println(country.toCSVFormat());
            }    
    System.out.println("Data successfully written to countries_data.csv");
        } 
        catch (IOException e) 
        {
        System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}

    class Country 
    {
    private String name;
    private String nationalCode;
    private long cases;
    private long deaths;
    private String continent;

    public Country(String name, String nationalCode, long cases, long deaths, String continent) 
    {
        this.name = name;
        this.nationalCode = nationalCode;
        this.cases = cases;
        this.deaths = deaths;
        this.continent = continent;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getNationalCode() 
    {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) 
    {
        this.nationalCode = nationalCode;
    }

    public long getCases() 
    {
        return cases;
    }

    public void setCases(long cases) 
    {
        this.cases = cases;
    }

    public long getDeaths() 
    {
        return deaths;
    }

    public void setDeaths(long deaths) 
    {
        this.deaths = deaths;
    }

    public String getContinent() 
    {
        return continent;
    }

    public void setContinent(String continent) 
    {
        this.continent = continent;
    }

    public String toCSVFormat() 
    {
        return String.join(",", name, nationalCode, String.valueOf(cases), String.valueOf(deaths), continent);
    }
    
    public double getDeathPercentage()
    {
        if(cases > 0)
        {
            return (double) deaths / cases * 100;
        }
        return 0;
    }
}
