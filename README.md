# Build Your Own World-Carrot Hunters

By: Phoebe Troup-Galligan and Olivia McCauley 

## Overview
Carrot Hunters is a world-building game where players explore a procedurally generated environment, collect items, and interact with various elements in the game world. The world is constructed using rooms, hallways, and walkable areas, with additional mechanics for player movement, interactions, and obstacles.
For a detailed running of the program, watch: https://www.youtube.com/watch?v=2IRYzywC2kc

## Running the Code: 
1. Clone the Repository
2. Compile the code:

       javac -d bin src/*.java
4. Run the Application

       javac -d bin src/*.java
## Usage
- Generate a World: Enter a seed value to generate a new world layout.
- Navigate the Menu: Use keyboard inputs to start, load, or quit the game.
- Move the Player: Use WASD keys to move around the world.
  
## Features
- Randomized World Generation: Uses a seed to generate a unique world layout with rooms and hallways.
- Hallway Connectivity: Implements a weighted quick union to ensure all rooms are connected.
- Interactive Menu System: Allows users to input a seed, view an inspiring carrot image, or quit the program.
- Character Movement: Player can move within the world, ensuring valid movements.
- Collectable Objects & Enemies: Includes interactive elements such as collectable items and moving enemies.

## Technologies Used
- Java: Primary programming language.
- StdDraw: Used for rendering the world and menu screens.
- Weighted Quick Union: Used for managing connectivity between rooms and hallways.
- Data Structures: Lists, sets, and maps for managing world entities.

## Project Structure: 
|-- src
|   |-- Location.java        # Represents a coordinate in the world grid
|   |-- World.java           # Manages world generation and connectivity
|   |-- Room.java            # Defines rooms within the world
|   |-- Menu.java            # Handles UI interactions and seed input
|   |-- MenuListen.java      # Listens for user inputs in the menu
|   |-- Renderer.java        # Draws the world using StdDraw
|   |-- Player.java          # Represents the player character
|   |-- Duck.java            # Represents an enemy NPC
|   |-- Collectable.java     # Represents items the player can collect
|   |-- Listener.java        # Handles user input for movement
|   |-- Verify.java          # Ensures room placement validity
|-- bin                     # Compiled files
|-- data                    # Saved game data
|-- README.md               # Documentation

