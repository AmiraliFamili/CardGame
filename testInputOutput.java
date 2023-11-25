import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import javax.naming.spi.DirStateFactory.Result;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.After;
import org.junit.AfterClass;
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
     * @InputOutputClassInstance player, files
     * @InputOutputClassMethods deleteDeckFiles(), deletePlayerFiles(),
     */
    @Test
    public void testInputOutputConstructorLinkedList() {
        InputOutput player = new InputOutput();

        player.deleteDeckFiles();
        player.deletePlayerFiles();

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

        try {
            assertTrue(Files.size(deckPath) == 0);
            assertTrue(Files.size(playerPath) != 0);
        } catch (IOException e) {
            fail("Error whilst creating or assigning files for player 7 in : testInputOutputConstructorLinkedList");
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

        InputOutput file = new InputOutput();
        file.deleteDeckFiles();
        file.deletePlayerFiles();
        file.playerNumber = 5;
        file.createDeckFiles();
        file.createPlayerFiles();

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

        try {
            assertTrue(Files.size(deckPath) != 0);
            assertTrue(Files.size(playerPath) != 0);
        } catch (IOException e) {
            fail("Error whilst creating or assigning files for player 7 in : testInputOutputConstructorLinkedList");
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
        LinkedList<LinkedList<Integer>> players = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new LinkedList<>());
            for (int j = 0; j < 6; j++) {
                players.get(i).add(j);
            }
        }

        InputOutput player = new InputOutput();
        player.playerNumber = 5;
        player.deleteDeckFiles();
        player.deletePlayerFiles();
        player.createDeckFiles();
        player.createPlayerFiles();
        player.writeEndGame(players, players.get(4));

        Path playerPath1 = Paths.get("players/player1_output.txt");
        Path playerPath2 = Paths.get("players/player2_output.txt");
        Path playerPath3 = Paths.get("players/player3_output.txt");
        Path playerPath4 = Paths.get("players/player4_output.txt");
        Path playerPath5 = Paths.get("players/player5_output.txt");

        try {
            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
            assertTrue(Files.size(playerPath4) != 0);
            assertTrue(Files.size(playerPath5) != 0);
        } catch (IOException e) {
            fail("Error checking player final writes in : testWriteEndGameSamePlayers");
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
        LinkedList<LinkedList<Integer>> players = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new LinkedList<>());
        }

        InputOutput player = new InputOutput();
        player.playerNumber = 5;
        player.deleteDeckFiles();
        player.deletePlayerFiles();
        player.createDeckFiles();
        player.createPlayerFiles();
        player.writeEndGame(players, players.get(4));

        Path playerPath1 = Paths.get("players/player1_output.txt");
        Path playerPath2 = Paths.get("players/player2_output.txt");
        Path playerPath3 = Paths.get("players/player3_output.txt");
        Path playerPath4 = Paths.get("players/player4_output.txt");
        Path playerPath5 = Paths.get("players/player5_output.txt");

        try {
            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
            assertTrue(Files.size(playerPath4) != 0);
            assertTrue(Files.size(playerPath5) != 0);
        } catch (IOException e) {
            fail("Error checking player final writes in : testWriteEndGameSamePlayers");
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
        LinkedList<LinkedList<Integer>> players = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new LinkedList<>());
            for (int j = 0; j < 6; j++) {
                players.get(i).add(j);
            }
        }

        players.add(null); // add a null value instead of hand

        InputOutput player = new InputOutput();
        player.playerNumber = 6;
        player.deleteDeckFiles();
        player.deletePlayerFiles();
        player.createDeckFiles();
        player.createPlayerFiles();
        player.writeEndGame(players, new LinkedList<>());

        Path playerPath1 = Paths.get("players/player1_output.txt");
        Path playerPath2 = Paths.get("players/player2_output.txt");
        Path playerPath3 = Paths.get("players/player3_output.txt");
        Path playerPath4 = Paths.get("players/player4_output.txt");
        Path playerPath5 = Paths.get("players/player5_output.txt");
        Path playerPath6 = Paths.get("players/player6_output.txt");

        try {
            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
            assertTrue(Files.size(playerPath4) != 0);
            assertTrue(Files.size(playerPath5) != 0);
            assertTrue(Files.size(playerPath6) == 0); // check to see if the code can handle a null hand
        } catch (IOException e) {
            fail("Error checking player final writes in : testWriteEndGameSamePlayers");
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

        InputOutput player = new InputOutput();
        player.playerNumber = 5;
        player.deleteDeckFiles();
        player.deletePlayerFiles();
        player.createDeckFiles();
        player.createPlayerFiles();
        player.writeEndGame(null, null);

        Path playerPath1 = Paths.get("players/player1_output.txt");
        Path playerPath2 = Paths.get("players/player2_output.txt");
        Path playerPath3 = Paths.get("players/player3_output.txt");
        Path playerPath4 = Paths.get("players/player4_output.txt");
        Path playerPath5 = Paths.get("players/player5_output.txt");

        try {
            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);
            assertTrue(Files.size(playerPath3) == 0);
            assertTrue(Files.size(playerPath4) == 0);
            assertTrue(Files.size(playerPath5) == 0);
        } catch (IOException e) {
            fail("Error checking player final writes in : testWriteEndGameSamePlayers");
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
        InputOutput draw = new InputOutput();
        draw.deleteDeckFiles();
        draw.deletePlayerFiles();
        draw.playerNumber = 1;
        draw.createDeckFiles();
        draw.createPlayerFiles();

        draw.writeDrawsCard(-8743, -3);

        Path playerPath1 = Paths.get("players/player1_output.txt");
        try {
            // the file should be empty because a player with index -3 does not exists
            assertTrue(Files.size(playerPath1) == 0);
        } catch (IOException e) {
            fail("Internal error at testWriteDrawsCard method");
        }

        draw.writeDrawsCard(-8374, 1);
        try {
            // the file shouldn't be empty since the player has drawn a -8374 from their
            // left deck
            assertTrue(Files.size(playerPath1) != 0);
        } catch (IOException e) {
            fail("Internal error at testWriteDrawsCard method");
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
        InputOutput discard = new InputOutput();
        discard.deleteDeckFiles();
        discard.deletePlayerFiles();
        discard.playerNumber = 3;
        discard.createDeckFiles();
        discard.createPlayerFiles();

        discard.writeDrawsCard(-843, -37);
        discard.writeDrawsCard(-843, -38);
        discard.writeDrawsCard(-843, -3934);

        Path playerPath1 = Paths.get("players/player1_output.txt");
        Path playerPath2 = Paths.get("players/player2_output.txt");
        Path playerPath3 = Paths.get("players/player3_output.txt");
        try {
            // the file should be empty because a player with index -3 does not exists
            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);
            assertTrue(Files.size(playerPath3) == 0);
        } catch (IOException e) {
            fail("Internal error at testWriteDrawsCard method");
        }

        discard.writeDrawsCard(-874, 1);
        discard.writeDrawsCard(-874, 2);
        discard.writeDrawsCard(-874, 3);

        try {
            // the file shouldn't be empty since the player has discarded a -874 from their
            // right deck
            assertTrue(Files.size(playerPath1) != 0);
            assertTrue(Files.size(playerPath2) != 0);
            assertTrue(Files.size(playerPath3) != 0);
        } catch (IOException e) {
            fail("Internal error at testWriteDrawsCard method");
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
        InputOutput current = new InputOutput();
        current.deleteDeckFiles();
        current.deletePlayerFiles();
        current.playerNumber = 2;
        current.createDeckFiles();
        current.createPlayerFiles();

        Path playerPath1 = Paths.get("players/player1_output.txt");
        Path playerPath2 = Paths.get("players/player2_output.txt");

        current.writeCurrentHand(null, 1);
        current.writeCurrentHand(null, 2);

        try { // nothing should be written to the files since hand is null
            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);
        } catch (IOException e) {
            fail("testWriteCurrentHand failed because something was written to the files with a null hand");
        }

        current.writeCurrentHand(new LinkedList<>(), 0);
        current.writeCurrentHand(new LinkedList<>(), -3);

        try { // nothing should be written to the files since player's index has the wrong
              // value
            assertTrue(Files.size(playerPath1) == 0);
            assertTrue(Files.size(playerPath2) == 0);
        } catch (IOException e) {
            fail("testWriteCurrentHand failed because something was written to the files with a null hand");
        }

        current.writeCurrentHand(new LinkedList<>(), 1);
        current.writeCurrentHand(new LinkedList<>(), 2);

        try { // nothing should be written to the files since player's index has the wrong
              // value
            assertEquals(26, Files.size(playerPath1));
            assertEquals(26, Files.size(playerPath2));
        } catch (IOException e) {
            fail("testWriteCurrentHand failed because something was written to the files with a null hand");
        }

        LinkedList<Integer> mockHand = new LinkedList<>();
        mockHand.add(null);
        mockHand.add(-2);
        mockHand.add(-1);
        mockHand.add(0);
        mockHand.add(1);
        mockHand.add(2);

        current.writeCurrentHand(mockHand, 1);
        current.writeCurrentHand(mockHand, 2);

        try { // nothing should be written to the files since player's index has the wrong
              // value
            assertEquals(65, Files.size(playerPath1));
            assertEquals(65, Files.size(playerPath2));
        } catch (IOException e) {
            fail("testWriteCurrentHand failed because something was written to the files with a null hand");
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

        InputOutput deck = new InputOutput();
        deck.deleteDeckFiles();
        deck.deletePlayerFiles();
        deck.playerNumber = 3;
        deck.createDeckFiles();

        Path deckPath1 = Paths.get("decks/deck1_output.txt");
        Path deckPath2 = Paths.get("decks/deck2_output.txt");
        Path deckPath3 = Paths.get("decks/deck3_output.txt");
        Path deckPath4 = Paths.get("decks/deck4_output.txt");

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

        deck.writeDeckContents(null);

        try { // nothing should be written to the files since decks is null
            assertEquals(0, Files.size(deckPath1));
            assertEquals(0, Files.size(deckPath2));
            assertEquals(0, Files.size(deckPath3));
        } catch (IOException e) {
            fail("testWriteDeckContents failed because decks was null");
        }

        deck.writeDeckContents(new LinkedList<>());

        try { // nothing should be written to the files since decks is empty
            assertEquals(0, Files.size(deckPath1));
            assertEquals(0, Files.size(deckPath2));
            assertEquals(0, Files.size(deckPath3));
        } catch (IOException e) {
            fail("testWriteDeckContents failed because decks was empty");
        }

        deck.writeDeckContents(decks);

        try { // null values will not be written
            assertEquals(22, Files.size(deckPath1));
            assertEquals(21, Files.size(deckPath2));
            assertEquals(26, Files.size(deckPath3));
        } catch (IOException e) {
            fail("testWriteDeckContents failed because something is wrong with the source code");
        }

        deck.playerNumber = 4;
        deck.deleteDeckFiles();
        deck.createDeckFiles();
        decks.add(null);

        deck.writeDeckContents(decks);

        try { // null values will not be written even as main elements
            assertEquals(22, Files.size(deckPath1));
            assertEquals(21, Files.size(deckPath2));
            assertEquals(26, Files.size(deckPath3));
            assertEquals(0, Files.size(deckPath4));
        } catch (IOException e) {
            fail("testWriteDeckContents failed because an element in decks was null");
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
     * @InputOutputClassMethods handToString(LinkedList<Integer>)
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

        String actual = sHand.cardsToString(hand);
        assertEquals(28, actual.length());
        // since there are 19 characters and 9 spaces , given that null is counted as a
        // space

        assertEquals("", sHand.cardsToString(null));
        assertEquals("", sHand.cardsToString(new LinkedList<>()));
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
     * @InputOutputClassInstance player
     * @InputOutputClassMethods handToString(LinkedList<Integer>)
     */
    @Test
    public void testInitialHand() {

        InputOutput initial = new InputOutput();
        initial.deleteDeckFiles();
        initial.deletePlayerFiles();
        initial.playerNumber = 4;
        initial.createDeckFiles();
        initial.createPlayerFiles();

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


        initial.initialHand(players);

            try {
                assertEquals(27 ,Files.size(playerPath1));
                assertEquals(35 ,Files.size(playerPath2));
                assertEquals(37 ,Files.size(playerPath3));
                assertEquals(0 ,Files.size(playerPath4));
            } catch (IOException e) {
                fail("testInitialHand failed because of a problem with null values");
            }
    }
}
