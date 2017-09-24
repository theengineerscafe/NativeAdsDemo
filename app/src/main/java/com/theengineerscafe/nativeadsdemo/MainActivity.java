package com.theengineerscafe.nativeadsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.app_id));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        addUser();

        //setup and load native ads
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds();

        adapter = new RecyclerViewAdapter(this, mRecyclerViewItems);
        recyclerView.setAdapter(adapter);



    }

    private void addUser() {

        //make sure add the images of user you want to show in drawable folder.

        User user = new User("James Anderson", "He is a very good bowler from England.", R.drawable.user1);
        mRecyclerViewItems.add(user);

        User user1 = new User("Johnny Depp", "One of the most paid actor in Hollywood.", R.drawable.user2);
        mRecyclerViewItems.add(user1);

        User user2 = new User("Elon Musk", "Inspiration for everyone. Also known as the living Iron Man.", R.drawable.user3);
        mRecyclerViewItems.add(user2);

        User user3 = new User("MKBHD", "Most followed tech YouTuber. Recently his channel crossed 5 million subscribers.", R.drawable.user4);
        mRecyclerViewItems.add(user3);

        User user4 = new User("Mrwhosetheboss", "Another tech YouTuber but a different one. Produces some great qualtiy content.", R.drawable.user1);
        mRecyclerViewItems.add(user4);

        User user5 = new User("Ravi Tamada", "Authour of a famous Android development blog AndroidHive.", R.drawable.user2);
        mRecyclerViewItems.add(user5);

        User user6 = new User("Mohit Gaur", "A singer from India who sings songs by including a story in them.", R.drawable.user3);
        mRecyclerViewItems.add(user6);

        User user7 = new User("Honey Singh", "Rapper from India known for many of his cool songs.", R.drawable.user4);
        mRecyclerViewItems.add(user7);

        User user8 = new User("Unerase Poetry", "A beautiful channel where you can find the youths of India presenting their poetry.", R.drawable.user1);
        mRecyclerViewItems.add(user8);

        User user9 = new User("Silicon Valley", "A series based on startup along with some comedy.", R.drawable.user2);
        mRecyclerViewItems.add(user9);

        User user10 = new User("Tyrion Lannister", "I think this man doesn't need an introduction.", R.drawable.user3);
        mRecyclerViewItems.add(user10);

        User user11 = new User("Jaime Lannister", "Brother of Tyrion Lannister.", R.drawable.user4);
        mRecyclerViewItems.add(user11);

        User user12 = new User("Jon Snow", "The one who doesn't know anything.", R.drawable.user1);
        mRecyclerViewItems.add(user12);

        User user13 = new User("Arya Stark", "The faceless girl from Game of Thrones.", R.drawable.user2);
        mRecyclerViewItems.add(user13);

    }

    private void addNativeExpressAds() {

        for (int i = 0; i < mRecyclerViewItems.size(); i += 4) {

            // Check whether we already have a NativeExpressAdView on the list before adding one
            if ((mRecyclerViewItems.size() - i == 0) || !(mRecyclerViewItems.get(i) instanceof NativeExpressAdView)) {
                final NativeExpressAdView adView = new NativeExpressAdView(this);
                mRecyclerViewItems.add(i, adView);
            }

        }
    }

    private void setUpAndLoadNativeExpressAds() {

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                final float density = getResources().getDisplayMetrics().density;
                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                for (int i = 0; i <= mRecyclerViewItems.size(); i += 4) {
                    final NativeExpressAdView adView =
                            (NativeExpressAdView) mRecyclerViewItems.get(i);

                    // Since you can only set the AdSize AND an AdUnitID once per AdView
                    if (adView.getAdSize() == null) {
                        AdSize adSize = new AdSize(
                                (int) (recyclerView.getWidth() / density),
                                150);
                        adView.setAdSize(adSize);
                        adView.setAdUnitId(getString(R.string.ad_id));
                    }

                }

                // Load the first Native Express ad in the items list.
                loadNativeExpressAd(0);
            }
        });
    }

    /**
     * Loads the Native Express ads in the items list.
     */
    private void loadNativeExpressAd(final int index) {

        if (index >= mRecyclerViewItems.size()) {
            return;
        }

        Object item = mRecyclerViewItems.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + 4);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + 4);
            }
        });


        // Load the Native Express ad.
        adView.loadAd(new AdRequest.Builder().build());
    }
}
