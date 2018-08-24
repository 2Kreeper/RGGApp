package eu.barononline.rggapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import eu.barononline.rggapp.fragments.events.EventsFragment;
import eu.barononline.rggapp.fragments.trainingtimes.TrainingTimesFragment;

public class MainActivity extends BaseActivity {

	public static final String LOG_TAG = "eu.barononline.rggapp";

	private static Resources.Theme appTheme;
	public static Resources.Theme getAppTheme() { return appTheme; }

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter sectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager viewPager;

	private TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		appTheme = getAppTheme();

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		viewPager = findViewById(R.id.container);
		viewPager.setAdapter(sectionsPagerAdapter);

		tabLayout = findViewById(R.id.tab_layout);

		//prevent toolbar from collapsing when scrolling
		AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) findViewById(R.id.toolbar).getLayoutParams();
		appBarParams.setScrollFlags(0);
		findViewById(R.id.toolbar).setLayoutParams(appBarParams);

		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition(), true);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
				case 0:
					return new TrainingTimesFragment();
				case 1:
					return new EventsFragment();
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}
