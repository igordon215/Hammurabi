package hammurabi.docs.matuszek;

public class playGame {
    public static void main(String[] args) {
        playGame game = new playGame();
        game.run();
    }

    public void run() {
        Hammurabi hammurabi = new Hammurabi();
        int year = 1;
        final int GAME_LENGTH = 10;

        System.out.println("WELCOME TO THE GAME OF HAMMURABI!");
        System.out.println("👑 YOU ARE THE RULER OF ANCIENT SUMERIA FOR 10-YEAR TERM 👑");

        while (year <= GAME_LENGTH) {
            System.out.println("YEAR " + year);
            System.out.println("POPULATION " + hammurabi.population);
            System.out.println("ACRES OWNED " + hammurabi.acresOwned);
            System.out.println("BUSHELS IN STORAGE " + hammurabi.bushels);
            System.out.println("LAND VALUE " + hammurabi.landValue);

            // PLAYER CHOICE
            int acresToBuy = askAcresToBuy(hammurabi);
            int acresToSell = askAcresToSell(hammurabi);
            int bushelsToFeed = askBushelsToFeed(hammurabi);
            int acresToPlant = askAcresToPlant(hammurabi);

            // PROCESS TURN
            hammurabi.acresOwned += acresToBuy - acresToSell;
            hammurabi.bushels -= acresToBuy * hammurabi.landValue;
            hammurabi.bushels += acresToSell * hammurabi.landValue;

            hammurabi.bushels -= bushelsToFeed;
            hammurabi.bushels -= acresToPlant / 2;

            int peopleStarved = hammurabi.starvationDeaths(hammurabi.population, bushelsToFeed);
            hammurabi.population -= peopleStarved;

            int plagueVictims = hammurabi.plagueDeaths(hammurabi.population);
            hammurabi.population -= plagueVictims;

            int harvestYield = hammurabi.harvest(acresToPlant);
            hammurabi.bushels += acresToPlant * harvestYield;

            int grainLost = hammurabi.grainEatenByRats(hammurabi.bushels);
            hammurabi.bushels -= grainLost;

            int newImmigrants = hammurabi.immigrants(hammurabi.population, hammurabi.bushels, hammurabi.acresOwned);
            hammurabi.population += newImmigrants;

            // END OF YEAR REPORTS
            System.out.println("\nYEAR-END REPORT:");
            System.out.println(peopleStarved + " PEOPLE STARVED");
            System.out.println(plagueVictims + " PEOPLE DIED FROM THE PLAGUE");
            System.out.println("THE HARVEST YIELDED: " + harvestYield + " BUSHELS PER ACRE");
            System.out.println(grainLost + " BUSHELS WERE LOST TO RATS");
            System.out.println(newImmigrants + " NEW IMMIGRANTS ARRIVED IN THE CITY");

            //END OF YEAR SUMMARY
            System.out.println("\nEND OF THE YEAR SUMMARY:");
            System.out.println("POPULATION: " + hammurabi.population);
            System.out.println("ACRES OWNED: " + hammurabi.acresOwned);
            System.out.println("BUSHELS IN STORAGE: " + hammurabi.bushels);
            System.out.println("LAND VALUE: " + hammurabi.landValue);
            System.out.println("--------------------");

            if (hammurabi.population <= 0) {
                System.out.println("YOUR ENTIRE POPULATION HAS DIED☠️... GAME OVER!!");
                break;
            }
            if (hammurabi.uprising(hammurabi.population, peopleStarved)) {
                System.out.println("THE PEOPLE HAVE REVOLTED AGAINST YOU DUE TO STARVATION!! YOU HAVE BEEN OVERTHROWN!!");
                break;
            }
            year++;
            hammurabi.landValue = hammurabi.newCostOfLand();
        }
        if (year > GAME_LENGTH) {
            System.out.println("CONGRATULATIONS! YOU'VE COMPLETED YOUR 10-YEAR TERM AS RULER OF SUMERIA");
        }
        System.out.println("FINAL POPULATION " + hammurabi.population);
        System.out.println("FINAL ACRES OWNED " + hammurabi.acresOwned);
        System.out.println("FINAL BUSHELS IN STORAGE " + hammurabi.bushels);
    }

    private int askAcresToBuy(Hammurabi hammurabi) {
        System.out.println("HOW MANY ACRES DO YOU WISH TO BUY? (0-" + (hammurabi.bushels / hammurabi.landValue) + ")");
        return hammurabi.scanner.nextInt();
    }

    private int askAcresToSell(Hammurabi hammurabi) {
        System.out.println("HOW MANY ACRES DO YOU WISH TO SELL? (0-" + hammurabi.acresOwned + ")");
        return hammurabi.scanner.nextInt();
    }

    private int askBushelsToFeed(Hammurabi hammurabi) {
        System.out.println("HOW MANY BUSHELS DO YOU WISH TO FEED YOUR PEOPLE? (0-" + hammurabi.bushels + ")");
        return hammurabi.scanner.nextInt();
    }

    private int askAcresToPlant(Hammurabi hammurabi) {
        int maxAcres = Math.min(hammurabi.acresOwned, hammurabi.bushels / 2);
        maxAcres = Math.min(maxAcres, hammurabi.population * 10);
        System.out.println("HOW MANY ACRES DO YOU WISH TO PLANT? (0-" + maxAcres + ")");
        return hammurabi.scanner.nextInt();
    }
}