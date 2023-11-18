import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class dataAnalysisProgram
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the WHO Analysis Program :-)");
        
        System.out.println("Enter the name of the file containing the data: ");
        String fileName = scanner.nextLine();
        Country[] countries = readDataFromCSV(fileName);
    
    
        if(countries.length == 0)
        {
            System.out.println("No data to analyze.");
            return;
        }

        
        String userChoice;

        do 
        {
          System.out.println("An overall analysis or a Continent analysis? (Overall/Continent)");
          userChoice = scanner.nextLine();

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

        System.out.println("Would you like to exit? (Yes/No)");
        userChoice  = scanner.nextLine();
        }
        while (!"Yes".equalsIgnoreCase(userChoice));
        
        scanner.close();
 }

    private static Country[] readDataFromCSV(String fileName)
    {
        try (Scanner fileScanner = new Scanner(new File(fileName)))
        {
            Country[] countries = new Country[200];
            int index = 0;
            fileScanner.nextLine(); //Skip header
        
            while (fileScanner.hasNextLine() && index < countries.length)
            {
                String line = fileScanner.nextLine();
                String[] details = line.split(",");
                countries[index++] = new Country(details[0], details[1], Long.parseLong(details[2]), Long.parseLong(details[3]), details[4]);
            }
        
            return trimCountryArray(countries, index);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found: " + e.getMessage());
            return new Country[0];
        }
    }

    private static void performAnalysis(Country[] countries)
    {
        sortCountriesByDeathPercentage(countries);
        System.out.println("Countries sorted by Death Percentage:");
        displayCountries(countries);

    
        sortCountriesByTotalCases(countries);
        System.out.println("\nCountries sorted by Total Cases:");
        displayCountries(countries);

    
        sortCountriesByTotalDeaths(countries);
        System.out.println("\nCountries sorted by Total Deaths:");
        displayCountries(countries);

    }

    private static void sortCountriesByDeathPercentage(Country[] countries)
    {
        for (int i = 1; i < countries.length; i++)
        {
            Country key = countries[i];
            int j = i - 1;

            while (j >= 0 && countries[j] != null && key != null && countries[j].getDeathPercentage() < key.getDeathPercentage())
            {
                countries[j + 1] = countries[j];
                j = j - 1;
            }
            countries[j + 1] = key;
        }
    }

    private static void sortCountriesByTotalCases(Country[] countries)
    {
        for (int i = 1; i < countries.length; i++)
        {
            Country key = countries[i];
            int j = i - 1;

            while (j >= 0 && countries[j] != null && key != null && countries[j].getCases() < key.getCases())
            {
                countries[j + 1] = countries[j];
                j = j - 1;
            }
            countries[j + 1] = key;
        }
    }

    private static void sortCountriesByTotalDeaths(Country[] countries) 
    {
        for( int i = 1; i< countries.length; i++)
        {
            Country key = countries[i];
            int j = i - 1;
        
            while( j >= 0 && countries[j] != null && key != null && countries[j].getDeaths() < key.getDeaths())
            {
                countries[j + 1] = countries[j];
                j = j - 1;
            }
            countries[j + 1] = key;
        }
    }

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

    private static Country[] filterByContinent(Country[] countries, String continent)
    {
        Country[] filteredCountries = new Country[countries.length];
        int index = 0;

        for(Country country : countries)
        {
            if(country != null && country.getContinent().equalsIgnoreCase(continent))
            {
                filteredCountries[index++] = country;
            }
        }
    
        return trimCountryArray(filteredCountries, index);
    }

    private static Country[] trimCountryArray(Country[] countries, int size)
    {
        Country[] trimmedArray = new Country[size];
        System.arraycopy(countries, 0, trimmedArray, 0, size);
        return trimmedArray;
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

    public String getContinent()
    {
        return continent;
    }

    public double getDeathPercentage()
    {
        return (cases > 0) ? (double) deaths / cases * 100 : 0;
    }
    
    public long getCases()
    {
        return cases;
    }

    public long getDeaths()
    {
        return deaths;
    }

    public String toCSVFormat()
    {
      return String.join(",", name, nationalCode, String.valueOf(cases), String.valueOf(deaths), continent);
    } 

    public String toString()
    {
        return name + "(" + nationalCode + ") - Cases: " + cases + ", Deaths: " + deaths + ", Death Percentage: " + String.format("%.2f", getDeathPercentage()) + "%"; 
    }

}            
