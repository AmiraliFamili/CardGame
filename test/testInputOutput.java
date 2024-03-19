

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @see testInputOutput
 * 
 *      - testInputOutput class is a test suit for the class with the
 *      corresponding name (InputOutput Class),
 *      It test the constructors, methods as well as Objects and instances
 *      created and/or used by this method.
 * 
 * @Note most test methods within this test class are testing multiple aspects
 *       of the class such as other methods and objects
 * 
 * @Note methods deleteDeckFiles(), deletePlayerFiles(), createDeckFiles(),
 *       createPlayerFiles(), functionality has been tested from within other
 *       test methods, these methods have only one dynamic value used within
 *       them (playerNumber) which is being checked and validated inside each
 *       method.
 * 
 * @Note for some methods in this test class java reflection is used to access
 *       private methods and instances of InputOutput class.
 * 
 * @author Amirali Famili
 */
public class testInputOutput {

    /**
     * @see testGetPlayerNumberValidInput
     * 
     *      - testGetPlayerNumberValidInput is a void method, it uses the
     *      InputStream to pass a mock input to the getPlayerNumber method and
     *      compare the result with the mock valid input.
     * 
     * @link InputOutput.java
     * 
     * @Note since the method is working fine it's not asking for the player number
     *       again recursively
     * 
     * @InputOutputClassInstance user
     * @InputOutputClassMethods getPlayerNumber()
     */
    @Test
    public void testGetPlayerNumberValidInput() {
        String input = "3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputOutput user = new InputOutput();
        int result = user.getPlayerNumber();

        System.setIn(System.in);

        assertEquals(3, result);
    }

    /**
     * @see testGetPlayerNumberInvalidString
     * 
     *      - testGetPlayerNumberInvalidInput is an unimplemented test method, it
     *      uses input stream to pass a string to the getPlayerNumber method,
     *      however since this method is recursively calling it self again until it
     *      receives a valid Integer this method could not be implemented in the
     *      test.
     * 
     * @link InputOutput.java
     * 
     * @Note note that this type of input can be checked directly from terminal when
     *       the program asks for a player number as input
     * 
     * @InputOutputClassInstance user
     * @InputOutputClassMethods getPlayerNumber()
     */
    // @Test
    public void testGetPlayerNumberInvalidString() {
        String input = "invalid\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputOutput user = new InputOutput();
        try {
            user.getPlayerNumber();
            fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
        }
        System.setIn(System.in);
    }

    /**
     * @see testGetPackFilePath
     * 
     *      - testGetPackFilePath tests a valid mockPack which contains : 0,
     *      positive and negative integers and checks the length of the list which
     *      contains the extracted elements, the method getPackFilePath will ask for
     *      a new pack via terminal if it detects any invalid element within the
     *      pack file.
     * 
     * @link InputOutput.java
     * 
     * @Note any invalid pack file format like strings will be detected by the
     *       program this can be checked via terminal, Negative Integers and Spaces
     *       will be ignored
     * @Note Integer overflow will not be tested here since the program will ask for
     *       a "Valid" pack and the test will crash
     * 
     * @InputOutputClassInstance file
     * @InputOutputClassMethods getPackFilePath()
     */
    @Test
    public void testGetPackFilePath() {
        String input = "mockPack.txt\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputOutput file = new InputOutput();
        LinkedList<Integer> pack = file.getPackFilePath();

        assertEquals(29, pack.size());
        System.setIn(System.in);
    }

    /**
     * @see testInputOutputConstructorLinkedList
     * 
     *      - testInputOutputConstructorLinkedList is a void method, responsible to
     *      test InputOutput constructor with players nested LinkedList as argument,
     *      this constructor will delete player and deck files and creates new ones
     *      for the new players with the correct number of files, then it would add
     *      the current hand of players to the player files to initialize the hand
     *      they started the game with, this method will test all of them in order.
     * 
     * @link InputOutput.java
     * 
     * @Note the methods used within the constructor will be tested later in this
     *       test class, this test class is for testing the functionality of the
     *       constructor it self.
     * 
     * @InputOutputClassInstance inputOutput, files
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     */
    @Test
    public void testInputOutputConstructorLinkedList() {

        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);

