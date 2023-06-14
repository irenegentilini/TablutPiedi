# TablutPiedi
 TablutPiedi is a program that plays the game tablut in the ashton variant.
 
# Command line
 To launch the game, after running the server, run from the folder "Tablut" the following command:
 ``` 
 piedino.sh <player> [timeout] [ip-address] [debug]
 ```
 * player = either "white" or "black" which defines the player you're playing with
 * timeout = the time the player has to think about the next move
 * ip-address = the ip-address of the server
 * debug = literally the string "debug" which enables the verbose output
 
 alternatively you can launch the server and the players using the ants with the following commands:
 ``` 
 ant clean
 ant compile
 ant server
 ant piedinoblack
 ant piedinowhite 
 ```
 
# Eclipse
 Simply import the project into eclipse.
 You can then run the project simply running the files, or using the provided ant.
 If you're using the ants make sure to run ``` compile ```, ``` server ```, and then the chosen players.

# Challenge
This project was used to participate to the Ai@Unibo's Tablut Students Challenge.
This project, piedino, won in a tie with another project, as seen in the results website: http://ai.unibo.it/node/621
