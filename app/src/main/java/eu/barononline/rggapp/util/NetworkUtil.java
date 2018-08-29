package eu.barononline.rggapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {

	public static final String BASE_URL = "http://DESKTOP-D0P7VF6:52595/api/";

	public static String sendRequestGET(String urlString, String params) {
		try {
			if(!urlString.endsWith("/"))
				urlString += "/";

			URL url = new URL(urlString + "?" + params);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			InputStream in = con.getInputStream();

			String response = convertStreamToString(in);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");

		String result = s.hasNext() ? s.next() : "";
		s.close();
		return result;
	}
}
