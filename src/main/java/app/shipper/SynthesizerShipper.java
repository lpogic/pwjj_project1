package app.shipper;

import app.core.OpenRoot;
import app.core.service.OpenShipper;
import app.core.shop.Shop;
import app.model.MidiTrackPlayer;
import app.model.TrackText;

public class SynthesizerShipper extends OpenShipper {
    public static final Object play = new Object();

    public SynthesizerShipper(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void signContract(Shop shop) {
        shop.offer(play,()->{
            synchronized (play) {
                if (shop.order("TrackText", TrackText.class)) {
                    TrackText trackText = (TrackText) shop.purchase("TrackText");
                    if (MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                        MidiTrackPlayer.play(trackText, MidiTrackPlayer.INSTRUMENT_BASS);
                    }
                }
            }
            return null;
        });
    }
}
