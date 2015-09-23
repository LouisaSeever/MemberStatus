package com.louisaseever.memberstatus;

import java.util.ArrayList;

/**
 * Created by LouisaSeever on 8/13/2015.
 */
public abstract class AssignmentDataSource {
    abstract public void addAssignmentCategory(String category) throws Exception;
    abstract public ArrayList<String> getAssignmentCategories() throws Exception;
    abstract public void addAssignment(String category, String description, int minPoints, int maxPoints) throws Exception;
    abstract public void addAssignment(Assignment assignment) throws Exception;
    abstract public void removeAssignment(int id) throws Exception;
    abstract public void updateAssignment(int id, String category, String description, int minPoints, int maxPoints) throws Exception;
    abstract public ArrayList<Assignment> getAssignments();
    abstract public Assignment getAssignment(int id);
    abstract public void clear() throws Exception;
    abstract public void save() throws Exception;
    abstract public void rebuild() throws Exception;
 //   abstract public String toString();
    abstract public void fromString(String  input)throws Exception ;

    public void load() throws Exception{
        //load categories
        loadCategories();
        
        //catchall
        addAssignment("Java-by doing","Custom", 0, 500);
        //basics
        addAssignment("Java-by doing","Installing he Java Development Kit", 20, 20);
        addAssignment("Java-by doing","Compiler Check", 3, 3);
        addAssignment("Java-by doing","Compiling Practice", 3,3);
        addAssignment("Java-by doing","An Important Message", 5,5);
        addAssignment("Java-by doing","A Good First Program", 4,4);
        addAssignment("Java-by doing","Comments and Slashes", 3,3);
        addAssignment("Java-by doing","A Letter to Yourself", 7,7);
        addAssignment("Java-by doing","Your Initials", 6,6);
        addAssignment("Java-by doing","Numbers and Math", 7,7);

        //variables
        addAssignment("Java-by doing","Variables and Names", 7,7);
        addAssignment("Java-by doing","More Variables and Printing", 7,7);
        addAssignment("Java-by doing","Using Variables", 9,9);
        addAssignment("Java-by doing","Still Using Variables", 10,10);
        addAssignment("Java-by doing","Your Schedule", 15,15);
        addAssignment("Java-by doing","Asking Questions", 10,10);
        addAssignment("Java-by doing","The Forgetful Machine", 12,12);
        addAssignment("Java-by doing","Name, Age and Salary", 15, 15);
        addAssignment("Java-by doing","More Use of Input Data", 16,16);
        addAssignment("Java-by doing","Age in Five Years", 18,18);
        addAssignment("Java-by doing","A Dumb Calculator", 20,20);
        addAssignment("Java-by doing","BMI Calculator", 15, 28);

        //if Statements
        addAssignment("Java-by doing","What If", 15,15);
        addAssignment("Java-by doing","How Old Are You", 25, 25);
        addAssignment("Java-by doing","Else And If", 20, 20);
        addAssignment("Java-by doing","Weekday Name", 15, 15);
        addAssignment("Java-by doing","How Old Are You, Specifically?", 20,20);
        addAssignment("Java-by doing","Space Boxing", 30,30);
        addAssignment("Java-by doing","A Little Quiz", 40,40);
        addAssignment("Java-by doing","Modulus Animation", 20,20);

        //GUIs
        addAssignment("Java-by doing","Using Swing for Input", 25, 25);
        addAssignment("Java-by doing","A Boring Window", 10,10);
        addAssignment("Java-by doing","A Frame with a Panel", 20, 20);
        addAssignment("Java-by doing","Twenty Questions", 35,35);
        addAssignment("Java-by doing","Choose Your Own Short Adventure", 60, 60);
        addAssignment("Java-by doing","Age Messages 3", 25, 25);
        addAssignment("Java-by doing","Two More Questions", 35, 35);
        addAssignment("Java-by doing","BMI Categories", 35, 45);
        addAssignment("Java-by doing","Gender Game", 50,50);
        addAssignment("Java-by doing","CompareTo() Challenge", 35, 35);
        addAssignment("Java-by doing","Alphbetical Order", 30,30);
        addAssignment("Java-by doing","The Worst Number-Guessing Game Ever", 15,15);

        //Random Numbers
        addAssignment("Java-by doing","Randomness", 15, 15);
        addAssignment("Java-by doing","Magic 8-Ball", 10,10);
        addAssignment("Java-by doing","A Number Guessing Game", 20, 20);
        addAssignment("Java-by doing","Fortune Cookie", 15, 25);
        addAssignment("Java-by doing","Dice", 25, 25);
        addAssignment("Java-by doing","One Shot Hi-Lo", 35, 35);
        addAssignment("Java-by doing","Three-Card Monte", 20, 20);

        //While Loops
        addAssignment("Java-by doing","Enter Your PIN", 15, 15);
        addAssignment("Java-by doing","Keep Guessing", 50, 50);
        addAssignment("Java-by doing","Dice Doubles", 45,45);
        addAssignment("Java-by doing","Counting with a While Loop", 30, 30);
        addAssignment("Java-by doing","PIN Lockout", 10,10);
        addAssignment("Java-by doing","NumberGuessing with a Counter", 40,40);
        addAssignment("Java-by doing","Hi-Lo with Limited Tries", 55,55);
        addAssignment("Java-by doing","Adding Values in a Loop", 30,30);

        //Do-While Loops
        addAssignment("Java-by doing","Do-While Swimming", 15,15);
        addAssignment("Java-by doing","Flip Again?", 20, 20);
        addAssignment("Java-by doing","Shorter Double Dice", 40,40);
        addAssignment("Java-by doing","Again with the Number-Guessing", 35, 35);
        addAssignment("Java-by doing","Safe Square Root", 55, 55);
        addAssignment("Java-by doing","Right Triangle Checker", 65,65);
        addAssignment("Java-by doing","Collatz Sequence", 30, 60);
        addAssignment("Java-by doing","Short Adventure2: With a Loop", 75, 75);
        addAssignment("Java-by doing","Baby Nim", 60, 60);
        addAssignment("Java-by doing","Nim", 70, 185);

        //For Loops
        addAssignment("Java-by doing","Counting with a For Loop", 10,10);
        addAssignment("Java-by doing","Ten Times", 15,15);
        addAssignment("Java-by doing","Counting Machine", 20, 20);
        addAssignment("Java-by doing","Counting Machine Revisited", 25, 25);
        addAssignment("Java-by doing","Counting by Halves", 20, 20);
        addAssignment("Java-by doing","Xs and Ys", 15, 15);
        addAssignment("Java-by doing","Noticing Even Numbers", 30, 30);
        addAssignment("Java-by doing","Fizz Buzz", 40, 40);
        addAssignment("Java-by doing","Letters at a Time", 20, 20);
        addAssignment("Java-by doing","For Loop Challenge", 40, 40);
        addAssignment("Java-by doing","Adding Values with a For Loop", 30, 30);
        addAssignment("Java-by doing","Baby Blackjack", 20, 20);

        //Projects
        addAssignment("Java-by doing","Blackjack", 80, 200);

        //Graphics
        addAssignment("Java-by doing","Graphics Demo 1: Basics", 20,20);
        addAssignment("Java-by doing","Graphics Demo 2: Arcs and Custom colors", 20,20);
        addAssignment("Java-by doing","Graphics Demo 3: Fonts and Lines", 15, 15);
        addAssignment("Java-by doing","A Circle", 20, 20);
        addAssignment("Java-by doing","A Smiling Face", 40, 40);
        addAssignment("Java-by doing","A Clock Face", 50, 70);
        addAssignment("Java-by doing","Graphing Lines", 40, 40);
        addAssignment("Java-by doing","Graphing Parabolas", 40, 40);
        addAssignment("Java-by doing","Graphics demo 4;  Polygons", 40, 40);
        addAssignment("Java-by doing","Drawing a Boring Triangle", 30, 30);
        addAssignment("Java-by doing","Drawing Random Triangles", 60,60);
        addAssignment("Java-by doing","Drawing Random Right Triangles", 80, 140);
        addAssignment("Java-by doing","Sierpinski Triangle", 150, 150);
        addAssignment("Java-by doing","Boxy 1", 50, 50);
        addAssignment("Java-by doing","Boxy 2", 40, 40);
        addAssignment("Java-by doing","Boxy 3", 60,60);
        addAssignment("Java-by doing","Smiling Face Function", 80, 80);
        addAssignment("Java-by doing","Forest and Trees", 150, 150);
        addAssignment("Java-by doing","Image Demo-Showing Images on the Screen", 20, 20);
        addAssignment("Java-by doing","Motivational Poster", 50, 80);

        //Functions
        addAssignment("Java-by doing","Picture Menu", 10, 10);
        addAssignment("Java-by doing","Flicker Phrase", 15, 15);
        addAssignment("Java-by doing","Heron's Formula", 15, 15);
        addAssignment("Java-by doing","Distance Formula", 50, 50);
        addAssignment("Java-by doing","Month Name", 60, 60);
        addAssignment("Java-by doing","Month Offset", 60, 60);
        addAssignment("Java-by doing","Weekday Calculator", 90, 90);
        addAssignment("Java-by doing","Area Calculator", 110, 110);
        addAssignment("Java-by doing","Function Call Alphabet", 90,90);
        addAssignment("Java-by doing","Fill-In Functions", 100, 100);
        addAssignment("Java-by doing","More fill-In Funcitons", 100, 100);
        addAssignment("Java-by doing","Keychains for Sale", 40, 40);
        addAssignment("Java-by doing","real this time", 65, 65);
        addAssignment("Java-by doing","Keychains for Sale, real ultimate power", 80, 80);
        addAssignment("Java-by doing","Calling Functions from Other Files", 40, 40);
        addAssignment("Java-by doing","Evenness Function", 60, 60);
        addAssignment("Java-by doing","Find Prime Numbers", 85, 85);
        addAssignment("Java-by doing","Baby Calculator", 20, 20);
        addAssignment("Java-by doing","Project:  Calculator", 80, 300);
        addAssignment("Java-by doing","A Refresher (For Loops)", 20, 20);
        addAssignment("Java-by doing","Refresher Challenge (For Loops)", 20, 20);
        addAssignment("Java-by doing","Displaying some Multiples", 30, 30);

        //Nested Loops
        addAssignment("Java-by doing","Nesting Loops", 20, 20);
        addAssignment("Java-by doing","Odometer Loops", 25, 25);
        addAssignment("Java-by doing","Basic Nested Loops", 50, 50);
        addAssignment("Java-by doing","Multiplication table", 65, 65);
        addAssignment("Java-by doing","Number Puzzle 1", 60, 60);
        addAssignment("Java-by doing","Getting Individual Digits", 80, 80);
        addAssignment("Java-by doing","More Number Puzzles", 100, 100);
        addAssignment("Java-by doing","Number Puzzles III: Armstrong Numbers", 90, 90);
        addAssignment("Java-by doing","Number Puzzles IV:  A New Hope", 90, 90);

        //File Input and Output
        addAssignment("Java-by doing","Letter Revisited", 40, 40);
        addAssignment("Java-by doing","High Score", 50, 50);
        addAssignment("Java-by doing","Simple File Input", 40, 40);
        addAssignment("Java-by doing","Summing Three Numbers from a File", 50, 50);
        addAssignment("Java-by doing","Summing Three Numbers from Any File", 65, 65);
        addAssignment("Java-by doing","Displaying a File", 60, 60);
        addAssignment("Java-by doing","Summing Several Numbers from any File", 60, 60);
        addAssignment("Java-by doing","Simple web Input", 15, 15);
        addAssignment("Java-by doing","A Little Puzzle", 75, 75);
        addAssignment("Java-by doing","Vowel Capitalization", 80, 80);

        //arrays
        addAssignment("Java-by doing","Basic Arrays", 20, 20);
        addAssignment("Java-by doing","Basic Arrays 1", 30, 30);
        addAssignment("Java-by doing","Basic Arrays 2", 40, 40);
        addAssignment("Java-by doing","Basic Arrays 3", 25, 25);
        addAssignment("Java-by doing","Copying arrays", 55, 55);
        addAssignment("Java-by doing","Grades in an Array and a File", 90, 90);
        addAssignment("Java-by doing","Finding a Value in an Array", 80, 80);
        addAssignment("Java-by doing","How Many Times", 40, 40);
        addAssignment("Java-by doing","Is It There or Not?", 85, 85);
        addAssignment("Java-by doing","Where Is It?", 90, 90);
        addAssignment("Java-by doing","Finding the Largest Value in an Array", 100, 100);
        addAssignment("Java-by doing","Locating the Largest Value in an Array", 110, 110);
        addAssignment("Java-by doing","Giving an Array a Bunch of Values at Once", 30, 30);
        addAssignment("Java-by doing","Parallel Arrays", 100, 100);
        addAssignment("Java-by doing","Tic-Tac-Toe", 150, 150);
        addAssignment("Java-by doing","Hangman", 200, 200);

        //Sorting
        addAssignment("Java-by doing","MadLib Random", 40, 40);
        addAssignment("Java-by doing","Movie Title Generator", 40, 40);
        addAssignment("Java-by doing","PokeSwap", 40, 40);
        addAssignment("Java-by doing","PokeTrader", 30, 30);
        addAssignment("Java-by doing","PokeTrader 2", 20, 20);
        addAssignment("Java-by doing","Sorting an Array", 100, 100);
        addAssignment("Java-by doing","Exchange Sort", 120, 120);
        addAssignment("Java-by doing","Bubble Sort", 130, 130);
        addAssignment("Java-by doing","Selection Sort", 130, 130);

        //Records
        addAssignment("Java-by doing","Web Addresses", 30, 30);
        addAssignment("Java-by doing","Web Adresses Array", 10, 10);
        addAssignment("Java-by doing","Address toString()", 20, 20);
        addAssignment("Java-by doing","Basic Records", 80, 80);
        addAssignment("Java-by doing","A Little Database", 100, 100);
        addAssignment("Java-by doing","A Little Database is shorter with a loop", 100, 100);
        addAssignment("Java-by doing","Getting Data from a File", 120, 120);
        addAssignment("Java-by doing","Getting More Data from a File", 140, 140);
        addAssignment("Java-by doing","Storing Data in a File", 150, 150);
        addAssignment("Java-by doing","Reading What You Wrote", 160, 160);
        addAssignment("Java-by doing","Sorting an Array of Records", 160, 160);
        addAssignment("Java-by doing","Sorting Strings", 150, 150);
        addAssignment("Java-by doing","Sorting Records on Two Fields", 200, 200);

        //Objects
        addAssignment("Java-by doing","A Tic-Tac-Toe Class", 100, 100);

        //ArrayLists
        addAssignment("Java-by doing","Basic ArrayLists 0", 20, 20);
        addAssignment("Java-by doing","Basic ArrayLists 1", 30, 30);
        addAssignment("Java-by doing","Basic ArrayLists 2", 40, 40);
        addAssignment("Java-by doing","Basic ArrayLists 3", 25, 25);
        addAssignment("Java-by doing","Copying ArrayLists", 55, 55);
        addAssignment("Java-by doing","Finding a Value in an ArrayList", 80, 80);
        addAssignment("Java-by doing","ArrayList - There or Not?", 85, 85);
        addAssignment("Java-by doing","ArrayList - Where Is It?", 90, 90);
        addAssignment("Java-by doing","Getting the Largest Value in an ArrayList", 100, 100);
        addAssignment("Java-by doing","Locating the Largest Value in an ArrayList", 110, 110);
        addAssignment("Java-by doing","Sorting n ArrayList", 120, 120);
        addAssignment("Java-by doing","Sorting an ArrayList of Strings", 150, 150);
        addAssignment("Java-by doing","Sorting Sentences", 100, 100);
        addAssignment("Java-by doing","Sorting an ArrayList of Records", 160, 160);
        addAssignment("Java-by doing","Project:  Address Book", 200, 400);
        addAssignment("Java-by doing","Project:  Hearts", 300, 500);

        //Project Euler
        addAssignment("Java-by doing","001 - Sum Multiples of 3 or 5", 20, 20);
        addAssignment("Java-by doing","002 - Sum even Fibonacci Numbers", 40, 40);
        addAssignment("Java-by doing","003 - Largest Prime Factor of Big Number", 60, 60);
        addAssignment("Java-by doing","004 - Largest Palindromic Product", 70, 70);
        addAssignment("Java-by doing","005 - Smallest Divisible by 1 - 20", 50, 50);
        addAssignment("Java-by doing","006 - Sum of Squares Minus Squares of Sums", 50, 50);
        addAssignment("Java-by doing","007 - The 10001st Prime", 80, 80);
        addAssignment("Java-by doing","008 - Largest Product of Consecutive Digits", 100, 100);
        addAssignment("Java-by doing","009 - Pythagorean Triplet", 75, 75);
        addAssignment("Java-by doing","010 - Sum of Primes Under Two Million", 100, 100);
        addAssignment("Java-by doing","011 - Largest Product of 4 Numbers in Grid", 160, 160);
        addAssignment("Java-by doing","012 - First Triangle Number w/501 Divisors", 160, 160);
        addAssignment("Java-by doing","013 - Sum 100 fifty-Digit Numbers", 80, 80);
        addAssignment("Java-by doing","014 - Longest Collatz Sequence", 150, 150);
        addAssignment("Java-by doing","015 - Routes Through a 20X20 Grid", 100, 100);
        addAssignment("Java-by doing","016 - sum of the Digits of 2^1000", 50, 50);
        addAssignment("Java-by doing","017 - Letters in One to One Thousand", 110,110);
        addAssignment("Java-by doing","018 - Maximum Sum Path through Triangle", 160, 160);
        addAssignment("Java-by doing","019 - Sundays on the 1st of the Month", 50, 50);
        addAssignment("Java-by doing","020 - Sum the Digits of 100!", 50, 50);

        //graphics
        addAssignment("Java-by doing","Mouse Demo", 20, 20);
        addAssignment("Java-by doing","Traffic light - Drawing Shapes and Clicking Them", 40, 40);
        addAssignment("Java-by doing","Shapes Demo - easily Drawing and Transforming Shapes", 1, 1);
        addAssignment("Java-by doing","Last Clicked - Drawing Boxes Where You Clicked", 1, 1);
        addAssignment("Java-by doing","No erasey - Making Stuff Stay on the Screen", 1, 1);
        addAssignment("Java-by doing","Drag location - Gettint Drags instead of Clicks", 1,1);
        addAssignment("Java-by doing","Drawing Layers", 1,1);
        addAssignment("Java-by doing","Etch-A-Sketch", 20, 20);
        addAssignment("Java-by doing","Bouncing Ball", 1, 1);
        addAssignment("Java-by doing","Pong", 100, 100);
        addAssignment("Java-by doing","Reversi", 200, 200);
        addAssignment("Java-by doing","Paint", 200, 500);


    }

    private void loadCategories()throws Exception{
        addAssignmentCategory("Java-by doing");
    }

}
