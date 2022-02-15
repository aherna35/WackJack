import java.util.Scanner;

public class WackJack {
    static int userCardSum = 0, dealerCardSum = 0, bettingChips = 10, playerBet = 0, userAce = 0, dealerAce = 0;
    static int userCardArray[] = new int[21], dealerCardArray[] = new int[21];
    static String topUserCards = "", midUserCards = "", botUserCards = "";
    static String topDealerCards = "", midDealerCards = "", botDealerCards = "";
    static String cardTop = " . - . ", cardBot   = " . _ . ";
    static String oneMid   = " . 1 . ", twoMid   = " . 2 . ", threeMid = " . 3 . ", fourMid = " . 4 . ",   fiveMid = " . 5 . ";
    static String sixMid   = " . 6 . ", sevenMid = " . 7 . ", eightMid = " . 8 . ", nineMid = " . 9 . ",   tenMid  = " . X . ";
    static String jackMid  = " . J . ", queenMid = " . Q . ", kingMid  = " . K . ", aceMid  = " . A . ", hiddenMid = " . ? . ";

    /**
     * @return (int) 
     * 
     * Draws a random card in between 1 and 13
     */
    public static int drawRandomCard() {
        return (int) ( (Math.random() * 14) + 1);
    }

    /**
     * @param card (int)
     * @return (String)
     * 
     * Returns a string based off of which card was inputted
     */
    public static String createCards(int card) {
        switch (card) {
            case 1:  return oneMid;
            case 2:  return twoMid;
            case 3:  return threeMid;
            case 4:  return fourMid;
            case 5:  return fiveMid;
            case 6:  return sixMid;
            case 7:  return sevenMid;
            case 8:  return eightMid;
            case 9:  return nineMid;
            case 10: return tenMid;
            case 11: return jackMid;
            case 12: return kingMid;
            case 13: return queenMid;
            case 14: return aceMid;
            case 15: return hiddenMid;
            default: return "something went terrible wrong";
        }
    }

    /**
     * @param card1 (int)
     * @param u (char)
     * Adds a card to the card string depending on if it's a player (u) or dealer (anything else)
     */
    public static void addCardToString(int card1, char u) {
        if (u == 'u') {
            topUserCards += cardTop;
            midUserCards += createCards(card1);
            botUserCards += cardBot;
        }
        else {
            topDealerCards += cardTop;
            midDealerCards += createCards(card1);
            botDealerCards += cardBot + "";
        }
    }

    /**
     * @param u (char)
     * Prints either player card string or user card string
     */
    public static void printCardString(char u) {
        if (u == 'u') {
            System.out.println(topUserCards);
            System.out.println(midUserCards);
            System.out.println(botUserCards);
        }
        else {
            System.out.println(topDealerCards);
            System.out.println(midDealerCards);
            System.out.println(botDealerCards);
        }
    }

    /**
     * @param card1 (int)
     * @param card2 (int)
     * @return (String)
     * Creates string with two cards, specifically useful for the start of the game
     */
    public static String twoCardsString(int card1, int card2) {
        String topString = cardTop + cardTop + "\n";
        String botString = cardBot + cardBot + "\n";
        return (topString + createCards(card1) + createCards(card2) + "\n" + botString);
    }

    /**
     * @param card1 (int)
     * @return (String)
     * Returns a singular card, specifically useful for hitting
     */
    public static String oneCardString(int card1) {
        return (cardTop + "\n" + createCards(card1) + "\n" + cardBot);
    }

