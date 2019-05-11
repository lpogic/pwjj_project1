package app.dealer;

import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.contract.ClassicContract;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import javafx.scene.control.Alert;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginDealer extends OpenDealer {

    public static final Contract<String> remoteToken = Contract.forClass(String.class, Stamp.WARRANTY);
    public static final Contract<String> token = Contract.forClass(String.class, Stamp.WARRANTY);
    public static final Contract<String> username = Contract.forClass(String.class);
    public static final Contract<String> password = Contract.forClass(String.class);

    public LoginDealer(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void employ(){
        shop().offer(remoteToken,()->{
            if(shop().order(username) && shop().order(password)){
                try {
                    return requestToken(shop().deal(username), shop().deal(password));
                }catch (IOException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Wystapil blad podczas logowania");
                    alert.showAndWait();
                }
            }
            return null;
        });

        shop().offer(token,()->{
            root().openStage("login").openScene().openStyle("css/login.css");
            root().openStage("login").showAndWait();
            return shop().deal(remoteToken,null);
        });
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

    private String requestToken(String username, String password)throws IOException {
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
}
