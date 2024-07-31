
# 2D World Simulation

This program is a 2D world simulation developed using object-oriented programming (OOP) principles in Java. It provides an interactive interface where entities such as creatures and obstacles interact in a simulated environment.

## Key Features

### Object-Oriented Design
The program is divided into classes representing different elements of the simulation, such as the world map (WorldMap), entities (Entity, Creature, Herbivore, Predator), coordinates (Coordinates), and various utility classes.

### Graphical Interface
The simulation is displayed in a graphical window, where entities move and interact with each other. The world map is rendered using custom images for different entity types.

### Entity Interaction
Entities in the simulation move, interact, and follow specific behaviors defined in their respective classes. For example, predators hunt herbivores, and herbivores seek food.

### Pathfinding
The program implements pathfinding algorithms to allow entities to navigate the world map and reach their targets efficiently.

## Installation and Launch

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/simulation_of_2d_world.git
   ```

2. Navigate to the project directory:
   ```sh
   cd simulation_of_2d_world
   ```

3. Build the project using Maven:
   ```sh
   mvn clean install
   ```

4. Launch the project:
   ```sh
   java -jar target/simulation_of_2d_world-1.0.jar
   ```

## Usage
After starting the application, a window with the simulation will open. The window will display a world map with creatures and obstacles. Creatures will automatically move and interact with each other.

## Testing
To run the tests, execute the following command:
```sh
mvn test
```

## Documentation
Generated documentation can be found in the `doc` folder. To regenerate the documentation, run the following command:
```sh
javadoc -d doc -sourcepath src/main/java -subpackages com.toropov.oleg
```
