/**
 * This class is part of the "Alien Aztec Adventure" application. 
 *
 * This class holds information about a command that was issued by the user.
 * 
 * A command currently consists of two strings:
 * - a command word 
 * - a second word 
 * 
 * The way this is used is: 
 * Commands are already checked for being valid command words. 
 * If the user entered an invalid command (a word that is not known) then the command word is <null>.
 * If the command had only one word, then the second word is <null>.
 * 
 * @author David Charkey , Michael Kolling, David J. Barnes, 
 */

public class Command
{
    private String commandWord;
    private String secondWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

