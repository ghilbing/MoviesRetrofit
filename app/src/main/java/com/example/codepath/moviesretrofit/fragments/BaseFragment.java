package com.example.codepath.moviesretrofit.fragments;

/**
 * Created by gretel on 9/7/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{


private Toast mToast;

@CallSuper
@Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        }

@CallSuper
@Override public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
        }

protected void showToast(String message) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mToast.show();
        }

protected void showToast(@StringRes int resId) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
        mToast.show();
        }

protected List<Object> getModules() {
        return Collections.emptyList();
        }
        }