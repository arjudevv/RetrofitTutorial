package com.example.apituto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apituto.api.ApiClient;
import com.example.apituto.api.ApiInterface;
import com.example.apituto.models.Photos;
import com.example.apituto.R;
import com.example.apituto.databinding.ActivityPhotosBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosActivity extends AppCompatActivity {

    ActivityPhotosBinding binding;

    ApiInterface apiInterface;

    private List<Photos> photos  =new ArrayList<>();

    ImageView imageView;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        setListeners();
        loadPhotos();
    }

    private void loadPhotos() {

        Call<List<Photos>> call = apiInterface.getPhotos();

        call.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {

                if (response.isSuccessful()) {

                    String message = "Successful!!";
                    Toast.makeText(PhotosActivity.this, message, Toast.LENGTH_SHORT).show();

                    photos = response.body();

                    CustomAdapter customAdapter = new CustomAdapter(photos,PhotosActivity.this);
                    binding.gridPhotos.setAdapter(customAdapter);
                } else {
                    String message = "An error occurred!";
                    Toast.makeText(PhotosActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) { }
        });
    }

    private void setListeners() {
        binding.buttonHome.setOnClickListener(v -> onBackPressed());
    }

    public class CustomAdapter extends BaseAdapter {

        private final List<Photos> photosList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<Photos> photosList, Context context) {
            this.photosList = photosList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return photosList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.row_grid_items,parent,false);
            }

            imageView = convertView.findViewById(R.id.imageView);
            textView = convertView.findViewById(R.id.textView);

            textView.setText(photosList.get(position).getTitle());

            //Glide library

//            Glide.with(context)
//                    .load(photosList.get(position).getUrl())
//                    .into(imageView);

            //Picasso library

            Picasso picasso = Picasso.get();
            picasso.setIndicatorsEnabled(true);

            picasso.load(photosList.get(position).getUrl())
                    .resize(150,150)
                    .centerCrop()
                    .into(imageView);

/*
            Picasso.get()
                    .load(photosList.get(position).getUrl())
                    .resize(150,150)
                    .centerCrop()
                    .into(imageView);
*/

            return convertView;
        }
    }
}