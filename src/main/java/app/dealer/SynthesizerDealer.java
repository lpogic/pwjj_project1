package app.dealer;

import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import app.model.MidiTrackPlayer;
import app.model.TrackText;

public class SynthesizerDealer extends OpenDealer {
    public static final Contract<Object> play = Contract.forObject(Stamp.SERVICE);

    public SynthesizerDealer(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void employ() {
        shop().offer(play,()->{
            synchronized (play) {
                if (shop().order(TrackText.class)) {
                    TrackText trackText = shop().deal(TrackText.class);
                    if (MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                        MidiTrackPlayer.play(trackText, MidiTrackPlayer.INSTRUMENT_BASS);
                    }
                }
            }
            return null;
        });
    }
}
