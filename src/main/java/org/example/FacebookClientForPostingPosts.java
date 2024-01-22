package org.example;

import com.vdurmont.emoji.EmojiParser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookClientForPostingPosts {

    private static final String TXT_FOR_POST_PATH = "C:\\Users\\PC\\Desktop\\FaceBookPostSender\\src\\main\\java\\org\\example\\postText.txt";
    private static Robot rb;

    public void sendPostIntoTheGroup(String[] groupId, String postText, File imageFile, String profileID) {
        try {
            rb = new Robot();
            TimeUnit.SECONDS.sleep(2);

            postToFacebookGroups(groupId, postText, imageFile);

            postToFacebookProfile(profileID, postText, imageFile);

        } catch (InterruptedException | AWTException e) {
            System.err.println(e);
        }

    }

    private static void typeText(Robot robot, String text) {

        for (char c : text.toCharArray()) {
            int keyCode;

            switch (c) {
                case ':':
                    keyCode = 1;
                    break;
                case '.':
                    keyCode = KeyEvent.VK_PERIOD;
                    break;
                case '/':
                    keyCode = KeyEvent.VK_SLASH;
                    break;
                case '-':
                    keyCode = KeyEvent.VK_MINUS;
                    break;
                case '"':
                    keyCode = KeyEvent.VK_QUOTE;
                    break;
                case '!':
                    keyCode = 0;
                    break;
                case '@':
                    keyCode = KeyEvent.VK_AT;
                    break;
                case '#':
                    keyCode = KeyEvent.VK_NUMBER_SIGN;
                    break;
                case '$':
                    keyCode = KeyEvent.VK_DOLLAR;
                    break;
                default:
                    keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                    break;
            }
            String s = String.valueOf(c);
            if(keyCode == 0) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if(keyCode == 1) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");
                Matcher m = pattern.matcher(s);
                if(s == s.toUpperCase() && !m.find()) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                    robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                } else {
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }
            }
        }
            robot.delay(10);
    }

    public void postToFacebookGroups(String[] groupIDs, String postText, File imageFile) {
        try {
            for (String groupID : groupIDs) {
                String link = "https://facebook.com/groups/" + groupID;

                openWebpage(new URI(link));

                TimeUnit.SECONDS.sleep(4);

                pressMouseOnPosition(1264, 482);

                TimeUnit.SECONDS.sleep(1);

                pressP();

                TimeUnit.SECONDS.sleep(3);

                workWithFacebookPost(postText, imageFile, false);
            }
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void postToFacebookProfile(String profileID, String postText, File photoFile) {
        String link = "https://www.facebook.com/profile.php?id=" + profileID;
        try {
            openWebpage(new URI(link));

            TimeUnit.SECONDS.sleep(3);

            pressMouseOnPosition(1160, 496);

            TimeUnit.SECONDS.sleep(2);

            workWithFacebookPost(postText, photoFile, true);


        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void workWithFacebookPost(String postText, File imageFile, boolean isProfile) {
        try {
            TimeUnit.SECONDS.sleep(1);

            typeText(rb, finalTextForTHePost(postText));

            TimeUnit.SECONDS.sleep(3);

            if(isProfile) {
                pressMouseOnPosition(956, 849);
            } else {
                pressMouseOnPosition(998, 839);
            }

            TimeUnit.SECONDS.sleep(1);

            pressMouseOnPosition(913, 594);

            enterImage(imageFile);

            TimeUnit.SECONDS.sleep(3);

            pressMouseOnPosition(953, 909);

            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void enterImage(File imageFile) {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        TransferableImage transferableImage = new TransferableImage(imageFile);

        clipboard.setContents(transferableImage, null);

        pressCTRL_V();
    }

    public String finalTextForTHePost(String postText) {
        return addSpacesForSize(removeLeadingSpaces(removedEmojiesFromText(postText)));
    }

    public String removedEmojiesFromText(String text) {
        return EmojiParser.removeAllEmojis(text);
    }


    public boolean openWebpage(URI uri) {
        Desktop desktop = desktopCreatorAndTester();
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Desktop desktopCreatorAndTester() {
        return Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    }

    private void pressP() {
        rb.keyPress(KeyEvent.VK_P);
        rb.keyRelease(KeyEvent.VK_P);
    }

    public String removeLeadingSpaces(String text) {
        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }

        return String.join("\n", lines);
    }

    public String addSpacesForSize(String text) {
        String[] lines = text.split("\n");

        if (lines.length < 15) {
            String[] newLines = new String[15];
            System.arraycopy(lines, 0, newLines, 0, lines.length);
            for (int i = lines.length; i < newLines.length; i++) {
                newLines[i] = "\n";
            }
            lines = newLines;
        }

        return String.join("\n", lines);
    }

    private void pressMouseOnPosition(int x, int y) throws InterruptedException {
        rb.mouseMove(x, y);
        rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rb.delay(200);
        rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void pressCTRL_V() {
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
    }

}
