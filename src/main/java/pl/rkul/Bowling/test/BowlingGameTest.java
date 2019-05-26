package pl.rkul.Bowling.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.rkul.Bowling.BowlingGame;


public class BowlingGameTest {

    BowlingGame game1;

    @Before
    public void makeANewGameForTests(){
        game1 = new BowlingGame();     // I use it for other Tests instead of first one when I am testing making a new Game object.
    }

    @Test
    public void testGameStart(){
        BowlingGame game = new BowlingGame();
        for(int i=0; i<20; i++){ // there will be 20 rolls, 21 only with strike or scope in 21st roll
            game.roll(0);}
        Assert.assertEquals(0, game.calculateScore());
    }

    @Test
    public void AllRollsWithDefaultNumber(){ //I calculate for default 4 - pins taken down in each roll
        for(int i=0; i<20; i++){
            game1.roll(4);
        }
        Assert.assertEquals(80,game1.calculateScore());
    }

    private void defaultSpare(){
        game1.roll(2);
        game1.roll(8);
    }

    private void defaultStrike(){
        game1.roll(10);
    }

    @Test
    public void testSpare(){
        for(int i =0 ; i<16; i++){
            game1.roll(0);
        }
        defaultSpare();       // Checking spare in 17th and 18th roll
        game1.roll(5);  // I check if this one is doubled after (19th roll)
        game1.roll(0);  // 20th roll
        Assert.assertEquals(20,game1.calculateScore());
    }

    @Test
    public void testStrike(){
        for(int i =0 ; i<16; i++){
            game1.roll(0);
        }
        defaultStrike();    // 17th roll
        game1.roll(2); // 18th
        game1.roll(5); // 19th
        // there is no 20th roll cause it was one strike not in 10th frame
        Assert.assertEquals(24,game1.calculateScore());
    }

    @Test
    public void perfectGame(){  // there is 12 rolls in perfect game 10 + 2 extra in 10th frame
        for(int i=0 ; i<12; i++){
            defaultStrike();
        }
        Assert.assertEquals(300,game1.calculateScore());
    }

    @Test
    public void spareInLast(){
        for(int i=0 ; i<18; i++){
            game1.roll(0);
        }
        defaultSpare(); // 19th and 20th
        game1.roll(3);
        Assert.assertEquals(13,game1.calculateScore());
    }

    @Test
    public void strikeInLastFrameFirstRoll(){
        for(int i=0 ; i<18; i++){
            game1.roll(0);
        }
        defaultStrike(); // 19th
        game1.roll(3);
        game1.roll(3);
        Assert.assertEquals(16,game1.calculateScore());
    }
}