            LinkedList<LinkedList<Integer>> players = new LinkedList<>();
            for (int i = 0; i < 8; i++) {
                players.add(new LinkedList<>());
                for (int j = 0; j < 6; j++) {
                    players.get(i).add(j);
                }
            }

            InputOutput files = new InputOutput(players);

            File player_2 = new File("players/player" + 2 + "_output.txt");
            File player_3 = new File("players/player" + 3 + "_output.txt");
            File player_5 = new File("players/player" + 5 + "_output.txt");
            File player_7 = new File("players/player" + 7 + "_output.txt");
            File deck_1 = new File("decks/deck" + 1 + "_output.txt");
            File deck_4 = new File("decks/deck" + 4 + "_output.txt");
            File deck_6 = new File("decks/deck" + 6 + "_output.txt");
            File deck_7 = new File("decks/deck" + 7 + "_output.txt");

            assertTrue(player_2.exists());
            assertTrue(player_3.exists());
            assertTrue(player_5.exists());
            assertTrue(player_7.exists());
            assertTrue(deck_1.exists());
            assertTrue(deck_4.exists());
            assertTrue(deck_6.exists());
            assertTrue(deck_7.exists());

            Path playerPath = Paths.get("players/player7_output.txt");
            Path deckPath = Paths.get("decks/deck7_output.txt");

