package ru.client.habr;

import ru.client.habr.R;
import ru.client.habr.HabraLogin.LogoutListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

/**
 * @author WNeZRoS
 */
public final class HabraPreferences extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		Preference prefLoginForm = findPreference("prefLoginForm");
		prefLoginForm.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				startActivity(new Intent(getBaseContext(),ActivityLogin.class));
				return false;
			}
		});
		
		Preference prefLogout = findPreference("prefLogout");
		prefLogout.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				HabraLogin.getHabraLogin().logout(new LogoutListener() {
					@Override
					public void onFinish(boolean logged) {
						if(!logged) {
							Toast.makeText(getApplicationContext(), getString(R.string.logout_success), Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(), getString(R.string.logout_fail), Toast.LENGTH_LONG).show();
						}
					}
				});
				return false;
			}
		});
	}
}