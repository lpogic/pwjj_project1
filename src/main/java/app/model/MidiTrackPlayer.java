package app.model;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MidiTrackPlayer {
    public static final int CMD_PICK_INSTRUMENT = 192;
    public static final int CMD_PLAY = 144;
    public static final int CMD_STOP = 128;
    public static final int INSTRUMENT_BASS = 10;

    private MidiTrackPlayer() {
    }

    public static void play(TrackText trackText, int instrument)
    {
        System.out.println("midi play");
        Collection<Integer> noteSequence = parseNoteSequence(trackText.getTrack());
        int rate = trackText.getRate();
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
            int i = 1;

            track.add(makeEvent(CMD_PICK_INSTRUMENT, 1, instrument, 0, 1));
            for(Integer it : noteSequence){
                track.add(makeEvent(CMD_PLAY,1,it,100,i));
                i+= rate;
                track.add(makeEvent(CMD_STOP,1,it,100,i));
            }

            sequencer.setSequence(sequence);
            sequencer.start();

            while (sequencer.isRunning()){
                Thread.sleep(1);
            }
            System.out.println("end");
        }
        catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException mue) {
            mue.printStackTrace();
        }
    }

    private static MidiEvent makeEvent(int command, int channel,
                               int note, int velocity, int tick) {
        try {
            return new MidiEvent(new ShortMessage(command, channel, note, velocity), tick);
        } catch (InvalidMidiDataException imde) {
            imde.printStackTrace();
        }
        return null;
    }

    public static boolean validateTrackText(String trackText){
        return trackText.matches("^[1-6]*$");
    }

    public static Collection<Integer> parseNoteSequence(String trackText){
        List<Integer> notes = new ArrayList<>();
        for(char it : trackText.toCharArray()){
            notes.add((it - '0') * 4 + 20);
        }
        return notes;
    }
}
