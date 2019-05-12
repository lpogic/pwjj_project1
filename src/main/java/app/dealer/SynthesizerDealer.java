package app.dealer;

import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import app.model.MidiTrackPlayer;
import app.model.TrackText;

public class SynthesizerDealer extends OpenDealer {

    public static final Contract<Object> playTrack = Contract.forObject(Stamp.SERVICE);

    private MidiTrackPlayer player;

    public SynthesizerDealer(OpenRoot openRoot) {
        super(openRoot);
        player = MidiTrackPlayer.getInstance();
    }

    @Override
    public void employ() {
        shop().offer(playTrack,()->{
            synchronized (playTrack) {
                if (shop().order(TrackText.class)) {
                    TrackText trackText = shop().deal(TrackText.class);
                    if (MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                        player.play(trackText, MidiTrackPlayer.INSTRUMENT_BASS);
                    }
                }
            }
            return null;
        });
    }
}
