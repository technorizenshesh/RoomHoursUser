package com.example.roomhoursuser.HomeFragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roomhoursuser.FavFragmen.ApiModel.GetFavModelData;
import com.example.roomhoursuser.FavFragmen.FavFragment;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class FavRoomAllRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<GetFavModelData> modelList;
    private OnItemClickListener mItemClickListener;
    Fragment fragment;
    public FavRoomAllRecyclerViewAdapter(Context context, ArrayList<GetFavModelData> modelList, Fragment fragment) {
        this.mContext = context;
        this.modelList = modelList;
        this.fragment = fragment;
    }

    public void updateList(ArrayList<GetFavModelData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fav, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetFavModelData model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

          genericViewHolder.txt_selection_mode.setText(model.getSlectTionMode().toString());
          genericViewHolder.product_name.setText(model.getTitle().toString());

         String image_URL = model.getImage().toString();


          /*  if(image_URL !=null)
            {
                Glide.with(mContext).load(image_URL).placeholder(R.drawable.room_one)
                        .into(genericViewHolder.image);
            }*/

            genericViewHolder.RR_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FavFragment.progressBar.setVisibility(View.VISIBLE);
                    addFavroutie(model.getId().toString());
                }
            });

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

   public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetFavModelData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetFavModelData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_selection_mode;
        private TextView product_name;
        private RelativeLayout RR_like;
        private ImageView image;

        public ViewHolder(final View itemView) {
            super(itemView);

             this.txt_selection_mode=itemView.findViewById(R.id.txt_selection_mode);
             this.product_name=itemView.findViewById(R.id.product_name);
             this.image=itemView.findViewById(R.id.image);
             this.RR_like=itemView.findViewById(R.id.RR_like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }

    private void addFavroutie(String RoomId) {

        String User_Id= Preference.get(mContext,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_fav_room(User_Id,RoomId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    FavFragment.progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    String  result = jsonObject.getString("result");

                    if (status.equalsIgnoreCase("1")){
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        ((FavFragment) fragment).getFavuriteApi();

                    }else {

                        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    ((FavFragment) fragment).getFavuriteApi();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                FavFragment.progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

