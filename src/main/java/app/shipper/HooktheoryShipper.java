package app.shipper;

import app.core.OpenRoot;
import app.core.service.OpenShipper;
import app.core.shop.Shop;
import app.model.MidiTrackPlayer;
import app.model.TrackText;
import app.model.chords.TrendyChord;
import app.model.chords.XMLLoader;
import javafx.scene.control.Alert;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HooktheoryShipper extends OpenShipper {

    public static final Object showTrends = new Object();
    public static final Object trends = new Object();

    public HooktheoryShipper(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void signContract(Shop shop) {
        super.signContract(shop);

        shop.offer(showTrends,()->{
            if(shop.order(LoginShipper.token,String.class)){
                openRoot().openStage("diagram").show();
            }
            return null;
        });

        shop.offer(trends,()->{
            if(shop.order(LoginShipper.token,String.class)
                    && shop.order("TrackText", TrackText.class)){
                TrackText trackText = (TrackText)shop.purchase("TrackText");
                if(MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                    try {
                        return requestTrends((String) shop.purchase(LoginShipper.token),
                                convertTrack(trackText.getTrack()));
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

    private Collection<TrendyChord> requestTrends(String token, String childPath)throws Exception{
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
