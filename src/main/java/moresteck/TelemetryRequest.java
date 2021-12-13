package moresteck;

import com.google.gson.Gson;
import moresteck.util.Request;
import moresteck.util.RequestUtil;
import moresteck.util.Response;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class TelemetryRequest extends Request {
    public String source = "minecraft.java";
    public String name = "WorldLoaded";
    public long timestamp = Instant.now().getEpochSecond();
    public TelemetryData data;

    public TelemetryRequest(TelemetryData telemetryData) {
        this.REQUEST_URL = "https://api.minecraftservices.com/events";

        this.data = telemetryData;

        this.PROPERTIES.put("Content-Type", "application/json; charset=utf-8");
        this.PROPERTIES.put("Authorization", "Bearer " + null);

        Gson gson = new Gson();
        try {
            this.PROPERTIES.put("Content-Length", Integer.toString(gson.toJson(this).getBytes(StandardCharsets.UTF_8).length));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Response perform() {
        RequestUtil.performPOSTRequest(this);
        return null;
    }
}
