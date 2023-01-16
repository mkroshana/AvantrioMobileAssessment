package com.avantrio.mobileassessment.BottomNavigationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.avantrio.mobileassessment.R;
import com.avantrio.mobileassessment.Adapters.UsersAdapter;
import com.avantrio.mobileassessment.Models.UsersModel;
import com.avantrio.mobileassessment.Models.AuthenticationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersFragment extends Fragment
{
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<UsersModel> usersModels;
    private static String JSON_URL = "http://apps.avantrio.xyz:8010/api/users";
    private UsersAdapter usersAdapter;
    String token;

    public UsersFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //Enable options menu in the fragment
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        AuthenticationModel authenticationModel = AuthenticationModel.getInstance(getActivity().getApplication());
        token = authenticationModel.getUserToken().toString();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        //inflating menu
        inflater.inflate(R.menu.three_dot_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.usersList);
        searchView = view.findViewById(R.id.searchUsers);
        usersModels = new ArrayList<>();

        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                filterList(s);
                return true;
            }
        });
        extractUsers();
    }

    private void filterList(String s)
    {
        List<UsersModel> filteredList = new ArrayList<>();
        for (UsersModel usersModel : usersModels)
        {
            if (usersModel.getName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(usersModel);
            }
        }

        if (filteredList.isEmpty())
        {
            filteredList.clear();
            usersAdapter.setFilteredList(filteredList);
            Toast.makeText(getContext(), "No users found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            usersAdapter.setFilteredList(filteredList);
        }
    }

    private void extractUsers()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                for (int i = 0; i < response.length(); i++)
                {
                    try
                    {
                        JSONObject userObject = response.getJSONObject(i);
                        UsersModel usersModel = new UsersModel();
                        usersModel.setName(userObject.getString("name").toString());
                        usersModels.add(usersModel);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                usersAdapter = new UsersAdapter(getContext(), usersModels);
                recyclerView.setAdapter(usersAdapter);
            }
        }, new Response.ErrorListener()
        {
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

                //String auth = "Bearer " + token;
                headers.put("Authorization", "Bearer " + token );
                return headers;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setTag("tag");
        queue.add(jsonArrayRequest);
    }
}