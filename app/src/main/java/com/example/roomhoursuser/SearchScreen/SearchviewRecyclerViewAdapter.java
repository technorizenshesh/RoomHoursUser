package com.example.roomhoursuser.SearchScreen;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomhoursuser.R;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class SearchviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<MemberDataList> modelList;
    private OnItemClickListener mItemClickListener;

    public SearchviewRecyclerViewAdapter(Context context, ArrayList<MemberDataList> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<MemberDataList> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_productlist_new_search, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final MemberDataList model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txt_name.setText(model.getName().toString());
        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

   public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private MemberDataList getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, MemberDataList model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_name;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txt_name=itemView.findViewById(R.id.txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }



}

