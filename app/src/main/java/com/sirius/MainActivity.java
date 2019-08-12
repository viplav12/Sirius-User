package com.sirius;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements ICallListner {

    RecyclerView recyclerView;
    LinearLayoutManager Manager;

    String responseData = "";
    ICallListner serviceRequest;
    private RecyclerView.Adapter mAdapter;
    private List<CustomModal> userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceRequest = MainActivity.this;
        userObject = new ArrayList<CustomModal>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomAdapter(this, userObject);
        // set the adapter object to the Recyclerview
        recyclerView.setAdapter(mAdapter);

        callService();
    }

    public void callService() {
        ServiceInteractor.get("https://jsonplaceholder.typicode.com/users", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                responseData = "No internet Connection";
                serviceRequest.onError(responseData);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        responseData = response.body().string();
                        // jsonParsing
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject customModalObj = jsonArray.getJSONObject(i);

                            String userNameResponse = customModalObj.getString("username");
                            String userAddressStreet = customModalObj.getJSONObject("address").getString("street");
                            String userAddressSuit = customModalObj.getJSONObject("address").getString("suite");
                            String userAddressCity = customModalObj.getJSONObject("address").getString("city");
                            String userAddressZipcode = customModalObj.getJSONObject("address").getString("zipcode");
                            String userAddressGeoLat = customModalObj.getJSONObject("address").getJSONObject("geo").getString("lat");
                            String userAddressGeoLon = customModalObj.getJSONObject("address").getJSONObject("geo").getString("lng");
                            String completeAddress = userAddressStreet + ", " + userAddressSuit + "\n" +
                                    userAddressCity + ", " + userAddressZipcode + "\n" + userAddressGeoLat + " " + userAddressGeoLon;
                            String userId = customModalObj.getString("id");

                            CustomModal customModal = new CustomModal();
                            customModal.setUserName(userNameResponse);
                            customModal.setUserAddress(completeAddress);
                            customModal.setUserId(userId);
                            userObject.add(customModal);
                        }
                        serviceRequest.onResponse(responseData);
                        Log.i("Response", responseData);
                    } else {

                        responseData = "Something went Wrong";
                        serviceRequest.onError(responseData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    responseData = "Parsing Error";
                    serviceRequest.onError(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
