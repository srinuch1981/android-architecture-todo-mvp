/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.architecture.blueprints.todoapp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * When Used With Loaders no need to retainInstance(), View Get's attached to Presenter in OnResume and Detached onPause
 * <p>
 *     onActivityResult() get's called before onResume() so must attach onActivityResult() as well.
 *     In case Presenter has cache mechanism then we no need to attach in onActivityResult
 * </p>
 *
 * onPresenterDestroyed() can be used to cleanup any resource in UI layer which are passed to presenter while creation
 * Presenter - Can be clean its resources in onDestroy()
 * @param <P> Presenter to hold state for the View
 * @param <V> View to Represent UI
 */

public abstract class BasePresenterFragment<P extends BasePresenter<V>, V extends BaseView> extends Fragment {

    private static final String TAG = BasePresenterFragment.class.getSimpleName();

    private static final int LOADER_ID = 101;

    // boolean flag to avoid delivering the result twice. Calling initLoader in onActivityCreated makes
    // onLoadFinished will be called twice during configuration change.
    private boolean mDelivered = false;
    private BasePresenter<V> mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach-" + tag());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate-" + tag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView-" + tag());
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated-" + tag());
        Log.i(TAG, "onActivityCreated- " + this);
        // LoaderCallbacks as an object, so no hint regarding loader will be leak to the subclasses.
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args) {
                Log.i(TAG, "onCreateLoader-" + tag());
                return new PresenterLoader<>(getContext(), getPresenterFactory() , tag());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter) {
                Log.i(TAG, "onLoadFinished-" + tag());
                if (!mDelivered) {
                    BasePresenterFragment.this.mPresenter = presenter;
                    Log.i(TAG, "onLoadFinished- mPresenter=" + presenter);
                    mDelivered = true;
                    onPresenterPrepared(presenter);
                }
            }

            @Override
            public final void onLoaderReset(Loader<P> loader) {
                Log.i(TAG, "onLoaderReset-" + tag());
                BasePresenterFragment.this.mPresenter = null;
                onPresenterDestroyed();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume-" + tag());
        if(mPresenter != null) {
            Log.i(TAG, "onResume- mPresenter=" + mPresenter);
            mPresenter.onViewAttached(getPresenterView());
        }
    }

    @Override
    public void onPause() {
        if(mPresenter != null) {
            Log.i(TAG, "onPause- mPresenter=" + mPresenter);
            mPresenter.onViewDetached();
        }
        super.onPause();
        Log.i(TAG, "onPause-" + tag());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop-" + tag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy-" + tag());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView-" + tag());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult-" + tag());
        // onActivityResult called before onResume check lifecycle callbacks once >>> //TODO SRINI
        if(mPresenter != null) {
            Log.i(TAG, "onActivityResult- mPresenter=" + mPresenter);
            mPresenter.onViewAttached(getPresenterView());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract String tag();

    protected abstract PresenterFactory<P> getPresenterFactory();

    protected <P extends BasePresenter<V>> P getPresenter() {
        return null;
    }

    protected abstract void onPresenterPrepared(P presenter);

    protected void onPresenterDestroyed() {
        // hook for subclasses
    }

    // Override in case of fragment not implementing Presenter<View> interface
    protected V getPresenterView() {
        return (V) this;
    }
}
