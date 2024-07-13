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
            if (year GAME_LENGTH){
                //WIN
            //
            //
            //
        }
            private int askAcresToBuy(){

        }
            private int askAcresToSell(){

        }
            private int askBushelsToFeed90{

        }
            private int askAcresToPlant{

        }
            private void processTurn90{
                //buy/sell land
                //feed people
                // plant crops
                

                // calc results
        }
    }









}
