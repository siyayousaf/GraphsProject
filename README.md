## Graph Data Structure Project (Java)

## Overview
This project implements a graph data structure in Java using an adjacency list representation. The program supports both directed and undirected graphs and allows users to interact with the graph through a command-line interface (CLI).
The goal of this project is to demonstrate understanding of:

## Graph representations
Traversal algorithms (BFS, DFS)
Shortest path algorithms in unweighted graphs
Basic graph operations (adding/removing vertices and edges)
Cycle detection in directed graphs
Features
Create a directed or undirected graph
Add and remove vertices
Add and remove edges
Print the graph structure
Breadth-First Search (BFS) traversal
Depth-First Search (DFS) traversal
Shortest path between two vertices (unweighted, BFS-based)
Directed cycle detection
Interactive menu-based interface

## Data Structure Used
Adjacency List
Implemented using:
HashMap<String, LinkedList<String>>
Efficient for sparse graphs
Allows dynamic addition and removal of vertices and edges
Algorithms Implemented
Breadth-First Search (BFS)
Uses a queue
Explores vertices level by level
Depth-First Search (DFS)
Uses recursion
Explores as deep as possible before backtracking
Shortest Path (Unweighted Graph)
Uses BFS with parent tracking
Reconstructs the actual path
Cycle Detection (Directed Graph)
Uses DFS with node coloring (unvisited, visiting, visited)

## How to Compile and Run
Make sure you are in the directory containing GraphProject.java.
javac GraphProject.java
java GraphProject

## How to Use the Program
When prompted:
Create directed graph? (y/n):
Enter y for directed
Enter n for undirected
Use the menu to interact with the graph:
1) Add vertex
2) Add edge
3) Remove vertex
4) Remove edge
5) Print graph
6) BFS traversal
7) DFS traversal
8) Shortest path
9) Check directed cycle
0) Exit
Follow on-screen prompts to enter vertex names and edges.

## Example Operations
Add vertices like A, B, C
Add edges such as A -> B
Run BFS or DFS starting from a chosen vertex
Find the shortest path between two vertices
Detect cycles in directed graphs

## Learning Outcomes
Through this project, I practiced:
Designing and implementing a graph from scratch
Applying BFS and DFS traversal algorithms
Using Java collections (HashMap, LinkedList, Queue)
Managing user input with a menu-based interface
Debugging and testing graph behavior
