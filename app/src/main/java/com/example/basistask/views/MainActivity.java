package com.example.basistask.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.basistask.R;
import com.example.basistask.interfaces.FragmentCommunication;
import com.example.basistask.utils.Constants;
import com.example.basistask.viewmodels.SwipeViewModel;

//here main activity is used as container for the 2 fragments
public class MainActivity extends AppCompatActivity implements FragmentCommunication {

    ViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateSetup();
    }

    //initialise variables and open the welcome fragment
    private void initiateSetup(){
        viewModel= ViewModelProviders.of(this).get(SwipeViewModel.class);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        welcomeFragment.assignInterface(this);
        getSupportFragmentManager().beginTransaction().add(R.id.setup_container,welcomeFragment)
                .commitAllowingStateLoss();
    }

    // this method opens fragments according to the fragment names that is passed
    @Override
    public void startFragment(String fragmentName) {

        switch (fragmentName){

            case Constants.SWIPE_CARDS_FRAGMENT:
                SwipeCardsFragment swipeCardsFragment=new SwipeCardsFragment();
                swipeCardsFragment.assignInterface(this);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right
                        ,R.anim.slide_out_left).replace(R.id.setup_container,swipeCardsFragment)
                        .commitAllowingStateLoss();
                break;

            case Constants.WELCOME_FRAGMENT:
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                welcomeFragment.assignInterface(this);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left
                ,R.anim.slide_out_right).replace(R.id.setup_container,welcomeFragment)
                        .commitAllowingStateLoss();
                break;
        }
    }
}
