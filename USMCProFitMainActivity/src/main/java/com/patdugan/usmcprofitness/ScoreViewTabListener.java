package com.patdugan.usmcprofitness;

import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;

public class ScoreViewTabListener<T extends Fragment> implements TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
 
    public ScoreViewTabListener(Activity activity, String tag, Class<T> clz){
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // Nothing special to do here for this application
    }
 
    @SuppressWarnings("deprecation")
	@Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if(mFragment==null){
            mFragment = (Fragment) Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content,mFragment, mTag);
        }else{
            ft.attach(mFragment);
        }
    }

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if(mFragment!=null)
            ft.detach(mFragment);
	}
}