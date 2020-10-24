# ThreeInARow
This is a basic Java implementation of the Three in a Row game.

The way I have done the coding, debugging, compiling, running and testing is by installing eclipse. Install JDK, JRE (version 14.x.x) and make sure you are able to run a java program.

1. Make sure that you have eclipse installed.

2. Right click on `RowGameApp.java` and then hover-over/click-on the `run as` text, and then click on `Java Application`. This should start our terminal.

3. On the Terminal, you should see `Enter number of Rows: `. Please enter any natural number representing the number of rows in the grid and press enter.

4. On the Terminal, you should see `Enter number of Columns: `. Please enter any natural number representing the number of columns in the grid and press enter.

5. On the Terminal, you should see `Enter 1 to play ThreeInARow or any other number to play TicTacToe: `. Please enter any natural number representing whether you want to play ThreeInARow or TicTacToe. So, enter 1 to play ThreeInARow, or enter 2 to play TicTacToe.

6. Note, that if the number of rows and columns are both less than 3 and greater than 0, then you will get `Game ends in a draw!` on the terminal. Else, you should see the UI Grid popup with the same number of rows and columns as entered earlier.

7. Testing can be done combinely or individually. To test combinely, just right click on the (`threeinarow/test`) or (`/test` folder inside `threeinarow`). Again hover/click on `run as` and choose `JUnit Test`. To test individually, right click on the correspoonding test file (Ex: modelTests.java), hover/click on `run as` and choose `JUnit Test`.
