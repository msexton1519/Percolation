# Background
Percolation requires algs4.jar from Princeton University to run properly due to the use of its QuickUnion data
structure. Also, Princeton University's PercolationViewer class to view the results. 
The implementation of the Percolation class and PercolationStats classes are my own and make use of the Union
data structure in order to solve this particular dynamic connectivity problem. 
# Percolation
The Percolation class keeps track of a grid and how each site is connected. If the top row connects to the bottom row in
some way, the grid is said to percolate. 
For example, when using the viewer:
![Alt Text](https://github.com/msexton1519/Percolation/blob/main/image1-ANIMATION%20(1).gif)
# Peroclation Stats
This class is run with two command line arguments where the first argument is the length of the grid to be constructed, 
size = length * length, and the second argument is the number of trials run with a grid size of length * length.
A confidence interval is constructred where the percolation value lies within the low end and high end with 95% accuracy.
Note: there is no definitive percolation value for any grid, thus, the value must be estimated.
# Author
Matt Sexton
