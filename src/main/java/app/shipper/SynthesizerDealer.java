package app.shipper;

import app.core.OpenRoot;
import app.core.service.OpenRootDealer;
import app.model.MidiTrackPlayer;
import app.model.TrackText;

public class SynthesizerDealer extends OpenRootDealer {
    public static final Object play = new Object();

    public SynthesizerDealer(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void employ() {
        offer(play,()->{
            synchronized (play) {
                if (rootDealer().order(TrackText.class)) {
                    TrackText trackText = rootDealer().purchase(TrackText.class);
                    if (MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                        MidiTrackPlayer.play(trackText, MidiTrackPlayer.INSTRUMENT_BASS);
                    }
                }
            }
            return null;
        });
    }
}
