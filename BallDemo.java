import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Michael Kölling and David J. Barnes
 * @author Kevin Cox-Torrelio
 * 
 * MODIFIED 10/24/2026
 * 
 * @version 2016.02.29
 */

public class BallDemo   
{
    private Canvas myCanvas;
    private Box box;
    private Random randomNumGen;
    private ArrayList<BoxBall> generatedBalls;

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible. Sets up random number generator and collection for generated balls.
     * 
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
        box=new Box (100,100,500,400, myCanvas);
        box.draw();
        
        Box box2 = new Box(myCanvas);
        box2.draw();
        
        randomNumGen = new Random();
        generatedBalls = new ArrayList();
        
    }

    /**
     * boxBounce - simulate 5-50 balls bouncing within a box
     * 
     * 
     * 
     * @param numOfBalls number of balls to simulate bouncing, clamped between 5-50. 
     */
    public void boxBounce(int numOfBalls)
    {
        
        
        numOfBalls = Math.clamp(numOfBalls, 5, 50);
        int iterationsDone = 0;
        
                
        while(generatedBalls.size() < numOfBalls){
            
            // positional randomness on the canvas
            int randomCanvasXPos = randomNumGen.nextInt((box.getLeftWall() + 50), (box.getRightWall() - 50));
            int randomCanvasYPos = randomNumGen.nextInt((box.getTopWall() + 50), (box.getBottomWall() - 50));
            
            int randomXSpeed = randomNumGen.nextInt(5, 12);
            int randomYSpeed = randomNumGen.nextInt(5, 12);
            
            // add some randomness to the direction
            if(randomNumGen.nextInt(2) > 0) {
                randomXSpeed = -randomXSpeed;
            }
            
            if(randomNumGen.nextInt(2) > 0) {
                randomYSpeed = -randomYSpeed;
            }
            
            
            int red = randomNumGen.nextInt(50, 200);
            int blue = randomNumGen.nextInt(50, 200);
            int green = randomNumGen.nextInt(50, 200);
            
            
            Color randomColor = new Color(red, blue, green);
            
            BoxBall ball = new BoxBall(randomCanvasXPos, randomCanvasYPos, 16, randomColor, box, myCanvas, randomXSpeed, randomYSpeed);
            ball.draw();
            generatedBalls.add(ball);
        }
        
        while (iterationsDone <= 500) {
            myCanvas.wait(10);           // small delay
            
            for(BoxBall ball : generatedBalls){
                ball.move();
            }
            
            iterationsDone++;
        }
        
    }
    
    /**
     * Simulate two bouncing balls
     */
    public void bounce()
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.setForegroundColor(Color.BLACK);
        myCanvas.drawLine(50, ground, 550, ground);

        // create and show the balls
        BouncingBall ball = new BouncingBall(50, 50, 16, Color.BLUE, ground, myCanvas);
        ball.draw();
        BouncingBall ball2 = new BouncingBall(70, 80, 20, Color.RED, ground, myCanvas);
        ball2.draw();

        // make them bounce
        boolean finished =  false;
        while (!finished) {
            myCanvas.wait(50);           // small delay
            ball.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
}
