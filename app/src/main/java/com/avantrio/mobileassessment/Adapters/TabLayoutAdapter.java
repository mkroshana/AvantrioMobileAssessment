package com.avantrio.mobileassessment.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.avantrio.mobileassessment.TabLayoutFragments.AlertFragment;
import com.avantrio.mobileassessment.TabLayoutFragments.AllFragment;
import com.avantrio.mobileassessment.TabLayoutFragments.LocationFragment;
import com.avantrio.mobileassessment.TabLayoutFragments.MessagesFragment;

public class TabLayoutAdapter extends FragmentStateAdapter
{

    public TabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
    {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch (position)
        {
            case 0 : return new AllFragment();
            case 1 : return new LocationFragment();
            case 2 : return new MessagesFragment();
            case 3 : return new AlertFragment();
        }
        return null;
    }

    @Override
    public int getItemCount()
    {
        return 4 ;
    }
}
