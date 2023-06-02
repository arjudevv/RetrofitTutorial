package com.example.apituto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apituto.api.ApiClient;
import com.example.apituto.api.ApiInterface;
import com.example.apituto.models.Post;
import com.example.apituto.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        getPosts();
//        createPosts();
//        updatePost();
//        deletePost();
        setListeners();
    }

    private void deletePost() {

        Call<Void> call = apiInterface.deletePost(6);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                binding.textResults.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                binding.textResults.setText(t.getMessage());
            }
        });

    }

    private void updatePost() {

        Post post = new Post(1, null, "Cristiano Ronaldo");

        Map<String, String> map = new HashMap<>();
        map.put("Header1", "cr7");
        map.put("Header2", "lm10");

        Call<Post> call = apiInterface.patchPost(map,12, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    binding.textResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Subject: " + postResponse.getSubject() + "\n\n";

                binding.textResults.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                binding.textResults.setText(t.getMessage());
            }
        });

    }

    private void createPosts() {

        Post post = new Post(28,"New Title","Blaah Blaah Blaaah...");

        Map<String, String> map = new HashMap<>();
        map.put("userId","14");
        map.put("title","Case");

        Call<Post> call = apiInterface.createPost(map);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    binding.textResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Subject: " + postResponse.getSubject() + "\n\n";

                binding.textResults.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                binding.textResults.setText(t.getMessage());
            }
        });

    }

    private void setListeners() {
        binding.buttonComments.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CommentsActivity.class)));
        binding.buttonPhotos.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PhotosActivity.class)));
    }

    private void getPosts() {

        Map<String,String> map = new HashMap<>();
        map.put("userId","2");
        map.put("_sort","id");
        map.put("_order","desc");

        Call<List<Post>> call = apiInterface.getPosts(map);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()) {
                    binding.textResults.setText("Code: "+ response.code());
                    return;
                }

                List<Post> Post = response.body();

                for (Post post : Post) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Subject: " + post.getSubject() + "\n\n";

                    binding.textResults.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                binding.textResults.setText(t.getMessage());
            }
        });
    }
}