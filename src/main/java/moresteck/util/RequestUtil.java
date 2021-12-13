package moresteck.util;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static net.waterfallflower.telemetrymod.TelemetryMod.CORE_LOGGER;

public class RequestUtil {
	private static boolean debug = true;

	public static String performPOSTRequest(Request req) {
		byte[] data = performRawPOSTRequest(req);
		if (data != null) {
			try {
				String response = new String(data, StandardCharsets.UTF_8);

				if(debug)
					CORE_LOGGER.info("Response from the telemetry service: \n" + response);
				if(response.contains("ok"))
					CORE_LOGGER.info("Successfully sent telemetry info!");
				return response;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] performRawPOSTRequest(Request req) {
		HttpURLConnection con = null;
		try {
			URL url = new URL(req.REQUEST_URL);
			con = (HttpURLConnection) url.openConnection();

			for (String key : req.PROPERTIES.keySet()) {
				con.addRequestProperty(key, req.PROPERTIES.get(key));
			}
			con.setRequestMethod("POST");
			con.setReadTimeout(15000);
			con.setConnectTimeout(15000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);

			// Send POST
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			if (req.POST_DATA == null) {
				Gson gson = new Gson();
				String s = gson.toJson(req);
				if (debug) CORE_LOGGER.info("Sending: " + s);
				out.write(s.getBytes(StandardCharsets.UTF_8));
			} else {
				if (debug) CORE_LOGGER.info("Sending: " + new String(req.POST_DATA, StandardCharsets.UTF_8));
				out.write(req.POST_DATA);
			}
			out.flush();
			out.close();

			// Read response and return
			return readInputStream(con.getInputStream());
		} catch (Throwable t) {
			//t.printStackTrace();
			return null;
		}
	}

	public static String performGETRequest(Request req) {
		byte[] data = performRawGETRequest(req);
		if (data != null) {
			try {
				String response = new String(data, StandardCharsets.UTF_8);
				if (debug) CORE_LOGGER.info("Incoming: " + response);
				return response;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] performRawGETRequest(Request req) {
		HttpURLConnection con = null;
		try {
			if (debug) CORE_LOGGER.info("Outcome to: " + req.REQUEST_URL);
			URL url = new URL(req.REQUEST_URL);
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setReadTimeout(15000);
			con.setConnectTimeout(15000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			for (String key : req.PROPERTIES.keySet()) {
				con.addRequestProperty(key, req.PROPERTIES.get(key));
			}

			// Read response and return
			return readInputStream(con.getInputStream());
		} catch (Throwable t) {
			//t.printStackTrace();
			return null;
		}
	}

	public static byte[] readInputStream(InputStream in) {
		try {
			byte[] buffer = new byte[4096];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int count = in.available();
			while ((count = in.read(buffer)) > 0) {
				baos.write(buffer, 0, count);
			}
			byte[] data = baos.toByteArray();
			return data;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	public static byte[] readInputStream2(InputStream in) {
		try {
			byte[] buffer = new byte[4096];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (in.available() > 0) {
				in.read(buffer);
				baos.write(buffer);
			}
			byte[] data = baos.toByteArray();
			return data;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
}
