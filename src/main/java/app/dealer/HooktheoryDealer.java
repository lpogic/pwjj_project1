package app.dealer;

import app.controller.MainController;
import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import app.model.HooktheoryConnection;
import app.model.MidiTrackPlayer;
import app.model.TrackText;
import app.model.chords.TrendyChord;
import javafx.scene.control.Alert;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;


public class HooktheoryDealer extends OpenDealer {

    public static final Contract<String> requestToken = Contract.forObjectOf(String.class);
    public static final Contract<String> getToken = Contract.forObjectOf(String.class);
    public static final Contract<String> username = Contract.forObjectOf(String.class);
    public static final Contract<String> password = Contract.forObjectOf(String.class);
    public static final Contract<List<TrendyChord>> getTrends = Contract.forListOf(TrendyChord.class, Stamp.SUPPLY);

    private HooktheoryConnection hooktheory;

    public HooktheoryDealer(OpenRoot openRoot) {
        super(openRoot);
        hooktheory = new HooktheoryConnection();
    }

    @Override
    public void employ() {

        shop().offer(getToken,()->{
            root().openStage("login").openScene().openStyle("css/login.css");
            root().openStage("login").showAndWait();
            return shop().deal(requestToken);
        });

        shop().offer(requestToken,()->{
            if(shop().order(username) && shop().order(password)){
                try {
                    return hooktheory.requestToken(shop().deal(username), shop().deal(password));
                }catch (IOException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Wystapil blad podczas logowania");
                    alert.showAndWait();
                }
            }
            return null;
        });

        shop().offer(MainController.showDiagram,()->{
            if(shop().order(getTrends)){
                root().openStage("diagram").show();
            }
            return null;
        });

        shop().offer(getTrends,()->{
            if(shop().order(getToken) && shop().order(TrackText.class)){
                TrackText trackText = shop().deal(TrackText.class);
                if(MidiTrackPlayer.validateTrackText(trackText.getTrack())) {
                    try {
                        return hooktheory.requestTrends(shop().deal(getToken), trackText.getTrack());
                    }catch (IOException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Blad");
                        alert.setHeaderText("Blad polaczenia z Hooktheory");
                        alert.showAndWait();
                    }catch (XMLStreamException xmlse){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Blad");
                        alert.setHeaderText("Nie znaleziono propozycji");
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
    }
}
