package com.example.roomhoursuser.SliderScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.roomhoursuser.SliderScreen.SliderOneFragment;
import com.example.roomhoursuser.SliderScreen.SliderThreeFragment;
import com.example.roomhoursuser.SliderScreen.SliderTwoFragment;

public class OuterSliderAdapter extends FragmentPagerAdapter {

    public OuterSliderAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
           /* case 0:
                SliderOneFragment sliderOneFragment = new SliderOneFragment();
                return sliderOneFragment;*/
            case 0:
                SliderTwoFragment sliderTwoFragment = new SliderTwoFragment();
                return sliderTwoFragment;
            case 1:
                SliderThreeFragment sliderThreeFragment = new SliderThreeFragment();
                return sliderThreeFragment;
            case 2:
                SliderFiveFragment sliderFiveFragment = new SliderFiveFragment();
                return sliderFiveFragment;
              /*  case 4:
                    SliderFourFragment sliderFourFragment = new SliderFourFragment();
                return sliderFourFragment;*/

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
