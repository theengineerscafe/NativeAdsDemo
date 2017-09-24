package com.theengineerscafe.nativeadsdemo;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by tecstaff on 10/09/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;


    Context mContext;

    // The list of Native ads and recylcerview items.
    private final List<Object> mRecyclerViewItems;

    public RecyclerViewAdapter(Context context, List<Object> recyclerViewItems) {

        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }

    //ViewHolder for binding User view
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView userDetails;
        public ImageView userPic;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.userName);
            userDetails = (TextView) view.findViewById(R.id.userDetails);
            userPic = (ImageView) view.findViewById(R.id.userProfile);
        }
    }

    //ViewHolder for binding ad view
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        public NativeExpressAdViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflates adview or simple item view depending on the position of view
        switch (viewType) {

            case ITEM_VIEW_TYPE:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_item_user, parent, false);
                return new MyViewHolder(itemView);

            case AD_VIEW_TYPE:
            default:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ad_row_type, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);


        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {

            case ITEM_VIEW_TYPE:

                MyViewHolder myViewHolder = (MyViewHolder) holder;
                final User user = (User) mRecyclerViewItems.get(position);

                //set name and detail of user
                myViewHolder.userName.setText(user.getName());
                myViewHolder.userDetails.setText(user.getDetails());

                //set the photo of user
                Picasso.with(((MyViewHolder) holder).userPic.getContext()).load(user.getImageId()).into(myViewHolder.userPic);


                break;

            case AD_VIEW_TYPE:
            default:
                NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
                NativeExpressAdView adView = (NativeExpressAdView) mRecyclerViewItems.get(position);
                ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                adCardView.removeAllViews();

                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }

                adCardView.addView(adView);

        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    @Override
    public int getItemViewType(int position) {

        //the number 4 indicates that ad will be shown at every 4th position.
        // You can change it to whatever you like but also make sure to change it in your activity

        return (position % 4 == 0) ? AD_VIEW_TYPE : ITEM_VIEW_TYPE;
    }
}
