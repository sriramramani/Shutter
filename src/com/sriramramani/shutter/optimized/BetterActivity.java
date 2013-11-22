package com.sriramramani.shutter.optimized;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.sriramramani.droid.inspector.server.ViewServer;
import com.sriramramani.shutter.R;

public class BetterActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewServer.get(this).addWindow(this);

        setContentView(R.layout.better_activity);

        final ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new PhotoAdapter(getResources()));
    }

	@Override
	public void onResume() {
	    super.onResume();
	    ViewServer.get(this).setFocusedWindow(this);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    ViewServer.get(this).removeWindow(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);

	    getWindow().setBackgroundDrawable(null);
	}
}
