package com.sirius;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class UserDetailActivity extends AppCompatActivity implements ICallListner {

    String postData = "";
    ICallListner serviceRequest;
    RecyclerView recyclerView;
    Context context;
    private TextView txt_userId_Detail, txt_userId, txt_title, txt_body, txt_userName;
    private List<CustomModal> userObject;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Details");

//        Intent intent = ((Activity) context).getIntent();
//        String userId = intent.getStringExtra("USER");

        txt_userName = (TextView) findViewById(R.id.txt_userName);
        txt_userId = (TextView) findViewById(R.id.txt_userId);

        detailPostCall(txt_userId.toString());

        txt_body = findViewById(R.id.txt_body);
        txt_title = findViewById(R.id.txt_title);
        txt_userId_Detail = findViewById(R.id.txt_userId_Detail);

        CustomModal userDetail = (CustomModal) getIntent().getSerializableExtra("USER");

        txt_userName.setText(userDetail.getUserName());
        txt_body.setText(userDetail.getUserBody());
        txt_title.setText(userDetail.getUserTitle());
        txt_userId_Detail.setText(userDetail.getUserId());
    }


    public void detailPostCall(String userid) {
        ServiceInteractor.get("https://jsonplaceholder.typicode.com/posts/", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                postData = "No internet Connection";
                serviceRequest.onError(postData);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        postData = response.body().string();
                        // jsonParsing
                        JSONArray jsonArray = new JSONArray(postData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject customModalObj = jsonArray.getJSONObject(i);
//                            String userNameResponse = customModalObj.getString("username");

                            String userID = customModalObj.getString("id");
                            String userTitle = customModalObj.getString("title");
                            String userBody = customModalObj.getString("body");

                            CustomModal customModal = new CustomModal();
//                            customModal.setUserName(userNameResponse);
                            customModal.setUserId(userID);
                            customModal.setUserTitle(userTitle);
                            customModal.setUserBody(userBody);

                            userObject.add(customModal);
                        }
                        serviceRequest.onResponse(postData);
                        Log.i("Response", postData);
                    } else {
                        postData = "Something went Wrong";
                        serviceRequest.onError(postData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    serviceRequest.onError(postData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(String response) {

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onError(String errorResponse) {

        Log.e(TAG, "Inside Error!!" + errorResponse);
    }
}
