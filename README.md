# PDI
PDI Assignment for Curtin University

# COVID-19 Data Management System

## Overview
This repository contains two Java programs: `dataInputProgram` and `dataAnalysisProgram`. These programs are designed to handle and analyze COVID-19 data across various countries. The `dataInputProgram` allows users to input data which is then written to a CSV file, while the `dataAnalysisProgram` reads this data for further analysis.

### dataInputProgram
This program collects information about COVID-19 cases from different countries. It prompts the user for the following details:
- Country Name
- National Code
- Detected COVID-19 Cases
- Deaths from COVID-19
- Continent

The data is validated and then written to a CSV file named by the user.

### dataAnalysisProgram
This program reads the data from the CSV file and offers two types of analyses:
- Overall Analysis: Analyze data from all countries.
- Continent Analysis: Analyze data based on a specific continent.

The program sorts countries based on death percentage, total cases, and total deaths using the insertion sort algorithm.

## Installation
Clone the repository using:
```
git clone https://github.com/Tauqeerkhan187/PDI.git
```

## Usage
Run each program in a Java-enabled environment.

### Running dataInputProgram
1. Compile the program: `javac dataInputProgram.java`
2. Run the program: `java dataInputProgram`
3. Follow the on-screen prompts to input data.

### Running dataAnalysisProgram
1. Compile the program: `javac dataAnalysisProgram.java`
2. Run the program: `java dataAnalysisProgram`
3. Enter the name of the CSV file when prompted.
4. Choose the type of analysis you wish to perform.

## Contributing
Contributions to this project are welcome. Please fork the repository and submit a pull request with your changes.

## Contact
For any questions or suggestions, please reach out to [Tauqeerkhan1888@gmail.com].

---

