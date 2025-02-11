# Build Your Own World Design Document

**Partner 1:** Phoebe Troup-Galligan 

**Partner 2:** Olivia McCauley 

## Classes and Data Structures
    LOCATION Class: X / Y Location on the grid
    WORLD Class: Class that holds objects necessary for world creation
        - List of rooms 
        - List of Hallways
        - Weighted Quick Union of Locations
        - List of all Walking locations
        - List of Edge locations 
    
    MENU CLASS: 
        CarrotInspoScreen(), displays an inspiring carrot picture 
        DrawMenu(), uses StdDraw to draw the main menu canvas. 
        DrawPromptSeed(), uses StdDraw to draw the seed prompt page with no seed displayed. 
        DrawPromptWSeed(), uses StdDraw to draw the seed prompt page with the partially written seed displayed. 
        DrawQuitScreen(), uses StdDraw to show quitting screen.
        InvalidSeed() uses StdDraw to give the user feedback that their seed is wrong. 
        Some other random irrelevant helper methods. 
    
    MenuListen Class: 
        Instance variables: (default values) 
        max = Long.parseLong("9223372036854775807");
        listening = true;
        listeningToChoice = true;
        listeningToSeed = false;
        stringSeed = "";
        validInts = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Methods: 
        menuListen: constantly running in Engine, uses listeningToChoice vs. ListeningToSeed to determine whether a main menu choice should be displayed (which would call displayChoice), or whether the program should run the interactive DrawPromptSeed/DrawPromptWSeed.
        displayChoice(): navigates between different main menu screens:
            N → Prompt Seed
            Q → Quit Screen → System.exit.(0)
            B → Main menu 
            I → carrot inspo 
            L → load saved game, not implemented yet. 
        manageSeeding()

       
    WALKABLE INTERFACE: 
        - Create method
        - getOccupied method
        - getEdges method
    
    ROOM Class: Stores locations of rooms
        - Height 
        - Width 
        - Starting point 
        - Edges List of locations
        
    Renderer Class: 
        - Takes in a world and the dimentions 
        - Sets TETile to its proper state. 
        
    Wall Class: 
        - Represents locations surrounding walkable ojects
        
    Verify Class: 
        - Takes in a Room object, the dimensions of the world, and a list of walkable
        locations, and verifies if the room's placement is valid.
            - For a room's location to constitute a "valid" placement, the room 
            must be completely inside the world, may not overlap with other rooms
            and must be 2 units away from all other walkable locations/rooms 
            (this is to support wall building) 
            
    Being Interface: 
        - Represents a being.  
        - Has a current location
        - Can have their current location move / down / left / right 
        - Can teleport
    
    Player Class : 
        - A being that represents our main character
        
    Listener class: 
        - Listens to key presses 
        - Listens for WASD key movements
        VerifyMovement private class: 
            - Verifies that player can move to desired location on the board
            
    Collectable Class: 
        - Represents an object that a player can collect 
        - has a value/worth 
        - has a location 
    
    Duck Class:     
        - Represents a being that is the bad guy.  Can move around on the board
        and teleport. 
            
## To Do:         
    Classes:
        - Reload world 
        

## Algorithms

    # AQUIRE USER IMPUT #:
    STEP 1: Store the input string : N#S
    STEP 2: Use this string to create a seed / Random number generator 
    
    # Create a World #
    STEP 1: Create an empty world grid filled with "LOCATIONS". The world must take in the randomizer.  
    STEP 2: Generate a random number of ROOMS. 
        - For each room, randomly pick: height, width, starting point(BOTTOM LEFT CORNER)
        - Verify that the rooms don't overlap or go off the grid
        - For each room, store the "edges" in a separate array
    STEP 3: Store the rooms in a List<Rooms>
    STEP 4: Create a weighted quick union that stores LOCATIONS.
    STEP 5: Connect Hallways to the rooms (Hallways are lists of locations)
        - While the rooms are not all connected: 
            - STEP A: Randomly select 2 rooms. Select 1 location from each room. 
            - STEP B: Create a hallway between those 2 rooms. 
            - STEP C: Calculate the "lower room"
                - Using this calculation, decide if we are going RIGHT UP or LEFT UP, STRAIGHT UP or STRAIT DOWN
                    - UNTIL: the room has been reached, go horizontal OR vertical. 
                    - IF: we reach the right x location, go VERTICAL until a destination is reached.
                    - IF: we reach the right y location, go HORIZONTAL until a destination is reached.
                    - FOR EACH NEW LOCATION:
                        - Add location to hallway List
                        - Add location to weighted quick union
    STEP 6: Store a list of all locations that are either hallways or rooms. Call this walking list. 
    STEP 7: Create a empty list of "Locations" for walls.
    - For every filled "location", every surrounding tile that is not a "filled location", make a wall location
    
    # Display the world #
    STEP 1: Create a world of equal size to the renderer.  
    STEP 2: For each Location in the walking list, set TETILE to something. 
    STEP 3: For each Location in the edge list, set tile to something different.  
    STEP 4: For every tile that is not one of these, set the tile to an empty tile. 
    STEP 5: Display the World. 

## Persistence


