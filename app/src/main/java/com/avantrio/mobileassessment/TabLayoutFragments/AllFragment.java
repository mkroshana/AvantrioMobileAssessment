package com.avantrio.mobileassessment.TabLayoutFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.avantrio.mobileassessment.R;
import com.avantrio.mobileassessment.SharedPreferenceClass;
import com.avantrio.mobileassessment.Adapters.UserLogsAdapter;
import com.avantrio.mobileassessment.Models.UserLogsModel;
import com.avantrio.mobileassessment.Models.AuthenticationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllFragment extends Fragment
{
    private RecyclerView recyclerView;
    private List<UserLogsModel> userLogsModels;
    private static String BASE_URL = "http://apps.avantrio.xyz:8010/api/user/";
    private UserLogsAdapter userLogsAdapter;
    String token;
    int userPosition;
    SharedPreferenceClass sharedPreferenceClass;
    public AllFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AuthenticationModel authenticationModel = AuthenticationModel.getInstance(getActivity().getApplication());
        token = authenticationModel.getUserToken().toString();
        userPosition = authenticationModel.getUserLogsPosition();
        sharedPreferenceClass = new SharedPreferenceClass(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.userLogsList);
        userLogsModels = new ArrayList<>();

        extractUserLogs();
    }

    private void extractUserLogs()
    {
        int position = sharedPreferenceClass.getValue_int("adapterPosition");
        String JSON_URL = BASE_URL + String.valueOf(position+1) + "/logs";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JSON_URL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                {
                    try
                    {
                        JSONArray logs = response.getJSONArray("logs");
                        UserLogsModel userLogsModel = new UserLogsModel();
                        for (int i = 0; i < logs.length(); i++)
                        {
                            JSONObject log = logs.getJSONObject(i);

                            LocalDate date = LocalDate.parse(log.getString("date"));
                            userLogsModel.setDate(date.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));

                            LocalTime time = LocalTime.parse(log.getString("time"));
                            userLogsModel.setTime(time.format(DateTimeFormatter.ofPattern("HH:mm a")));

                            if (log.getString("alert_view") == "true")
                            {
                                userLogsModel.setAlertView("On");
                            }
                            else
                            {
                                userLogsModel.setAlertView("Off");
                            }

                            userLogsModel.setLocationLat(Double.parseDouble(log.getString("latitude")));
                            userLogsModel.setLocationLong(Double.parseDouble(log.getString("longitude")));

                            userLogsModels.add(userLogsModel);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                userLogsAdapter = new UserLogsAdapter(getContext(), userLogsModels);
                recyclerView.setAdapter(userLogsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("tag","onErrorResponse: " + error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token );
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag("tag");
        queue.add(jsonObjectRequest);
    }
}