            assertTrue(Files.size(deckPath) == 0);
            assertTrue(Files.size(playerPath) != 0);
        } catch (Exception e) {
            fail("testInputOutputConstructorLinkedList Failed");
        }
    }

    /**
     * @see testInputOutputConstructorLinkedLists
     * 
     *      - testInputOutputConstructorLinkedLists is a void method, It's
     *      responsible for testing the functionality of the InputOutput constructor
     *      that takes winner, player and deck lists, constructor should write the
     *      final decks in their file and inform all players that a player has won,
     *      this method will check that it's actually doing those things.
     * 
     * @link InputOutput.java
     * 
     * @Note the methods used within the constructor will be tested later in this
     *       test class, this test class is for testing the functionality of the
     *       constructor it self.
     * 
     * @InputOutputClassInstance end, files
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles()
     */
    @Test
    public void testInputOutputConstructorLinkedLists() {

        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            playerNumberField.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            playerNumberField.set(inputOutput, 5);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);

            LinkedList<LinkedList<Integer>> players = new LinkedList<>();
            LinkedList<LinkedList<Integer>> decks = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                players.add(new LinkedList<>());
                decks.add(new LinkedList<>());
                for (int j = 0; j < 6; j++) {
                    players.get(i).add(j);
                    decks.get(i).add(j);
                }
            }

            LinkedList<Integer> winner = new LinkedList<>(players.get(4));

            InputOutput end = new InputOutput(winner, players, decks);

            Path playerPath = Paths.get("players/player5_output.txt");
            Path deckPath = Paths.get("decks/deck5_output.txt");

            assertTrue(Files.size(deckPath) != 0);
            assertTrue(Files.size(playerPath) != 0);
        } catch (Exception e) {
            fail("testInputOutputConstructorLinkedLists Failed");
        }
    }

    /**
     * @see testWriteEndGameSamePlayers
     * 
     *      - testWriteEndGameSamePlayers is a void method, It tests the method
     *      writeEndGame in the InputOutput Class by passing
     *      the same player hands and same winner to it.
     * 
     * @link InputOutput.java
     * 
     * @Note one bug here is that player 5 has been declared as the winner however,
     *       in the files player 1 is informing other players that he has won,
     *       this is because the game should have one winner and it should be
     *       impossible to have two winners with the same hand, therefore first
     *       occurrence of "Winner"'s index is written in the files.
     * 
     * @InputOutputClassInstance player
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeEndGame(LinkedList<LinkedList<Integer>>,
     *                          LinkedList<Integer>)
     */
    @Test
    public void testWriteEndGameSamePlayers() {
        try {
            LinkedList<LinkedList<Integer>> players = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                players.add(new LinkedList<>());
                for (int j = 0; j < 6; j++) {
                    players.get(i).add(j);
                }
            }

            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Method writeEndGameMethod = playerClass.getDeclaredMethod("writeEndGame", LinkedList.class,
                    LinkedList.class);
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            writeEndGameMethod.setAccessible(true);
            playerNumberField.setAccessible(true);

            playerNumberField.set(inputOutput, 5);
            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);
            writeEndGameMethod.invoke(inputOutput, players, players.get(3));

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");
            Path playerPath3 = Paths.get("players/player3_output.txt");
            Path playerPath4 = Paths.get("players/player4_output.txt");
            Path playerPath5 = Paths.get("players/player5_output.txt");

            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
            assertTrue(Files.size(playerPath4) != 0);
            assertTrue(Files.size(playerPath5) != 0);

        } catch (Exception e) {
            fail("testWriteEndGameSamePlayers Failed");
        }
    }

    /**
     * @see testWriteEndGameEmptyPlayers
     * 
     *      - testWriteEndGameEmptyPlayers is a void method, It tests the method
     *      writeEndGame in the InputOutput Class by passing
     *      empty players and winner.
     * 
     * @link InputOutput.java
     * 
     * @InputOutputClassInstance player
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeEndGame(LinkedList<LinkedList<Integer>>,
     *                          LinkedList<Integer>)
     */
    @Test
    public void testWriteEndGameEmptyPlayers() {
        try {
            LinkedList<LinkedList<Integer>> players = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                players.add(new LinkedList<>());
            }

            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Method writeEndGameMethod = playerClass.getDeclaredMethod("writeEndGame", LinkedList.class,
                    LinkedList.class);
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            writeEndGameMethod.setAccessible(true);
            playerNumberField.setAccessible(true);

            playerNumberField.set(inputOutput, 5);
            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);
            writeEndGameMethod.invoke(inputOutput, players, players.get(3));

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");
            Path playerPath3 = Paths.get("players/player3_output.txt");
            Path playerPath4 = Paths.get("players/player4_output.txt");
            Path playerPath5 = Paths.get("players/player5_output.txt");

            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
            assertTrue(Files.size(playerPath4) != 0);
            assertTrue(Files.size(playerPath5) != 0);

        } catch (Exception e) {
            fail("testWriteEndGameEmptyPlayers Failed");
        }
    }

    /**
     * @see testWriteEndGameNoWinner
     * 
     *      - testWriteEndGameNoWinner is a void method, It tests the method
     *      writeEndGame in the InputOutput Class by passing
     *      an empty winner hand which is not present in the original valid players
     *      list.
     * 
     * @link InputOutput.java
     * 
     * @Note the method will successfully write the correct format information in
     *       the text files however, since we don't have a winner, the winner is
     *       declared as player 0.
     * 
     * @InputOutputClassInstance player
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeEndGame(LinkedList<LinkedList<Integer>>,
     *                          LinkedList<Integer>)
     */
    @Test
    public void testWriteEndGameNoWinner() {
        try {
            LinkedList<LinkedList<Integer>> players = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                players.add(new LinkedList<>());
                for (int j = 0; j < 6; j++) {
                    players.get(i).add(j);
                }
            }

            players.add(null); // add a null value instead of hand

            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Method writeEndGameMethod = playerClass.getDeclaredMethod("writeEndGame", LinkedList.class,
                    LinkedList.class);
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            writeEndGameMethod.setAccessible(true);
            playerNumberField.setAccessible(true);

            playerNumberField.set(inputOutput, 6);
            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);
            writeEndGameMethod.invoke(inputOutput, players, players.get(5));

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");
            Path playerPath3 = Paths.get("players/player3_output.txt");
            Path playerPath4 = Paths.get("players/player4_output.txt");
            Path playerPath5 = Paths.get("players/player5_output.txt");
            Path playerPath6 = Paths.get("players/player6_output.txt");

            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
            assertTrue(Files.size(playerPath4) != 0);
            assertTrue(Files.size(playerPath5) != 0);
            assertTrue(Files.size(playerPath6) == 0); // nothing should be written since the player is null

        } catch (Exception e) {
            fail("testWriteEndGameNoWinner Failed");
        }
    }

    /**
     * @see testWriteEndGameNull
     * 
     *      - testWriteEndGameNull is a void method, It tests the method
     *      writeEndGame in the InputOutput Class by passing
     *      null values instead of players and winner.
     * 
     * @link InputOutput.java
     * 
     * @Note the method shouldn't print anything to the files but it should execute
     *       the code with no errors.
     * 
     * @InputOutputClassInstance player
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeEndGame(LinkedList<LinkedList<Integer>>,
     *                          LinkedList<Integer>)
     */
    @Test
    public void testWriteEndGameNull() {
        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Method writeEndGameMethod = playerClass.getDeclaredMethod("writeEndGame", LinkedList.class,
                    LinkedList.class);
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            writeEndGameMethod.setAccessible(true);
            playerNumberField.setAccessible(true);

            playerNumberField.set(inputOutput, 6);
            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);
            writeEndGameMethod.invoke(inputOutput, null, null);

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");
            Path playerPath3 = Paths.get("players/player3_output.txt");
            Path playerPath4 = Paths.get("players/player4_output.txt");
            Path playerPath5 = Paths.get("players/player5_output.txt");

            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);
            assertTrue(Files.size(playerPath3) == 0);
            assertTrue(Files.size(playerPath4) == 0);
            assertTrue(Files.size(playerPath5) == 0);

        } catch (Exception e) {
            fail("testWriteEndGameNull Failed");
        }
    }

    /**
     * @see testWriteDrawsCard
     * 
     *      - testWriteDrawsCard is a void method, It tests the method
     *      writeDrawsCard in the InputOutput Class by passing wrong values as
     *      player index and check to see if something will be written in their file
     *      or an error will occur.
     * 
     * @link InputOutput.java
     * 
     * @Note since the drawnCard argument inside writeDrawsCard method doesn't need
     *       any validation checks, since it's just writing the argument as a
     *       string, I have avoided testing this parameter.
     * 
     * @InputOutputClassInstance draw
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeDrawsCard(int, int)
     */
    @Test
    public void testWriteDrawsCard() {
        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method writeDrawsCardMethod = playerClass.getDeclaredMethod("writeDrawsCard", int.class, int.class);
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            writeDrawsCardMethod.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            playerNumberField.set(inputOutput, 1);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);

            writeDrawsCardMethod.invoke(inputOutput, -8743, -3);

            Path playerPath1 = Paths.get("players/player1_output.txt");

            // the file should be empty because a player with index -3 does not exists
            assertTrue(Files.size(playerPath1) == 0);

            writeDrawsCardMethod.invoke(inputOutput, -8743, 1);
            // the file shouldn't be empty since the player has drawn a -8374 from their
            // left deck
            assertTrue(Files.size(playerPath1) != 0);

        } catch (Exception e) {
            fail("testWriteDrawsCard Failed");
        }
    }

    /**
     * @see testWriteDiscardsCard
     * 
     *      - testWriteDiscardsCard is a void method, It tests the method
     *      writeDiscardsCard in the InputOutput Class by passing wrong values as
     *      player index and check to see if something will be written in their file
     *      or an error will occur.
     * 
     * @link InputOutput.java
     * 
     * @Note since the discardedCard argument inside writeDiscardsCard method
     *       doesn't need
     *       any validation checks, since it's just writing the argument as a
     *       string, I have avoided testing this parameter.
     * 
     * @InputOutputClassInstance discard
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeDiscardsCard(int, int)
     */
    @Test
    public void testWriteDiscardsCard() {
        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method writeDiscardsCardMethod = playerClass.getDeclaredMethod("writeDiscardsCard", int.class, int.class);
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            writeDiscardsCardMethod.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            playerNumberField.set(inputOutput, 3);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);

            writeDiscardsCardMethod.invoke(inputOutput, -843, -37);
            writeDiscardsCardMethod.invoke(inputOutput, -843, -38);
            writeDiscardsCardMethod.invoke(inputOutput, -843, -3934);

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");
            Path playerPath3 = Paths.get("players/player3_output.txt");

            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);
            assertTrue(Files.size(playerPath3) == 0);

            writeDiscardsCardMethod.invoke(inputOutput, -843, 1);
            writeDiscardsCardMethod.invoke(inputOutput, -843, 2);
            writeDiscardsCardMethod.invoke(inputOutput, -843, 3);

            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);

        } catch (Exception e) {
            fail("testWriteDiscardsCard Failed");
        }
    }

    /**
     * @see testWriteCurrentHand
     * 
     *      - testWriteCurrentHand is a void method, It tests the method
     *      writeCurrentHand in the InputOutput Class by passing wrong values as
     *      player index and player's hand to check if something will be written in
     *      their file
     *      or an error will occur.
     * 
     * @Note this method will check the size of the file created to validate that
     *       the correct information is being printed in the files, it's also
     *       checking if the current hand is being added successfully.
     * 
     * @link InputOutput.java
     * 
     * @InputOutputClassInstance current
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeCurrentHand(LinkedList<Integer>, int)
     */
    @Test
    public void testWriteCurrentHand() {
        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method writeCurrentHandMethod = playerClass.getDeclaredMethod("writeCurrentHand", LinkedList.class,
                    int.class);
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            writeCurrentHandMethod.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            playerNumberField.set(inputOutput, 2);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");

            // player is null
            writeCurrentHandMethod.invoke(inputOutput, null, 1);
            writeCurrentHandMethod.invoke(inputOutput, null, 2);

            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);

            // indexes are wrong
            writeCurrentHandMethod.invoke(inputOutput, new LinkedList<>(), 0);
            writeCurrentHandMethod.invoke(inputOutput, new LinkedList<>(), -3);

            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);

            // correct
            writeCurrentHandMethod.invoke(inputOutput, new LinkedList<>(), 1);
            writeCurrentHandMethod.invoke(inputOutput, new LinkedList<>(), 2);

            assertEquals(26, Files.size(playerPath1));
            assertEquals(26, Files.size(playerPath2));

            LinkedList<Integer> mockHand = new LinkedList<>();
            mockHand.add(null);
            mockHand.add(-2);
            mockHand.add(-1);
            mockHand.add(0);
            mockHand.add(1);
            mockHand.add(2);

            writeCurrentHandMethod.invoke(inputOutput, mockHand, 1);
            writeCurrentHandMethod.invoke(inputOutput, mockHand, 2);

            assertEquals(65, Files.size(playerPath1));
            assertEquals(65, Files.size(playerPath2));

        } catch (Exception e) {
            fail("testWriteCurrentHand Failed");
        }
    }

    /**
     * @see testWriteDeckContents
     * 
     *      - testWriteDeckContents is a void method, It tests the method
     *      writeDeckContents in the InputOutput Class by passing wrong values as
     *      decks LinkedList and see if we get an error, it also validates that the
     *      correct information is being written in the text files.
     * 
     * @Note this method will check the size of the file created to validate that
     *       the correct information is being printed in the files.
     * 
     * @link InputOutput.java
     * 
     * @InputOutputClassInstance deck
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     *                          createDeckFiles(), createPlayerFiles(),
     *                          writeDeckContents(LinkedList<LinkedList<Integer>>)
     */
    @Test
    public void testWriteDeckContents() {

        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method writeDeckContentsMethod = playerClass.getDeclaredMethod("writeDeckContents", LinkedList.class);
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            writeDeckContentsMethod.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            playerNumberField.set(inputOutput, 3);
            createDeckFilesMethod.invoke(inputOutput);

            Path deckPath1 = Paths.get("decks/deck1_output.txt");
            Path deckPath2 = Paths.get("decks/deck2_output.txt");
            Path deckPath3 = Paths.get("decks/deck3_output.txt");
            Path deckPath4 = Paths.get("decks/deck4_output.txt");

            writeDeckContentsMethod.invoke(inputOutput, new LinkedList<>());

            assertEquals(0, Files.size(deckPath1));
            assertEquals(0, Files.size(deckPath2));
            assertEquals(0, Files.size(deckPath3));

            LinkedList<LinkedList<Integer>> decks = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
                decks.add(new LinkedList<>());
            }

            decks.get(0).add(null);
            decks.get(0).add(0);
            decks.get(0).add(1);

            decks.get(1).add(null);
            decks.get(1).add(-1);

            decks.get(2).add(null);
            decks.get(2).add(34);
            decks.get(2).add(null);
            decks.get(2).add(-15);

            writeDeckContentsMethod.invoke(inputOutput, decks);

            assertEquals(22, Files.size(deckPath1));
            assertEquals(21, Files.size(deckPath2));
            assertEquals(26, Files.size(deckPath3));

        } catch (Exception e) {
            fail("testWriteDeckContents Failed");
        }
    }

    /**
     * @see testCardsToString
     * 
     *      - testCardsToString is a void method, it tests the method cardsToString
     *      method by creating a mock hand with wrong format and values and passing
     *      it to this method without any errors.
     * 
     * @link InputOutput.java
     * 
     * @InputOutputClassInstance sHand
     * @InputOutputClassMethods cardsToString(LinkedList<Integer>)
     */
    @Test
    public void testCardsToString() {

        InputOutput sHand = new InputOutput();

        LinkedList<Integer> hand = new LinkedList<>();
        hand.add(0);
        hand.add(-1);
        hand.add(-1);
        hand.add(-237);
        hand.add(null);
        hand.add(-237);
        hand.add(-237);
        hand.add(0);
        hand.add(1);

        try {
            Class<?> inputOutputClass = sHand.getClass();
            Method cardsToStringMethod = inputOutputClass.getDeclaredMethod("cardsToString", LinkedList.class);
            cardsToStringMethod.setAccessible(true);

            String actual = (String) cardsToStringMethod.invoke(sHand, hand);
            assertEquals(28, actual.length());
            // since there are 19 characters and 9 spaces , given that null is counted as a
            // space

            // assertEquals("", (String) cardsToStringMethod.invoke(sHand, null)); // null
            // values are not supported by java reflection but the code is checking for null
            assertEquals("", (String) cardsToStringMethod.invoke(sHand, new LinkedList<>()));
        } catch (Exception e) {
            fail("testCardsToString Failed");
        }
    }

    /**
     * @see testInitialHand
     * 
     *      - testInitialHand is a void method, it tests the method initialHands
     *      from InputOutput class, initialHands method writes the very first lines
     *      of text inside the players text files, this method is testing it's
     *      functionality and robustness.
     * 
     * @link InputOutput.java
     * 
     * @InputOutputClassInstance player
     * @InputOutputClassMethods initialHands(LinkedList<LinkedList<Integer>>)
     */
    @Test
    public void testInitialHand() {

        try {
            InputOutput inputOutput = new InputOutput();

            Class<?> playerClass = inputOutput.getClass();
            Method deleteDeckFilesMethod = playerClass.getDeclaredMethod("deleteDeckFiles");
            Method deletePlayerFilesMethod = playerClass.getDeclaredMethod("deletePlayerFiles");
            Method createDeckFilesMethod = playerClass.getDeclaredMethod("createDeckFiles");
            Method initialHandMethod = playerClass.getDeclaredMethod("initialHand", LinkedList.class);
            Method createPlayerFilesMethod = playerClass.getDeclaredMethod("createPlayerFiles");
            Field playerNumberField = playerClass.getDeclaredField("playerNumber");

            deleteDeckFilesMethod.setAccessible(true);
            deletePlayerFilesMethod.setAccessible(true);
            createDeckFilesMethod.setAccessible(true);
            createPlayerFilesMethod.setAccessible(true);
            playerNumberField.setAccessible(true);
            initialHandMethod.setAccessible(true);

            deleteDeckFilesMethod.invoke(inputOutput);
            deletePlayerFilesMethod.invoke(inputOutput);
            playerNumberField.set(inputOutput, 4);
            createDeckFilesMethod.invoke(inputOutput);
            createPlayerFilesMethod.invoke(inputOutput);

            Path playerPath1 = Paths.get("players/player1_output.txt");
            Path playerPath2 = Paths.get("players/player2_output.txt");
            Path playerPath3 = Paths.get("players/player3_output.txt");
            Path playerPath4 = Paths.get("players/player4_output.txt");

            LinkedList<LinkedList<Integer>> players = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
                players.add(new LinkedList<>());
            }

            players.add(null);

            players.get(0).add(null);
            players.get(0).add(null);
            players.get(0).add(null);
            players.get(0).add(null);
            players.get(1).add(5);
            players.get(1).add(-45);
            players.get(1).add(-7834);
            players.get(2).add(1);
            players.get(2).add(1);
            players.get(2).add(345678976);

            initialHandMethod.invoke(inputOutput, players);

            assertEquals(27, Files.size(playerPath1));
            assertEquals(35, Files.size(playerPath2));
            assertEquals(37, Files.size(playerPath3));
            assertEquals(0, Files.size(playerPath4));

        } catch (Exception e) {
            fail("testWriteDrawsCard Failed");
        }
    }
    public static void main(String[] args) {
        testInputOutput test = new testInputOutput();
        test.testWriteDiscardsCard();
    }
}
