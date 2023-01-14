package com.avantrio.mobileassessment;

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
import com.avantrio.mobileassessment.models.Model;

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
    private List<User> users;
    private static String JSON_URL = "http://apps.avantrio.xyz:8010/api/users";
    private Adapter adapter;
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
        Model model = Model.getInstance(getActivity().getApplication());
        token = model.getUserToken().toString();
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
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.usersList);
        searchView = view.findViewById(R.id.searchUsers);
        users = new ArrayList<>();

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
        List<User> filteredList = new ArrayList<>();
        for (User user : users)
        {
            if (user.getName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(user);
            }
        }

        if (filteredList.isEmpty())
        {
            filteredList.clear();
            adapter.setFilteredList(filteredList);
            Toast.makeText(getContext(), "No users found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapter.setFilteredList(filteredList);
        }
    }

    private void extractUsers()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject userObject = response.getJSONObject(i);
                        User user = new User();
                        user.setName(userObject.getString("name").toString());

                        users.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new Adapter(getContext(),users);
                recyclerView.setAdapter(adapter);
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

                //String auth = "Bearer " + token;
                headers.put("Authorization", "Bearer " + token );
                Log.d("Token",token);
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