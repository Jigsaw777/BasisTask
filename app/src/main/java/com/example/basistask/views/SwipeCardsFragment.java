package com.example.basistask.views;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.basistask.R;
import com.example.basistask.adapters.RecyclerViewAdapter;
import com.example.basistask.data.remote.modelClasses.DataResponseModel;
import com.example.basistask.data.remote.modelClasses.DatumResponseModelClass;
import com.example.basistask.interfaces.FragmentCommunication;
import com.example.basistask.utils.Constants;
import com.example.basistask.viewmodels.SwipeViewModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;
import java.util.Objects;

import static com.yuyakaido.android.cardstackview.Direction.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class SwipeCardsFragment extends Fragment implements View.OnClickListener, CardStackListener {

    private View v;
    private FragmentCommunication fragmentCommunication;
    private SwipeViewModel swipeViewModel;
    private Context appContext;
    ImageView accept,reject,rewind,refreshBeg;
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    private int dataSize=0;

    //card pos keeps track of the card which is currently visible to the user
    private int cardPos=0;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext=context;
    }

    public SwipeCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_swipe_cards, container, false);
        initialiseViews();
        return v;
    }

    // this method is called to initialise variables
    private void initialiseViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            swipeViewModel= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SwipeViewModel.class);
        }
        accept=v.findViewById(R.id.accept_button);
        reject=v.findViewById(R.id.reject_button);
        rewind=v.findViewById(R.id.rewind_button);
        progressBar=v.findViewById(R.id.progressTracker);
        refreshBeg=v.findViewById(R.id.refresh_beginning_button);

        //card stack view converts the recycler view items to cards
        cardStackView=v.findViewById(R.id.swipe_cards);
        cardStackLayoutManager=new CardStackLayoutManager(appContext,this);
        cardStackLayoutManager.setVisibleCount(3);
        cardStackLayoutManager.setStackFrom(StackFrom.Top);
        cardStackView.setLayoutManager(cardStackLayoutManager);

        //progress dialog is used to show the user that the data is being fetched
        progressDialog=new ProgressDialog(appContext);
        progressDialog.setMessage("Fetching data ..... ");

        accept.setOnClickListener(this);
        reject.setOnClickListener(this);
        rewind.setOnClickListener(this);
        refreshBeg.setOnClickListener(this);

        showData();
    }

    private void showData(){
        progressDialog.show();
        swipeViewModel.getData().observe(getViewLifecycleOwner(),swipeDataObserver);
    }

    public void assignInterface(FragmentCommunication fragmentCommunication){
        this.fragmentCommunication=fragmentCommunication;
    }

    private Observer<List<DatumResponseModelClass>> swipeDataObserver = new androidx.lifecycle.Observer<List<DatumResponseModelClass>>() {
        @Override
        public void onChanged(List<DatumResponseModelClass> list) {
            if(!list.isEmpty()){
                progressDialog.dismiss();
                setupProgressBar(list.size());
                dataSize=list.size();
                Log.v("SwipeCardsFrag","Response observed");
                recyclerViewAdapter=new RecyclerViewAdapter(appContext,list);
                cardStackView.setAdapter(recyclerViewAdapter);
                cardPos=cardStackLayoutManager.getTopPosition();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(appContext,"Error fetching data",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //accept button swipes the card to right
            case R.id.accept_button:
                SwipeAnimationSetting settingRight = new SwipeAnimationSetting.Builder().setDirection(Right).build();
                cardStackLayoutManager.setSwipeAnimationSetting(settingRight);
                cardStackView.swipe();
                break;

                //reject button swipes the card to left
            case R.id.reject_button:
                SwipeAnimationSetting settingLeft = new SwipeAnimationSetting.Builder().setDirection(Left).build();
                cardStackLayoutManager.setSwipeAnimationSetting(settingLeft);
                cardStackView.swipe();
                break;

                // rewind button rewinds the last card
            case R.id.rewind_button:
                cardStackView.rewind();
                break;

                //refresh button takes us to the first card. it refreshes the card stack
            case R.id.refresh_beginning_button:
                cardStackView.smoothScrollToPosition(0);
                setProgress(0);
                break;
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        setProgress(cardPos+1);
        cardPos=cardStackLayoutManager.getTopPosition();
        if(cardPos == dataSize){
            showDialogBox();
        }
    }

    @Override
    public void onCardRewound() {
        cardPos=cardStackLayoutManager.getTopPosition();
        setProgress(cardPos);
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }

    //initialise the progress bar which tracks the card position
    private void setupProgressBar(int size){
        Log.v("SwipeCardsFragment","size of progressBar : "+size*10);
        progressBar.setMax(size*10);
    }

    //sets the progress of the progressbar
    private void setProgress(int n){
        Log.v("SwipeCardsFragment","setProgress : "+n*10);
        progressBar.setProgress(n*10);
    }

    //this dialog box is shown when all cards are over in the cardstack
    private void showDialogBox(){
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(appContext);
        alertDialogBuilder.setTitle("Cards are over");
        alertDialogBuilder.setMessage("You will be redirected to home screen");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragmentCommunication.startFragment(Constants.WELCOME_FRAGMENT);
            }
        });

        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
