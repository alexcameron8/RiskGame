# Risk: Global Domination
Risk based game developed as an educational group project for SYSC3110.

Group 2 members include: Thomas Dunnigan, Alex Cameron, Benjamin Munro

## User Manual
The user manual is included with our submission on cuLearn but can also be found on Google Drive:
[User Manual](https://drive.google.com/file/d/1ZyVn-hjSJK7hDP9uS90wCvZ496TQ_Wyi/view?usp=sharing)

## Milestone 1
### Authors:
Alex Cameron
- Implemented design of player Turns
- Worked on designing Attacking between 2 players and random simulation of attack
- Implemented commands to place troops in territories
- Designed methods to determine if reinforcements can be placed in Player class.
- Added UML Diagrams entries for map related classes. 
- Created sequence diagrams.

Benjamin Munro
- Implemented command validation and processing (CommandProcessors and ValidCommands)
- Wrote JavaDocs for Classes relating to Commands
- Wrote user-manual
- Added UML Diagram entries for classes relating to commands and command processing

Thomas Dunnigan
- Implemented design of Territory, Continents, World
- Designed a Default World Map
- Worked on Player class adding and removing Territories and Continents
- Worked on designing Attacking between 2 players
- Added Command to view the entire Map
- Added ability to skip elimited players and display winner
- Added UML Diagram entries for classes relating to Attack and Player, and Command and Parser

### Description of the choice of data structures and relevant operations:
Hashmaps were used for the Territories so the Territory can be found using its name easily. 
As well, Arraylists were used to store lists of players in a game, number of territories and continents a player has because these lists should be regularly changed in size throughout the game and this can be easily done through this data structure. 

### Known Issues
Several of these issues will be fixed in later Milestones as the player who is playing the game of Risk will not be typing in console the commands to play the game.
Our known issues can be found at: https://github.com/computebender/Risk/issues
- When either placing or attacking with a 2 name country, the country must be put in quotations. E.g. “South Africa”
- When attacking or placing troops, if the user inputs a string as the number of troops to be placed/ used in the attack the command will fail because it only accepts int values.

## Milestone 2
### Authors:
Alex Cameron
- Designed Action Bar Controller, ActionBarModel, ActionBarView classes which is where attacking, placing troops and skipping to next turn happens.
- Designed MenuBar and Controller functionalities
- Wrote JavaDocs
Thomas Dunnigan

Ben Munro

### Changes Made to UML and Data Structures from Milestone 1
The major change in design from Milestone 1 to Milestone 2 was changing the user interface to a graphical interface where the user uses the mouse rather than a command based game that was used in the previous milestone. For milestone 2 we changed the design of territories to know which players own them, what continent they are apart of and gave them shapes and corresponding colours to player colours. As well, players now have colours and when an attack occurs it now returns a boolean value of true if successful and false otherwise. The way the map is created was changed from Milestone 1 to now import a JSON file with the data for the map. 
Note: *For markers convenience* a test menu item is in the game to transfer all territories to another player to test eliminating players/ speed the game up.    

### Known Issues
- Upon game setup if the user attempts to close any of the frames the game will continue to open and ignore the user hitting the cancel button in the top right corner.
- If the user tries to play the game not full screen there is potential for the message bar to not appear on screen because the text is too long to fit in the component.
- In our testing, if using mac or linux the JComboBox does not work to choose the player colour in the setup phase.

## Roadmap
### Milestone 3
Met by: Monday, 23 November 2020
- Additional features: bonus army placement + troupe movement phase.  
- Implement support for “AI” players.

### Milestone 4
Met by: Monday, 7 December 2020
- Save/Load feature
- Use custom maps in JSON or XML format



