package hammurabi.docs.matuszek;

public class playGame {
    public static void main(String[] args){
        playGame game = new playGame();
        game.run();
    }
    public void run() {
        Hammurabi hammurabi = new Hammurabi();
        int year = 1;
        final int GAME_LENGTH = 10;

        System.out.println("welcome msg");
        System.out.println("starter msg");

        while (year <= GAME_LENGTH) {
            System.out.println("year");
            System.out.println("pop");
            System.out.println("acres");
            System.out.println("bushels");
            System.out.println("land_value");

            //player choice

            int acresToBuy = askAcresToBuy(hammurabi);
            int acresToSell = askAcresToSell(hammurabi);
            int bushelsToFeed = askBushelsToFeed(hammurabi);
            int acresToPlant = askAcresToPlant(hammurabi);

            // turn

            processTurn(hammurabi, acresToBuy, acresToSell, bushelsToFeed, acresToPlant);

            //game_voer

            if (hammurabi.population <= 0) {
                System.out.println("pop die");
                break;

                //conditions
            }
            if (hammurabi.uprising(hammurabi.population, hammurabi.starvationDeaths(hammurabi.population, bushelsToFeed))) {
                System.out.println("People Revolt");
                break;
            }
            year++;
            hammurabi.landValue = hammurabi.newCostOfLand();

        }
            if (year > GAME_LENGTH) {
                //WIN
    }        //
            //
            //
        }
        private int askAcresToBuy(Hammurabi hammurabi) {
            System.out.println("How many acres do you wish to buy? (0-" + (hammurabi.bushels / hammurabi.landValue) + ")");
               return hammurabi.scanner.nextInt();

        }
            private int askAcresToSell(Hammurabi hammurabi){
                System.out.println(("acres to sell? (0-" + hammurabi.acresOwned + ")");
                return hammurabi.scanner.nextInt();

        }
            private int askBushelsToFeed(Hammurabi hammurabi){
                System.out.println("bushels to feed ppl? (0-" + hammurabi.bushels + ")");
                 return hammurabi.scanner.nextInt();

        }
            private int askAcresToPlant(Hammurabi hammurabi){
                 int maxAcres = Math.min(hammurabi.acresOwned, hammurabi.bushels / 2);
                 maxAcres = Math.min(maxAcres, hammurabi.population * 10);
                    System.out.println("acres to plant? (0-" + maxAcres + ")");
                    return hammurabi.scanner.nextInt();

        }
            private void processTurn(Hammurabi hammurabi, int acresToBuy, int  acresToSell, int  bushelsToFeed){
                //buy/sell land
                hammurabi.acresOwned += acresToBuy - acresToSell;
                hammurabi.bushels -= acresToBuy * hammurabi.landValue;
                hammurabi.bushels += acresToSell * hammurabi.landValue;
                //feed people
                hammurabi.bushels -= bushelsToFeed;
                // plant crops
                hammurabi.bushels -= acresToPlant / 2;

                // calc results
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

            //reports
        }










}
