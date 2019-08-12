package com.sirius;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends
        RecyclerView.Adapter<CustomAdapter.ModalViewHolder> {

    Context context;
    private List<CustomModal> modalList;

    public CustomAdapter(Context mContext, List<CustomModal> customList) {
        this.context = mContext;
        this.modalList = (List<CustomModal>) customList;

    }


    @Override
    public ModalViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        View userLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.catalog_row, null);
        // create ModalViewHolder

        ModalViewHolder modalViewHolder = new ModalViewHolder(userLayoutView);

        return modalViewHolder;
    }

    @Override
    public void onBindViewHolder(ModalViewHolder modalViewHolder, int position) {

        CustomModal singleCatalog = modalList.get(position);

        modalViewHolder.catalog_item = singleCatalog;
        modalViewHolder.txt_name.setText(singleCatalog.getUserName());
        modalViewHolder.txt_address.setText(singleCatalog.getUserAddress());
    }

    // Returns the size
    @Override
    public int getItemCount() {
        return modalList.size();
    }

    public static class ModalViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_name;
        public TextView txt_address;
        public TextView txt_userId;
        public CustomModal catalog_item;

        public ModalViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            txt_name = (TextView) itemLayoutView.findViewById(R.id.user_name);
            txt_address = (TextView) itemLayoutView.findViewById(R.id.txt_address);
            txt_address.setText(R.string.blank);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent userDetails = new Intent(view.getContext(), UserDetailActivity.class);

                    userDetails.putExtra("USER", catalog_item);

                    view.getContext().startActivity(userDetails);


                }
            });
        }
    }

}