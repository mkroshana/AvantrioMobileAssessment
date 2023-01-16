package com.avantrio.mobileassessment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantrio.mobileassessment.Adapters.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

public class UserLogsFragment extends Fragment
{
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    public UserLogsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_logs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.tabLayoutView);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        TextView txtName = view.findViewById(R.id.txtUsername);
        ImageView imgSort = view.findViewById(R.id.imgSort);

        FragmentManager fragmentManager = getParentFragmentManager();
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(tabLayoutAdapter);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            txtName.setText(bundle.getString("userName"));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        imgBack.setOnClickListener(view12 -> getActivity().onBackPressed());

        imgSort.setOnClickListener(view1 ->
        {

        });
    }
}