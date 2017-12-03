package com.sabry.muhammed.hyperdesign;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import static com.sabry.muhammed.hyperdesign.MainActivity.globalDataHolderArray;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.mViewHolder> {

    private Context mContext;

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.main_activity_recycler_view
                        , parent
                        , false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        DataHolder myDataHolder = globalDataHolderArray.get(position);
        holder.bind(myDataHolder);
    }

    @Override
    public int getItemCount()
    {
        return globalDataHolderArray.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder {

        //getting reference to the views
        private final ImageView mainImage;
        private final TextView imageDescription;
        private final TextView priceTag;
        private final ConstraintLayout constraintLayout;
        private final LinearLayout linearLayout;

         mViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mainImage = itemView.findViewById(R.id.mainImage);
            imageDescription = itemView.findViewById(R.id.mainText);
            priceTag = itemView.findViewById(R.id.price);
            linearLayout = itemView.findViewById(R.id.mainRecyclerViewLayout);
            constraintLayout = itemView.findViewById(R.id.RecyclerViewConstraintLayout);
        }

        //Method to bind the data into views
        void bind(DataHolder dataHolder) {

            //getting DataHolder's data
            String textData = dataHolder.getTextData();
            String imageUri = dataHolder.getImageUrl();
            String priceText = dataHolder.getPrice();
            int height = dataHolder.getImageHeight();

            //Binding data to Child Views
            ViewGroup.LayoutParams params = constraintLayout.getLayoutParams();
            params.height = height;
            constraintLayout.setLayoutParams(params);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                linearLayout.setElevation(8);
            }

            imageDescription.setText(textData);
            priceTag.setText("$" + priceText);

            //downloading the image from internet
            Picasso.with(mContext).load(imageUri).resize(150, height).into(mainImage);
        }
    }
}
