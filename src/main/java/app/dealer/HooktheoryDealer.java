package app.dealer;

import app.controller.DiagramController;
import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.contract.Contract;
import app.model.MidiTrackPlayer;
import app.model.TrackText;
import app.model.chords.TrendyChord;
import app.model.chords.XMLLoader;
import javafx.scene.control.Alert;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HooktheoryDealer extends OpenDealer {

    public HooktheoryDealer(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void employ() {

        shop().offer(DiagramController.show,()->{
            if(shop().order(LoginDealer.token)){
                root().openStage("diagram").show();
            }
            return null;
        });

        shop().offer(DiagramController.getTrends,()->{
            if(shop().order(LoginDealer.token) && shop().order(TrackText.class)){
                TrackText trackText = shop().deal(TrackText.class);
                if(MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                    try {
                        return requestTrends(shop().deal(LoginDealer.token), convertTrack(trackText.getTrack()));
                    }catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Blad");
                        alert.setHeaderText("Wystapil blad podczas proby zaladowania diagramu");
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
    }

    private List<TrendyChord> requestTrends(String token, String childPath)throws Exception{
        String urlString = "https://api.hooktheory.com/v1/trends/nodes";
        if(!childPath.isEmpty()){
            urlString += "?cp=" + childPath;
        }
        System.out.println(urlString);
        URL url = new URL(urlString);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept","application/xml");
        connection.setRequestProperty("Content-Type","application/xml");
        connection.setRequestProperty("Authorization","Bearer " + token);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return XMLLoader.loadTrendyChords(connection.getInputStream());
        }
        return null;
    }

    private String convertTrack(String track){
        if(track.isEmpty())return "";
        StringBuilder stringBuilder = new StringBuilder("" + track.charAt(0));
        for(int i = 1;i < track.length();++i){
            stringBuilder.append(',').append(track.charAt(i));
        }
        return stringBuilder.toString();
    }
}
