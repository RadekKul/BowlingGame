package pl.rkul.Bowling;

import java.util.Scanner;

public class BowlingGame {

    private int rollNumber = 0;
    private int[] ballRolls = new int[21];    // every frame has 2 rolls, the last (10th) can have 3 rolls in special case, maximum 21 rolls (10*2 + 1 in 10th in special case)
    private Scanner scanner;
    private int pinsNumberToValidate;
    private int movesValidator = 1;
    private int frame = 1;

    public void start() {

        System.out.println("Welcome in Bowling Game");

        while (frame < 11) {

            scanner = new Scanner(System.in);
            System.out.println("\nChoose option 1 or 2 by pressing right number \n1.PLAY \n2.CHECK SCORE");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                System.out.println("\nWrite the number of pins taken down in roll: ");


                if (scanner.hasNextInt()) {
                    pinsNumberToValidate = scanner.nextInt();

                    if (frame != 10) {

                        if (pinsInOneRollValidator(pinsNumberToValidate) && sumOfPinsInOneFrameValidator(getRollNumber(), pinsNumberToValidate)) {
                            roll(pinsNumberToValidate);
                            calculateFrames(getRollNumber(), pinsNumberToValidate);
                        } else {
                            System.out.println("Wrong data has been entered");
                        }
                    } else { //10th frame
                        if (pinsInOneRollValidator(pinsNumberToValidate) && sumOfPinsIn10thFrameValidator(getRollNumber(), pinsNumberToValidate)) {
                            roll(pinsNumberToValidate);
                            calculateFrames(getRollNumber(), pinsNumberToValidate);
                        } else {
                            System.out.println("Wrong data has been entered");
                        }

                    }
                }

            } else if (option.equals("2")) {
                System.out.println("\nCurrent score: " + calculateScore());
            } else {
                System.out.println("\nYou didn't choose any option. Try again");
            }

        }
        System.out.println("Your score: " + calculateScore());
    }


    public void roll(int pins) {

        ballRolls[rollNumber] = pins;   // save the number of pins taken down in every roll to use it in strike/spare
        rollNumber++;                   // after each roll, increment rollNumber
    }

    public int calculateScore() {
        int score = 0;
        int currentRollIndex = 0;

        for (int currentFrame = 1; currentFrame <= 10; currentFrame++) {
            if (isStrike(currentRollIndex)) {
                score += 10 + strikeBonusPoints(currentRollIndex);
                currentRollIndex += 1;
            } else if (isSpare(currentRollIndex)) {
                score += 10 + spareBonusPoints(currentRollIndex);
                currentRollIndex += 2;
            } else {
                score += sumOfPinsTakenDownInFrame(currentRollIndex);
                currentRollIndex += 2; // in sum we count 2 rolls, if only one roll was made, it counts second as 0.
            }
        }
        return score;
    }

    public boolean isSpare(int rollIndex) {
        return ballRolls[rollIndex] + ballRolls[rollIndex + 1] == 10;
    }

    public boolean isStrike(int rollIndex) {
        return ballRolls[rollIndex] == 10;
    }

    public int sumOfPinsTakenDownInFrame(int rollIndex) {
        return ballRolls[rollIndex] + ballRolls[rollIndex + 1];
    }

    public int spareBonusPoints(int rollIndex) {
        return ballRolls[rollIndex + 2];
    }

    public int strikeBonusPoints(int rollIndex) {
        return ballRolls[rollIndex + 1] + ballRolls[rollIndex + 2];
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public boolean pinsInOneRollValidator(int pins) {
        if (pins > 10 || pins < 0) {
            System.out.println("You've entered wrong number of pins taken down. Try again");
            return false;
        }
        return true;
    }

    public boolean sumOfPinsInOneFrameValidator(int rollNumber, int pins) {

        if (movesValidator == 2) {
            if (pins + ballRolls[rollNumber - 1] > 10) {
                System.out.println("You've entered wrong number of pins (more than 10 pins taken down in one frame)");
                return false;
            }
        }
        return true;
    }

    // validation for 10th frame
    private boolean sumOfPinsIn10thFrameValidator(int rollNumber, int pins) {
        if (movesValidator == 2) {
            if ((pins + ballRolls[rollNumber - 1] > 10) && !isStrike(rollNumber - 1)) {
                System.out.println("You've entered wrong number of pins (more than 10 pins taken down in one frame)");
                return false;
            }
        } else if (movesValidator == 3) {
            if ((pins + ballRolls[rollNumber - 1] > 10) && !isStrike(rollNumber - 1) && !isSpare(rollNumber - 2)) {
                System.out.println("You've entered wrong number of pins (more than 10 pins taken down in one frame)");
                return false;
            }
        }
        return true;
    }

    public void calculateFrames(int rollNumber, int pins) {

        if (frame != 10) {
            if (pins == 10 || movesValidator == 2) {
                frame++;
                System.out.println("\nLet's start frame number " + frame);
                movesValidator = 1;
            } else {
                movesValidator++;
                System.out.println("\nLet's start next roll");
            }
        } else { // calculation for 10th frame
            if (movesValidator == 1) {
                movesValidator++;
                System.out.println("\nLet's start next roll");
            } else if (movesValidator == 2) {
                if ((pins == 10 && isStrike(ballRolls[rollNumber - 1]) || isSpare(rollNumber-2))) {
                    movesValidator++;
                    System.out.println("\nLet's start next roll");
                } else {
                    frame++;
                    System.out.println("\nThis is the end of game");
                }
            } else {
                frame++;
                System.out.println("\nThis is the end of game");
            }
        }
    }
}
