package com.example.basistask.views;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basistask.R;
import com.example.basistask.adapters.RecyclerViewAdapter;
import com.example.basistask.data.remote.modelClasses.DataResponseModel;
import com.example.basistask.interfaces.FragmentCommunication;
import com.example.basistask.viewmodels.SwipeViewModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.Objects;

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

    private void initialiseViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            swipeViewModel= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SwipeViewModel.class);
        }
        accept=v.findViewById(R.id.accept_button);
        reject=v.findViewById(R.id.reject_button);
        rewind=v.findViewById(R.id.rewind_button);
        refreshBeg=v.findViewById(R.id.refresh_beginning_button);
        cardStackView=v.findViewById(R.id.swipe_cards);
        cardStackLayoutManager=new CardStackLayoutManager(appContext,this);
        cardStackLayoutManager.setVisibleCount(3);
        cardStackLayoutManager.setStackFrom(StackFrom.Top);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        showData();
    }

    private void showData(){
        swipeViewModel.getDataListLiveData().observe(getViewLifecycleOwner(),swipeDataObserver);
        swipeViewModel.getData();
    }

    public void assignInterface(FragmentCommunication fragmentCommunication){
        this.fragmentCommunication=fragmentCommunication;
    }

    private Observer<DataResponseModel> swipeDataObserver = new androidx.lifecycle.Observer<DataResponseModel>() {
        @Override
        public void onChanged(DataResponseModel dataResponseModel) {
            if(dataResponseModel.getData() != null){
                Toast.makeText(appContext,"Please try again after sometime",Toast.LENGTH_LONG).show();
                recyclerViewAdapter=new RecyclerViewAdapter(appContext,dataResponseModel.getData());
                cardPos=cardStackLayoutManager.getTopPosition();
            }
            else {
                Toast.makeText(appContext,"Error fetching data",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accept_button:
                break;

            case R.id.reject_button:
                break;

            case R.id.rewind_button:
                break;

            case R.id.refresh_beginning_button:
                break;
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

    }

    @Override
    public void onCardRewound() {

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
}
