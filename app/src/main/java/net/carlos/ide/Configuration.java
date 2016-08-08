package net.carlos.ide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Configuration extends PreferenceActivity
{
   private SharedPreferences prefs;

   public static boolean DESTAQUE = true;
   public static boolean SHOW_LINE = true;
   public static int FONTE_SIZE = 16;
   public static boolean REOPEN_FILES = false;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      // TODO: Implement this method
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.layout.configuracoes);

      prefs = PreferenceManager.getDefaultSharedPreferences(this);

      DESTAQUE = prefs.getBoolean("cbDestaque", true);
      SHOW_LINE = prefs.getBoolean("cbShowLine", true);
      FONTE_SIZE = Integer.valueOf(prefs.getString("edFonteSize", "16"));
      REOPEN_FILES = prefs.getBoolean("reopen", false);

      final EditTextPreference fonteSize = (EditTextPreference) findPreference("edFonteSize");
      CheckBoxPreference destaque = (CheckBoxPreference) findPreference("cbDestaque");
      CheckBoxPreference showLine = (CheckBoxPreference) findPreference("cbShowLine");

      destaque.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
	 {

	    @Override
	    public boolean onPreferenceClick(Preference p1)
	    {
	       DESTAQUE = p1.getSharedPreferences().getBoolean("cbDestaque", true);

	       return false;
	    }

	 });

      showLine.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
	 {
	    
	    @Override
	    public boolean onPreferenceClick(Preference p1)
	    {
	       SHOW_LINE = p1.getSharedPreferences().getBoolean("cbShowLine", true);

	       return false;
	    }

	 });

      fonteSize.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
	 {

	    @Override
	    public boolean onPreferenceClick(Preference p1)
	    {
	       FONTE_SIZE = Integer.valueOf(p1.getSharedPreferences().getString("edFonteSize", "16"));

	       return false;
	    }

	 });
   }

}
