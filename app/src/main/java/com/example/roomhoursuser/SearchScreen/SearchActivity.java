package com.example.roomhoursuser.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.roomhoursuser.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RelativeLayout RR_back;
    private EditText edt_search;

    private RecyclerView recycler_view_ProductList;
    private SearchviewRecyclerViewAdapter mAdapter;
    private ArrayList<MemberDataList> modelList_member = new ArrayList<>();

    private Preference preference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.mehroon));
        }

        setContentView(R.layout.activity_search);
        findview();

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        preference = new Preference(this);


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // Toast.makeText( HomeProductListSearch.this, "", Toast.LENGTH_SHORT ).show();

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // Toast.makeText( HomeProductListSearch.this, ""+charSequence, Toast.LENGTH_SHORT ).show();
                if(charSequence.length()<=0)
                {
                    recycler_view_ProductList.setVisibility( View.INVISIBLE );

                }else
                {
                    recycler_view_ProductList.setVisibility( View.VISIBLE );
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });

        setAdapter();
    }

    private void findview() {
        RR_back=findViewById(R.id.RR_back);
        edt_search=findViewById(R.id.edt_search);
        progressBar=findViewById(R.id.progressBar);
        recycler_view_ProductList=(RecyclerView) findViewById(R.id.recycler_view_ProductList);
    }

    void filter(String text) {

        List<MemberDataList> temp = new ArrayList();
        for (MemberDataList d : modelList_member) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
           if (d.getName().toLowerCase().contains(text.toString().toLowerCase())) {
                temp.add(d);

            }
            //update recyclerview
            mAdapter.updateList((ArrayList<MemberDataList>) temp);

        }
    }



    private void setAdapter() {
        modelList_member.add(new MemberDataList("The  Dubai Mail"));
        modelList_member.add(new MemberDataList("The  Dubai Mail"));

        mAdapter = new SearchviewRecyclerViewAdapter(SearchActivity.this, this.modelList_member);
        recycler_view_ProductList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recycler_view_ProductList.setLayoutManager(linearLayoutManager);
        recycler_view_ProductList.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new SearchviewRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, MemberDataList model) {

            }
        });
    }

}
