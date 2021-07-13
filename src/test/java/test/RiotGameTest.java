/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package test;

import kong.unirest.JsonNode;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import riotgame.Handler;
import riotgame.model.riotapi.RiotGame;
import riotgame.model.riotapi.RiotGameMaker;
import riotgame.model.twilioapi.TwilioSmsMaker;
import riotgame.model.twilioapi.TwilioSmsOffline;
import riotgame.riotobjects.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RiotGameTest {

    @Mock
    private RiotGame riot;

    private RiotGameMaker maker;

    private TwilioSmsMaker smsMaker;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void set() {
        Handler handler = new Handler();
        RiotGameMaker.init(handler, riot);

        maker = RiotGameMaker.getMaker();

        TwilioSmsOffline sms = new TwilioSmsOffline();
        TwilioSmsMaker.init(sms);
        smsMaker = TwilioSmsMaker.getMaker();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    public void testTwilioSMS() {
        smsMaker.sendMessage("Printing that report!");
        assertTrue(outContent.toString().contains("Printing that report!"));
    }

    @Test
    public void testUserInfo() {
        when(riot.getSummonerByName("oi", "name"))
            .thenReturn(
                new JsonNode(
                    "{\n"
                        + "    \"id\": \"OkDB-hlKjxiGBoqTFaHb9KS9_wjfSI7gXbG7hgP9lWpq-t8\",\n"
                        + "    \"accountId\": \"QOEAA0klFt9uvKZJPJckEjdLsu1moc-Ojojl0CZOxhL__Ew3WFFjJ5Ju\",\n"
                        + "    \"puuid\": \"YH4UnOulfZV7Ziupe_CQ5XwYA9qOBpcEPULoOFRUgky2veBYIDCjy_05FG3U19m5UIzaX_-pSR6yVw\",\n"
                        + "    \"name\": \"heniaodaren\",\n"
                        + "    \"profileIconId\": 3175,\n"
                        + "    \"revisionDate\": 1596174391000,\n"
                        + "    \"summonerLevel\": 10\n"
                        + "}"));

        UserInfo info = maker.getSummonerByName("oi", "name");

        verify(riot).getSummonerByName("oi", "name");

        assertEquals(10, info.getSummonerLevel());
    }
}