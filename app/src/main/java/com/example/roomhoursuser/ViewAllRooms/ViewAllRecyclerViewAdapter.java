package com.example.roomhoursuser.ViewAllRooms;


import android.app.Activity;
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
import com.example.roomhoursuser.HomeFragment.HomeDataModel;
import com.example.roomhoursuser.HomeFragment.HomeFragment;
import com.example.roomhoursuser.HomeScreen.HomeActivity;
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
public class ViewAllRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<HomeDataModel> modelList;
    private OnItemClickListener mItemClickListener;
    private Fragment fragment;
    boolean isLike=true;

    public ViewAllRecyclerViewAdapter(Context context, ArrayList<HomeDataModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<HomeDataModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommended_one, viewGroup, false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_new_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final HomeDataModel model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            String image_URL = model.getImage().toString();

            if(image_URL !=null)
            {
                Glide.with(mContext).load(image_URL).placeholder(R.drawable.room_one)
                        .into(genericViewHolder.img_room);
            }

            String IsFav= model.getIsFav().toString();

            if(IsFav.equalsIgnoreCase("1"))
            {
                genericViewHolder.img_like.setImageResource(R.drawable.hart_new);

            }else {

                genericViewHolder.img_like.setImageResource(R.drawable.add_fav);

            }

            genericViewHolder.product_name.setText(model.getTitle().toString());

            genericViewHolder.txt_selection_mode.setText(model.getSlectTionMode().toString());


            genericViewHolder.RR_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HomeFragment.progressBar.setVisibility(View.VISIBLE);
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

    private HomeDataModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, HomeDataModel model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_room;
        private TextView product_name;
        private RelativeLayout RR_like;
        private ImageView img_like;
        private TextView txt_selection_mode;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.img_room=itemView.findViewById(R.id.img_room);
            this.product_name=itemView.findViewById(R.id.product_name);
            this.RR_like=itemView.findViewById(R.id.RR_like);
            this.img_like=itemView.findViewById(R.id.img_like);
            this.txt_selection_mode=itemView.findViewById(R.id.txt_selection_mode);

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

                    ViewAllActivity.progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    String  result = jsonObject.getString("result");

                    if (status.equalsIgnoreCase("1")){

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                        ((ViewAllActivity)mContext).filterApi("","","","","","");


                    }else {

                        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                    ((ViewAllActivity)mContext).filterApi("","","","","","");

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ViewAllActivity.progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

