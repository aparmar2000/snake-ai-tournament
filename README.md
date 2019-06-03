Snake AI Tournament
======

This Java program is designed to compare various types of AI, all designed to play the game Snake.
All variables are currently set in the code, the important ones are these:
* stepLimit - The number of game steps each individual game is limited to.  Functions as a time limit, and is also used in the calculation of the poit value of collecting food.
* gridSize - The size of the game's grid. For example, if this was 10 the grid would be 10x10.
* trials - The number of games the AI is allowed to play.  Only the highest-score one will be kept.
* currentAI - The AI to be used for this set of trials.

The rules of the version of Snake implemented in this program are as follows:
* Each step the snake has three options:
  * Go forward
  * Turn left
  * Turn right
* One point is gained for every step the AI lives.
* Collecting food increases the length of the snake by one, and grants points equal to 1/10 of the step limit.
* There are three ways in which the snake can die and therefore end the current trial:
  * Run into a wall
  * Run into its own tail
  * Run out of time (the step limit)

The highest-scoring game is recorded, and once all trials have been completed it is rendered out as a series of .png files in the "results" folder.  
If this folder does not exist, it will be created, and if there are files already in it they will be erased.

There are six different AIs currently.  Their name and a brief overview follows (More detail on their functionality can be found on their respective wiki pages):
* ChaoticAI
  This AI makes decisions at random.
* ClaustrophobicAI
  This AI will turn toward the food if it has direct line-of-sight from the snake's head.
  Otherwise, it tries to stay away from obstacles (the outer wall and its own tail).
* AgoraphobicAI
  This AI will turn toward the food if it has direct line-of-sight from the snake's head.
  Otherwise, it tries to stay as close as possible to obstacles (the outer wall and its own tail).
* PeekAI
  This AI will turn toward the food if it has direct line-of-sight from the snake's head.
  Otherwise, it will only turn if it is about to run into something in the very next step, in which case it will turn in whichever direction has the most empty space.
* ShortcutAI
  This AI turns toward the adjacent grid space that is closest to the food.
* BestFirstAI
  This AI performs a Best-First Search to find a path to the food.