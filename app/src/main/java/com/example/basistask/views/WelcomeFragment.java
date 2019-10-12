package com.example.basistask.views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.basistask.R;
import com.example.basistask.interfaces.FragmentCommunication;
import com.example.basistask.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private View v;
    private FragmentCommunication fragmentCommunication;
    private Button b;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_welcome, container, false);
        initialiseViews();
        return v;
    }

    public void assignInterface(FragmentCommunication fragmentCommunication){
        this.fragmentCommunication = fragmentCommunication;
    }

    private void initialiseViews(){
        b=v.findViewById(R.id.button);

        //this button takes us to the swip cards screen
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunication.startFragment(Constants.SWIPE_CARDS_FRAGMENT);
            }
        });
    }

}
