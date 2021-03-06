# PathFinding-Team

~~Major refactoring comming up, applying SOLID's Single responsibility principle.~~ DONE

Pathfinding AI implemented with the A* algorithm.

Version 1.0

## Introduction
An electrical grid maintenance team has to find its way to the location of the malfuntionnning cable to repair it and thus, restore the electrical system to the neighbourhood.

## Constraints
- The interrupter of the electric power source has to be turned off before repairing the broken cable, or the team gets electrocuted!
- To get the power running again to the neighbourhood, the team has to go back to the interruptor and switch it back on.
- Version 1.0 supports a maximum of one interrupter and a single damage point.

## Program input
The program takes a text file as input. The file must contain a set criteria of caracters.

- '*' : maintenance team starting position
- 'I' : open interrupter
- 'J' : closed interrupter
- 'B' : location of broken cable
- '#' : obstacle
- 'M' : house
- 'C' : cable
- 'S' : electric power source

## Program output
The program outputs the directions, step by step, on the console. Directions and actions are denoted by a set of definite caracters.

- '0' : the team sets the interrupter to OFF
- '1' : the team sets the interrupter to ON
- 'R' : The cable is repaired
- 'N' : North
- 'S' : South
- 'E' : Est
- 'W' : West

## Run the program
To run the program on a console
</br>

Navigate to the project directory.

`javac *.java`</br>
`java Driver <testFile.txt>`

For test file examples, refer to test1.txt and text2.txt in the repository.

## Example

Input : test1.txt

Output : N E E E N N W W W N 0 E E E E E R W W W W W 1

Input : test2.txt

Output : N N E E E E N N W W W W N 0 E E E E E R W W W W W 1





