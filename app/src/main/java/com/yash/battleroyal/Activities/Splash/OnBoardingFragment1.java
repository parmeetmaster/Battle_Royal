package com.yash.battleroyal.Activities.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yash.battleroyal.Activities.LoginActivity;
import com.yash.battleroyal.Activities.login.LoginNewActivity;
import com.yash.battleroyal.R;

import org.jetbrains.annotations.NotNull;

public class OnBoardingFragment1  extends Fragment {
    TextView skipTv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragement_onboarding1,container,false);

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
