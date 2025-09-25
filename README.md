# Minesweeper Game

**Students**: - Clarisse, Eryk, and Krzysztof

**Coursework**: PIS 2 (Pratical)


## Project Overview

Mine Quest is an interactive mining adventure game built using JavaFX. Players navigate a 2D grid of destructible blocks, mining for resources and finding paths to reach the ultimate goal of opening a chest by collecting three different keys, which are hidden across various levels.

The game features character selection and item upgrades, as well as strategic pathfinding, where the available paths depend on the player's tools and their levels. To succeed, players must mine blocks efficiently, avoid hazards, and discover the fastest routes.

## Features

### Core Gameplay 

- [ ] **Grid System & Mining**
  - [X] Implement cell class with different block types
  - [X] Implement tile-based 2D Map 
  - [ ] Handle left-click mining mechanics
  - [ ] Block destruction animations and effects

- [ ] **Resource & Key Management**
  - [ ] Randomly place minerals and resources
  - [ ] Randomly place three unique keys across levels
  - [ ] Implement inventory system for collected items

- [ ] **Character System**
  - [ ] Implement movement and controls
  - [ ] Implement multiple playable characters
  - [ ] Character-specific items and abilities
  - [ ] Automated enemy characters (mobs)

### User Interface 

- [ ] Window Management
  - [ ] Start screen with game introduction
  - [ ] Character selection window
  - [ ] Level selection interface
  - [ ] Main game window with HUD

- [ ] Game Interface
  - [ ] Menu bar with game options
  - [ ] Store panel for item upgrades
  - [ ] Health and oxygen indicators
  - [ ] Inventory display for keys and resources

### Game Mechanics

- [ ] Survival Elements
  - [ ] Oxygen depletion system
  - [ ] Health management
  - [ ] Hazard detection (mobs, traps)

- [ ] Progression System
  - [ ] Tool upgrading mechanics
  - [ ] Path unlocking based on tool levels
  - [ ] Level completion conditions

### Game Modes & Settings

- [ ] Difficulty Settings
  - [ ] Adjustable mine density (Easy, Medium, Hard)
  - [ ] Limited oxygen mode toggle
  - [ ] Time attack mode

- [ ] Quality of Life Features
  - [ ] Hint system (reveal one clue)
  - [ ] Path validation check
  - [ ] Visual bomb count indicator


## Installation

**Prerequisites**
- Java Development Kit (JDK)
- JavaFX SDK
- Eclipse IDE or IntelliJ IDEA

**Project Setup (GIT)**
1. Open Eclipse and go to `File > Import...`.
2. Select `Git > Projects from Git` and click `Next`.
3. Choose `Clone URI` and click `Next`.
4. Enter the repository URI:  
   `https://github.com/urbanKR/mine-quest.git`  
   then click `Next` and `Finish`.
   
**Project Setup (ZIP)**
1. Download and Extract the ZIP file
2. Open Eclipse and go to `File > Import...`.
3. Select `General > Existing Projects into Workspace` and click `Next`.
4. Click `Browse` and locate the folder where you extracted the ZIP.
5. Ensure the project apppears in the `Projects`, then click `Finish`

**Configuring JavaFX**
- Add the JavaFX `lib` folder to your project’s build path  
  (`Project > Properties > Java Build Path > Add External JARs`).
- Create a Run Configuration for the project (`Run > Run Configurations...`).  
  Set `Main.java` as the main class.
- In the VM arguments field, add:  
  `--add-modules=javafx.controls,javafx.fxml`

**Running the Project**
- After importing and configuring JavaFX, run the project directly from Eclipse.
- The start window of the Miner's Quest game will launch automatically.


## Usage
1. Ensure all dependencies are properly configured
2. Locate Main.java in src/main/java/application/
3. Right-click → Run As → Java Application
4. The game start window should launch automatically

## How to Play

### Basic Controls
- Left-click: Mine blocks/interact with objects
- Character Movement: Click on adjacent blocks to move
- Inventory Management: Use collected resources to upgrade tools

### Game Objectives
- Explore the Mine: Navigate through destructible blocks
- Collect Resources: Mine minerals to upgrade your tools
- Find Keys: Locate three unique keys hidden across different levels
- Open the Chest: Use all three keys to unlock the final chest
- Survive: Manage your oxygen and health while avoiding hazards


## Architecture & Implementation
- **Front-end:** JavaFX with FXML for UI layout
  **Game Engine:**  Custom-build game loop, with 2D array-based maze 
  **Key Classes:**
  - `Main.java` - Application entry point, JavaFX initialization
  - `Controller.java` - Main game logic and event handling
  - `Grid.java` - Maze generation and grid management
  - `Cell.java` - Individual block properties and behaviors
  - `Miner.java` - Player character with stats and inventory
  - `Tool.java` - Upgradeable mining equipment

Mob.java - Enemy AI and behaviors


## Screenshots
TODO

## Future Work
TODO


## Development Team
- Clarisse Carvalho
- Eryk x
- Krzysztof Urbam

## License