    /**
     * @return (String)
     * Determines if user wants to hit or stay, incorrect input catch 
     */
    public static String hitOrStay() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Hit or Stay? ");
            String userInput = scan.nextLine();
            if ( (userInput.equalsIgnoreCase("hit")) || userInput.equalsIgnoreCase("stay")) {
                return userInput;
            }
            else System.out.println("Please write 'hit' or 'stay'");
        }
    }

    /**
     * Uses hit or stay function to play (player), determines if the drawn card breaks or equals 21
     */
    public static void testUserLuck() {
        String playAnswer = hitOrStay();
        while (!playAnswer.equalsIgnoreCase("stay")) {

            int newCard = drawRandomCard();
            userCardSum += getCardValue(newCard);


            addCardToString(newCard, 'u');
            printCardString('u');

            checkAce('u');

            System.out.println("\nyour new total is " + userCardSum + "\n");

            if (userCardSum > 21) {
                System.out.println("Bust! Player loses their bet.");
                bettingChips -= playerBet;
                newGame();
            }
            if (userCardSum <= 20) {
                playAnswer = hitOrStay();
            }
            else playAnswer = "stay";

         }
    }

    /**
     * Uses hit or stay function to play (dealer), determines if the drawn card breaks or equals 21
     */
    public static void testDealerLuck() {
        System.out.println("\nThe dealer's cards are \n");
        printCardString('d');
        System.out.println("\ndealer's total is " + dealerCardSum + "\n");

        while (dealerCardSum < 17) {
            int newCard = drawRandomCard();

            dealerCardSum += getCardValue(newCard);

            addCardToString(newCard, 'd');
            printCardString('d');
            checkAce('d');

            System.out.println("\nDealer's new total is " + dealerCardSum + "\n");
            if (dealerCardSum > 21) {
                //System.out.println("Bust! Dealer loses");
            }
        }
    }

    /**
     * @return (String)
     * Determines whether or not the user won their bet / draw (push)
     */
    public static String determineWinner() {
        if (userCardSum > dealerCardSum && userCardSum <= 21) {
            bettingChips += playerBet;
            return "\nYou win your bet!";
        }
        if (dealerCardSum > userCardSum && dealerCardSum <=21) {
            bettingChips -= playerBet;
            return "\nYou lost your bet!";
        }
        if (userCardSum == dealerCardSum) {
            return "Push! Your bet was returned to you.";
        }
        else {
            bettingChips += playerBet;
            return "Dealer bust, and you won your bet!";
        }
    }

    /**
     * Resets the game state back to 0
     */
    public static void reset() {
        userCardSum = 0;
        dealerCardSum = 0;
        playerBet = 0;
        topUserCards = "";
        midUserCards = "";
        botUserCards = "";
        topDealerCards = "";
        midDealerCards = "";
        botDealerCards = "";
        userCardArray = new int[21];
        dealerCardArray = new int[21];
        userAce = 0;
        dealerAce = 0;
    }

    /**
     * Asks how many chips to bet unless the user is out of chips (game ends)
     */
    public static void placeBet() {

        if (bettingChips == 0) {
            System.out.println("\nUnfortunately you are out of chips! Come back again sometime soon.\n");
            System.exit(0);
        }
        Scanner scan = new Scanner(System.in);
        System.out.print("\nYou currently have " + bettingChips + " betting chips." +
                            "\nHow many would you like to bet? ");
        playerBet = scan.nextInt();
        if (playerBet < 1 || playerBet > bettingChips) {
            System.out.println("Please place a bet with the amount of betting chips you have.");
            placeBet();
        }
                        
    }

    /**
     * Prompts user to see if they would like to play a new game, if so they play otherwise close with their ending chips
     */
    public static void newGame() {
        System.out.print("\nWould you like to play again? if so, type 'yes': ");
        Scanner scan = new Scanner(System.in);
        if (scan.nextLine().equalsIgnoreCase("yes")) {
            reset();
            //scan.close();
            reset();
            playBlackJack();
        }
        else {
            //scan.close();
            System.out.println("\nYou started with 10 and cashed out with " + bettingChips + " betting chips!!!\n");
            System.exit(0);
        }
        
    }

    /**
     * @param card (int)
     * @return
     * returns the face value of the card
     */
    public static int getCardValue(int card) {

        switch (card) {
            case 1:  return 1;
            case 2:  return 2;
            case 3:  return 3;
            case 4:  return 4;
            case 5:  return 5;
            case 6:  return 6;
            case 7:  return 7;
            case 8:  return 8;
            case 9:  return 9;
            case 10: return 10;
            case 11: return 10;
            case 12: return 10;
            case 13: return 10;
            case 14: return 1; //ace
            case 15: return 11; //ace tester value
            default: return 0;
        }

    }
    
    /**
     * @param card (int)
     * @param u (char)
     * Adds card to the array of cards for either user or dealer
     */
    public static void addCardToDeck(int card, char u) {
        if (u == 'u') {
            for (int i = 0; i < userCardArray.length-1; i++) {
                if (userCardArray[i] == 0) {
                    userCardArray[i] = card;
                    break;
                }
            }
        }

        else {
            for (int i = 0; i < userCardArray.length-1; i++) {
                if (dealerCardArray[i] == 0) {
                    dealerCardArray[i] = card;
                    break;
                }
            }
        }
            
    }

    /**
     * @param u (char)
     * @return
     * returns the sum of the deck of cards for either the player or the dealer
     */
    public static int getSum(char u) {
        if (u == 'u') {
            for (int i = 0; i < userCardArray.length-1; i++) {
                if (userCardArray[i] == 0) {
                    break;
                }
                if (userCardArray[i] == 14) { //Ace catch 
                    userCardSum += 11;
                    userAce++;
                    continue;
                }
                userCardSum += getCardValue(userCardArray[i]);
            }
        }
        else {
            for (int i = 0; i < dealerCardArray.length-1; i++) {
                if (dealerCardArray[i] == 0) {
                    break;
                }
                if (dealerCardArray[i] == 14) { //Ace catch 
                    dealerCardSum += 11;
                    dealerAce++;
                    continue;
                }
                dealerCardSum += getCardValue(dealerCardArray[i]);
            }

        }
        return 1;
    }

    /**
     * @param u (char)
     * Checks to see if the player/dealer has an ace, if so subtracts 10 if they exceed 21
     */
    public static void checkAce(char u) {
        if (u == 'u') {
            if (userCardSum > 21) {
                if (userAce > 0) {
                    userCardSum -= 10;
                    userAce--;
                    checkAce(u);
                }
            }
        }
        else {
            if (dealerCardSum > 21) {
                if (dealerAce > 0) {
                    System.out.println("User acesasda: " + userAce);
                    dealerCardSum -= 10;
                    dealerAce--;
                    checkAce(u);
                }
            }
        }

    }

    /**
     * Starts the game of BlackJack using class functions
     */
    public static void playBlackJack() {
        placeBet();

        int dealerCard1 = drawRandomCard(), dealerCard2 = drawRandomCard();
        int playerCard1 = drawRandomCard(), playerCard2 = drawRandomCard();

        addCardToDeck(playerCard1, 'u');
        addCardToDeck(playerCard2, 'u');

        addCardToDeck(dealerCard1, 'd');
        addCardToDeck(dealerCard2, 'd');

        addCardToString(playerCard1, 'u');
        addCardToString(playerCard2, 'u');
        addCardToString(dealerCard1, 'd');
        addCardToString(dealerCard2, 'd');

        getSum('u');
        getSum('d');

        checkAce('u');
        System.out.println("\nYou were dealt: \n" + twoCardsString(playerCard1, playerCard2) + "\nfor a total of: " + userCardSum + "\n");
        System.out.println("The dealer was dealt: \n" + twoCardsString(dealerCard1, 15) + "\nfor a hidden total: ?\n");

        testUserLuck();
        testDealerLuck();

        System.out.println(determineWinner());
        
        newGame();
    }



    public static void main(String[] args) {
        playBlackJack();
    }
}
