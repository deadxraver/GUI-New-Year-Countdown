import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.PlayerApplet;

import java.io.InputStream;

public class MusicManager extends PlayerApplet {
    private static final MusicManager instance = new MusicManager();

    private MusicManager() {


        this.setFileName("nightcall.mp3");
    }

    public static MusicManager getInstance() {
        return instance;
    }

    public void play() throws JavaLayerException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("nightcall.mp3");
        play(is, getAudioDevice());
    }

}
