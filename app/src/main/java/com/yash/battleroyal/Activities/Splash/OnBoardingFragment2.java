package com.yash.battleroyal.Activities.Splash;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yash.battleroyal.Activities.LoginActivity;
import com.yash.battleroyal.Activities.login.LoginNewActivity;
import com.yash.battleroyal.R;

public class OnBoardingFragment2  extends Fragment {
    TextView skipTv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragement_onboarding2,container,false);
        skipTv = root.findViewById(R.id.skipTv);
        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginNewActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
