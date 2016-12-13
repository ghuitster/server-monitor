package com.example.david.servermonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResultsDisplayActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_display);
		ping("162.252.240.16");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_results_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public String ping(String url) {
		String str = "";
		try {
			Process process = Runtime.getRuntime().exec("/system/bin/ping -c 10 " + url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			int i;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();

			while((i = reader.read(buffer)) > 0)
				output.append(buffer, 0, i);

			reader.close();

			str = output.toString();

			String[] results = str.substring(str.indexOf("rtt") + "rtt min/avg/max/mdev = ".length(), str.length() - 4).split("/");
			Log.d("results - min", results[0]);
			Log.d("results - avg", results[1]);
			Log.d("results - max", results[2]);
			Log.d("results - mdev", results[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
