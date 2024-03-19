# Guide to Unite test of the MultiThreaded Card Game

- All the relevant files regarding the test are in this directory (test) : 
    total of 7 files and 1 directory including this file 

    1. lib directory : 
        jar files for Junit 4 and all relevant libraries required for the tests are stored in this directory (or jar files in the directory).
    2. testRunner.java : 
        the main class which should be used to run the tests with simply running the main.
        it should print (All tests passed: true) which indicates that all tests in different test files passed successfully.
    3. testCard.java : 
        contains all the test methods for methods which are present in the card class (Card.java) 
    4. testPlayer.java : 
        contains all the test methods for methods which are present in the player class (Player.java)
    5. testInputOutput.java : 
        contains all the relevant tests regarding the InputOutput class, it testes the InputOutput classes's constructor, it's methods and objects.
    6. testCardGame : 
        contains all the tests regarding the builder methods which majorly distribute the cards in the pack between players and decks and passes them to their relevant classes, it's also testing the main game method which threads are playing in.
    7. READMRE.md :
        this file ... contains information on how to run the tests and what test suites we have.
    8. mockPack.txt :
        is a simulated pack used for testing the InputOutput Class in testInputOutput.java
    9. Report.pdf : 
        provides a report for better understanding the project.
    10. CardGamePrintOutCode.pdf :
        contains a printout of the final code and associated tests.
    11. cardsTest.jar : 
        is a jar file for running the tests.
    12. Manifest.txt : 
        a text file helper for creating the jar file.


        # Instructions 

        - After the cardsTest.zip is unzipped copy all the content in the test directory and 
            paste them in the same directory (Path) as the source code.

        - if the jar files for the JUnit are not added (they should be), please add them manually using : 
            javac -cp CardGame/lib/junit-4.13.2.jar:CardGame/lib/hamcrest-core-1.3.jar ./*.java

        - if there is a problem with the code and specifically including the class path for the lib directories       
            content, you can also easily run the jar file which does the same thing.

        - Then simply run the main method within the testRunner.java which runs all the test suits. 

        - The test should take a few seconds.

        - if "players have not been set please re run the game" this message appeared on the screen please re run the test runner.

        # Notes 

        - Objects used for development and/or implementation of the game have not been tested 
            with their individual test methods, Instead they are tested throughout the test classes within each test method (testing their functionality whilst using them), they have been used as mock objects. 
        - these tests should be in the exact location of the source code in order to be executed, they also rely on the players and decks directories to write players and decks information to their relevant files.
        - 1 unknown test might fail through the integration test of the whole program, possibly because threads are interfering with the program. 




