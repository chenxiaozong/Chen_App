package com.example.fragmenttest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LeftFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LeftFragment", "onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LeftFragment", "onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LeftFragment", "onpause");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("LeftFragment", "onattach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LeftFragment", "ondestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LeftFragment", "ondestroyView");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LeftFragment", "ondetach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, container, false);
        return view;
    }

}
