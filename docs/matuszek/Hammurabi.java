package hammurabi.docs.matuszek;

import java.util.Random;
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    int population = 100;
    int bushels = 2800;
    int acresOwned = 1000;
    int landValue = 19;


    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        int year = 1;
        int population = 100;
        int bushels = 2800;
        int acresOwned = 1000;
        int landValue = 19;
        int starved = 0;
        int immigrants = 0;
        int ratsAte = 0;
        int harvested = 0;

        //decisions
        while (year <= 10) {
            printYearSummary(year, starved, immigrants, population, harvested, acresOwned, ratsAte, bushels, landValue);

            int acresToBuy = askHowManyAcresToBuy(landValue, bushels);
            int acresToSell = 0;
            if (acresToBuy == 0) {
                acresToSell = askHowManyAcresToSell(acresOwned);
            }
            int grainToFeed = askHowMuchGrainToFeedPeople(bushels);
            int acresToPlant = askHowManyAcresToPlant(acresOwned, population, bushels);

            //updates
            acresOwned += acresToBuy - acresToSell;
            bushels -= acresToBuy * landValue;
            bushels += acresToSell * landValue;
            bushels -= grainToFeed;

            //events
            int plagueVictims = plagueDeaths(population);
            population -= plagueVictims;

            starved = starvationDeaths(population, grainToFeed);
            population -= starved;
            if (uprising(population, starved)) {
                System.out.println("You have been overthrown due to extreme mismanagement");
                return;
            }

            immigrants = immigrants(population, acresOwned, bushels);
            population += immigrants;

            harvested = harvest(acresToPlant);
            bushels += harvested;

            ratsAte = grainEatenByRats(bushels);
            bushels -= ratsAte;

            landValue = newCostOfLand();

            year++;

        }


        finalSummary(population, acresOwned, year);


    }

    void printYearSummary(int year, int starved, int immigrants, int population, int harvested, int acresOwned, int ratsAte, int bushels, int landValue) {
        System.out.println("\n Great Hammurabi!");
        System.out.println("You are in year " + year + " of your ten year rule.");
        System.out.println("In the previous year " + starved + " people starved to death.");
        System.out.println("In the previous year " + immigrants + " people entered the kingdom.");
        System.out.println("The population is now " + population + ".");
        System.out.println("We harvested " + harvested + " bushels at " + (harvested / acresOwned) + " bushels per acre.");
        System.out.println("Rats destroyed " + ratsAte + " bushels, leaving " + bushels + " bushels in storage.");
        System.out.println("The city owns " + acresOwned + " acres of land.");
        System.out.println("Land is currently worth " + landValue + " bushels per acre.");
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        int acres = getNumber("How many acres do you wish to buy? ");
        if (acres * price <= bushels) {
            return acres;
        }
        System.out.println(" Great Hammurabi, we only have" + bushels + " bushels");
        return 0;
    }

    int askHowManyAcresToSell(int acresOwned) {
        int acres = getNumber("How many acres do you wish to sell? ");
        if (acres <= acresOwned) {
            return acres;
        }
        System.out.println(" Great Hammurabi, we only have " + acresOwned + " acres!");
        return 0;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int grain = getNumber("How many bushels do you wish to feed your people? ");
        if (grain <= bushels) {
            return grain;
        }
        System.out.println("  Great Hammurabi, we have only " + bushels + " bushels!");
        return 0;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        int acres = getNumber("How many acres do you wish to plant with seed? ");
        if (acres <= acresOwned && acres <= population * 10 && acres * 2 <= bushels) {
            return acres;
        }
        System.out.println(" Great Hammurabi, we don't have enough resources for that!");
        return 0;
    }

    void finalSummary(int population, int acresOwned, int years) {
        System.out.println("\nGame Over!");
        System.out.println("You ruled for " + years + " years.");
        System.out.println("Final population: " + population);
        System.out.println("Final land holdings: " + acresOwned + " acres");
        int acresPerPerson = population > 0 ? acresOwned / population : 0;
        System.out.println("Acres per person: " + acresPerPerson);
    }

    int getNumber(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    //Each year, there is a 15% chance of a horrible plague. When this happens,
    //half your people die.Return the number of plague deaths (possibly zero).
    public int plagueDeaths(int population) {
        int deaths = 0;
        if (rand.nextDouble() <= 0.15) {
            deaths = population / 2;
        }
        return deaths;
    }

    //Each person needs 20 bushels of grain to survive. If you feed them more than this, they are happy,
    // but the grain is still gone. You don't get any benefit from having happy subjects. Return the number
    // of deaths from starvation (possibly zero).
    public int starvationDeaths(int population, int bushels) {
//        int peopleFed = bushels / 20;
//        int starved = population - peopleFed;
//        if (starved < 0) {
//            starved = 0;
//        }
//        return starved;
        int peopleFed = bushels / 20;
        int starved = population - peopleFed;
        return starved > 0 ? starved : 0;
    }

    //Return true if more than 45% of the people starve.
    //(This will cause you to be immediately thrown out of office, ending the game.)
    public boolean uprising(int population, int dissatisfaction) {
        int threshold = population * 45 / 100;

        return dissatisfaction > threshold;
    }

    //Nobody will come to the city if people are starving (so don't call this method).
    // If everyone is well fed, compute how many people come to the city as:
    // (20 * _number of acres you have_ + _amount of grain you have in storage_) / (100 * _population_) + 1
    public int immigrants(int population, int bushels, int acresOwned) {
        if (population <= 0 || starvationDeaths(population, bushels) > 0) {
            return 0;
        }
        int immigrants = ((20 * acresOwned + bushels) / (100 * population)) + 1;
        return immigrants > 0 ? immigrants : 0;
//        if (population <= 0 || starvationDeaths(population, bushels) > 0) {
//            return 0;
//        }
//        return (acresOwned + bushels) / (population * 4) - 17;
    }

    //Choose a random integer between 1 and 6, inclusive. Each acre that was planted with seed will yield this many bushels of grain.
    //(Example: if you planted 50 acres, and your number is 3, you harvest 150 bushels of grain). Return the number of bushels harvested.
    public int harvest(int acresPlanted) {
        int yieldPerAcre = rand.nextInt(6) + 1;
        return acresPlanted * yieldPerAcre;
        //int yieldPerAcre = rand.nextInt(6) + 1;
        //return acresOwned * yieldPerAcre;
        //return rand.nextInt(6) + 1; (acresOwned)
    }

    //There is a 40% chance that you will have a rat infestation. When this happens, rats will eat somewhere
    // between 10% and 30% of your grain. Return the amount of grain eaten by rats (possibly zero).
    public int grainEatenByRats(int bushels) {
        if (rand.nextDouble() <= 0.4) {
            int percentEaten = rand.nextInt(21) + 10;
            return (bushels * percentEaten) / 100;
        }
        return 0;
//        if (rand.nextDouble() <= 0.4) {
//            return rand.nextInt(21) + 10;
//        }
//        return 0;
    }

    //The price of land is random, and ranges from 17 to 23 bushels per acre.
    // Return the new price for the next set of decisions the player has to make.
    // (The player will need this information in order to buy or sell land.)
    public int newCostOfLand() {
        return rand.nextInt(7) + 17;
    }
}
