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

       //decisions
    while (year <= 10) {
        int acresToBuy = askHowManyAcresToBuy(landValue, bushels);
        int acresToSell = 0;
        if (acresToBuy == 0){
            acresToSell = askHowManyAcresToSell(acresOwned);
        }
        int grainToFeed = askHowMuchGrainToFeedPeople(bushels);
        int acresToPlant = askHowManyAcresToPlant(acresOwned,population,bushels);

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

        immigrants = immigrants(population, bushels, acresOwned);
        population += immigrants;

        harvested = harvest(acresToPlant);
        bushels += harvested;

        ratsAte = grainEatenByRats(bushels);
        bushels -= ratsAte;

        landValue = newCostOfLand();

        year++;

    }


    finalSummary(population,acresOwned,year);



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
        int starved = population - bushels / 20;
        if (starved <= 0) {
            starved = 0;
        }
        return starved;
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
        return (acresOwned + bushels) / (population * 4) - 17;
    }

    //Choose a random integer between 1 and 6, inclusive. Each acre that was planted with seed will yield this many bushels of grain.
    //(Example: if you planted 50 acres, and your number is 3, you harvest 150 bushels of grain). Return the number of bushels harvested.
    public int harvest(int acresOwned) {
        return rand.nextInt(6) + 1;
    }

    //There is a 40% chance that you will have a rat infestation. When this happens, rats will eat somewhere
    // between 10% and 30% of your grain. Return the amount of grain eaten by rats (possibly zero).
    public int grainEatenByRats(int bushels) {
        if (rand.nextDouble() <= 0.4) {
            return rand.nextInt(21) + 10;
        }
        return 0;
    }

    //The price of land is random, and ranges from 17 to 23 bushels per acre.
    // Return the new price for the next set of decisions the player has to make.
    // (The player will need this information in order to buy or sell land.)
    public int newCostOfLand() {
        return rand.nextInt(7) + 17;
    }
}
