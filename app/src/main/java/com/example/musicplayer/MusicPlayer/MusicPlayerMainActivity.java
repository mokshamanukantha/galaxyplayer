package com.example.musicplayer.MusicPlayer;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.musicplayer.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MusicPlayerMainActivity extends ActionBarActivity implements MaterialTabListener {
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    private Resources res;
    //ImageView img;
    public MusicPlayerMainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main);
        res = this.getResources();
        // init toolbar (old action bar)
        /*DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Bitmap bitmap = null;

        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.music_notes_wallpaper);

        DTImageView bgImageView = new DTImageView(getApplicationContext());
        bgImageView.imageBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        img = (ImageView) findViewById(R.id.imageview);
        img.setImageBitmap(bgImageView.imageBitmap);
        bitmap.recycle();*/
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);

        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager);
        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(getIcon(i))
                            .setTabListener(this));

        }
    }
    @Override
    public void onTabSelected(MaterialTab tab) {
// when the tab is clicked the pager swipe content to the tab position
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public Fragment getItem(int num) {
            if(num==0) {
                return new SongsFragment();
            }
            if(num==1) {
                return new PlaylistsFragment();
            }
            if(num==2) {
                return new AlbumsFragment();
            }
            if(num==3) {
                return new ArtistsFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0) {
                return "Songs";
            }
            if(position==1) {
                return "Play Lists";
            }
            if(position==2) {
                return "Albums";
            }
            if(position==3) {
                return "Artists";
            }
            return null;
        }
    }
    /*
    * It doesn't matter the color of the icons, but they must have solid colors
    */
    private Drawable getIcon(int position) {
        if(position==0) {
            return res.getDrawable(R.drawable.ic_headphones_white);
        }
        if(position==1) {
            return res.getDrawable(R.drawable.ic_action_list_white);
        }
        if(position==2) {
            return res.getDrawable(R.drawable.ic_library_white);
        }
        if(position==3) {
            return res.getDrawable(R.drawable.ic_join_conversation);
        }
        return null;
    }

    /*public class DTImageView extends View {

        public Bitmap imageBitmap;

        public DTImageView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if(imageBitmap != null)
                canvas.drawBitmap(imageBitmap, 0, 0, null);
        }
    }*/
}
