package com.paisa.listener;
import android.content.Intent;
import android.support.v4.app.Fragment;

public interface FragmentListener {
	public void onItemSelected(Fragment frg, boolean animate);
	public void requestStartActivity(Intent intent);
	public void requestStartActivityForResult(Intent intent,int requestCode);

}
