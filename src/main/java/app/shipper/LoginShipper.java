package app.shipper;

import app.core.OpenRoot;
import app.core.service.OpenShipper;
import app.core.shop.Product;
import app.core.shop.Shop;
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

public class LoginShipper extends OpenShipper {
    public static final Object remoteToken = new Object();
    public static final Object token = new Object();

    public LoginShipper(OpenRoot openRoot) {
        super(openRoot);
    }

    @Override
    public void signContract(Shop shop){
        shop.offer(remoteToken,()->{
            if(shop.order("username",String.class) &&
                    shop.order("password",String.class)){
                try {
                    return requestToken((String) shop.purchase("username"),
                            (String) shop.purchase("password"));
                }catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad");
                alert.setHeaderText("Wystapil blad podczas logowania");
                alert.showAndWait();
            }
            }
            return null;
        },Product.REUSABLE);

        shop.offer(token,()->{
            openRoot().openStage("login").openScene().openStyle("css/login.css");
            openRoot().openStage("login").showAndWait();
            return shop.instantOrder(remoteToken);
        },Product.REUSABLE);
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
