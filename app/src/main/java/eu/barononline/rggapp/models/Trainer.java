package eu.barononline.rggapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import eu.barononline.rggapp.models.training.Training;

public class Trainer {

	private String firstName, lastName;

	public Trainer(JSONObject obj) {
		try {
			this.firstName = obj.getString("firstName");
			this.lastName = obj.getString("lastName");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Trainer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
