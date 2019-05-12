package app.model;

import app.model.chords.TrendyChord;
import app.model.chords.XMLLoader;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HooktheoryConnection {

    public String requestToken(String username, String password)throws IOException {
        URL url = new URL("https://api.hooktheory.com/v1/users/auth");
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);
        DataOutputStream os = new DataOutputStream(connection.getOutputStream());
        os.writeBytes(loginParams(username,password));
        os.flush();
        os.close();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return pullToken(response.toString());
        }
        return null;
    }

    private String loginParams(String username, String password){
        return "username=" + username + "&password=" + password;
    }

    private String pullToken(String response){
        Pattern pattern = Pattern.compile("activkey\":\"(\\w+?)\"");
        Matcher matcher = pattern.matcher(response);
        if(matcher.find())return matcher.group(1);
        return null;
    }

    public List<TrendyChord> requestTrends(String token, String childPath)throws XMLStreamException, IOException {
        String urlString = "https://api.hooktheory.com/v1/trends/nodes";
        childPath = convertTrack(childPath);
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
