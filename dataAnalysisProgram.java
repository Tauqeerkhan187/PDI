import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class dataAnalysisProgram
{
    public static void main(String[] args)
    {
        // Scanner for reading Input
        Scanner scanner = new Scanner(System.in);

        // Welcome message.
        System.out.println("Welcome to the WHO Analysis Program :-)");
        
        // Prompt for CSV file name        
        System.out.println("Enter the name of the file containing the data: ");
        String fileName = scanner.nextLine();

        // Read data from CSV file
        Country[] countries = readDataFromCSV(fileName);
    
        // Check if data was loaded or not.

        if(countries.length == 0)
        {
            System.out.println("No data to analyze.");
            return;
        }
        
        String userChoice;
        
        // Loop for multiple analyses.
    
        do 
        {
          System.out.println("An overall analysis or a Continent analysis? (Overall/Continent)");
          userChoice = scanner.nextLine();

        // Perform analysis on user choice.

        if ("Continent".equalsIgnoreCase(userChoice))
        {
            System.out.print("Enter continent code for specific analysis (EU, AF, AS, ME, NA, SA, AU, OT): ");
            String continent = scanner.nextLine().toUpperCase();
            System.out.println("Continent Analysis:");
            performAnalysis(filterByContinent(countries, continent));
        }
        else if ("Overall".equalsIgnoreCase(userChoice))
        {
            System.out.println("Overall Analysis:");
            performAnalysis(countries);
        }

        // Ask the user if they want to exit the program.
    
        System.out.println("Would you like to exit? (Yes/No)");
        userChoice  = scanner.nextLine();
        }
        while (!"Yes".equalsIgnoreCase(userChoice));
        
        scanner.close();
 }

    // Method to READ data from CSV file.
    
    private static Country[] readDataFromCSV(String fileName)
    {
        try (Scanner fileScanner = new Scanner(new File(fileName)))
        {
            Country[] countries = new Country[200];
            int index = 0;
            fileScanner.nextLine(); //Skip header
        
            // READ each line and CREATE country objects.

            while (fileScanner.hasNextLine() && index < countries.length)
            {
                String line = fileScanner.nextLine();
                String[] details = line.split(",");
                countries[index++] = new Country(details[0], details[1], Long.parseLong(details[2]), Long.parseLong(details[3]), details[4]);
            }
            
            // return with data trimmed to correct size.
        
            return trimCountryArray(countries, index);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found: " + e.getMessage());
            return new Country[0];
        }
    }

    // Method to perform analysis on data.

    private static void performAnalysis(Country[] countries)
    {
        // Sort by death percentage and OUTPUT.

        sortCountriesByDeathPercentage(countries);
        System.out.println("Countries sorted by Death Percentage:");
        displayCountries(countries);

        // Sort by total cases and OUTPUT.
    
        sortCountriesByTotalCases(countries);
        System.out.println("\nCountries sorted by Total Cases:");
        displayCountries(countries);

        // Sort by total deaths and OUTPUT.    
        sortCountriesByTotalDeaths(countries);
        System.out.println("\nCountries sorted by Total Deaths:");
        displayCountries(countries);

    }

    // Sort countries by death percentage using insertion sort

    private static void sortCountriesByDeathPercentage(Country[] countries)
    {
        for (int i = 1; i < countries.length; i++)
        {
            Country key = countries[i];
            int j = i - 1;
            
            // logic for Insertion sort.

            while (j >= 0 && countries[j] != null && key != null && countries[j].getDeathPercentage() < key.getDeathPercentage())
            {
                countries[j + 1] = countries[j];
                j = j - 1;
            }
            countries[j + 1] = key;
        }
    }

    // Sort countries by total cases using insertion sort.
    
    private static void sortCountriesByTotalCases(Country[] countries)
    {
        for (int i = 1; i < countries.length; i++)
        {
            Country key = countries[i];
            int j = i - 1;
            
            // Logic for insertion sort.

            while (j >= 0 && countries[j] != null && key != null && countries[j].getCases() < key.getCases())
            {
                countries[j + 1] = countries[j];
                j = j - 1;
            }
            countries[j + 1] = key;
        }
    }
    
    // Sort countries by total deaths using insertion sort

    private static void sortCountriesByTotalDeaths(Country[] countries) 
    {
        for( int i = 1; i< countries.length; i++)
        {
            Country key = countries[i];
            int j = i - 1;
            
            // logic for Insertion sort

            while( j >= 0 && countries[j] != null && key != null && countries[j].getDeaths() < key.getDeaths())
            {
                countries[j + 1] = countries[j];
                j = j - 1;
            }
            countries[j + 1] = key;
        }
    }

    // Method for displaying all countries in array.

    private static void displayCountries(Country[] countries)
    {
        for(Country country : countries)
        {
            if (country != null)
            {
                System.out.println(country);
            }
        }
    }

    // Filter the array of countries based on continent.

    private static Country[] filterByContinent(Country[] countries, String continent)
    {
        Country[] filteredCountries = new Country[countries.length];
        int index = 0;
        
        // Loop to check if country matches continent.
    
        for(Country country : countries)
        {
            if(country != null && country.getContinent().equalsIgnoreCase(continent))
            {
                filteredCountries[index++] = country;
            }
        }
    
        return trimCountryArray(filteredCountries, index);
    }

    // Trim array to size of actual data
    
    private static Country[] trimCountryArray(Country[] countries, int size)
    {
        Country[] trimmedArray = new Country[size];
        System.arraycopy(countries, 0, trimmedArray, 0, size);
        return trimmedArray;
    }
}

// Country class with fields, constructors, and methods.
class Country
{
    private String name;
    private String nationalCode;
    private long cases;
    private long deaths;
    private String continent;
    
    // Constructor for Country class

    public Country(String name, String nationalCode, long cases, long deaths, String continent)
    {
        this.name = name;
        this.nationalCode = nationalCode;
        this.cases = cases;
        this.deaths = deaths;
        this.continent = continent;
    }

    // Getter for continent.

    public String getContinent()
    {
        return continent;
    }

    // Calculate and return death Percentage.

    public double getDeathPercentage()
    {
        return (cases > 0) ? (double) deaths / cases * 100 : 0;
    }
    
    // Getter for cases

    public long getCases()
    {
        return cases;
    }

    // Getter for deaths

    public long getDeaths()
    {
        return deaths;
    }

    // Convert data to CSV format

    public String toCSVFormat()
    {
      return String.join(",", name, nationalCode, String.valueOf(cases), String.valueOf(deaths), continent);
    } 

    // Override toString to provide a formatted string representation of country objects
    
    @Override

    public String toString()
    {
        return name + "(" + nationalCode + ") - Cases: " + cases + ", Deaths: " + deaths + ", Continent: " + continent + ", Death Percentage: " + String.format("%.2f", getDeathPercentage()) + "%"; 
    }

}            
