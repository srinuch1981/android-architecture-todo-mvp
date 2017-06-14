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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Helper to create or load the presenter based on lifecycle
 * @param <T> Presenter
 */
public final class PresenterLoader<T extends BasePresenter> extends Loader<T> {
    private static final String TAG = BasePresenterFragment.class.getSimpleName();
    private final PresenterFactory<T> mFactory;
    private T mPresenter;
    private final String mTag;

    public PresenterLoader(@NonNull Context context, @Nullable PresenterFactory<T> factory, @Nullable String tag) {
        super(context);
        mFactory = factory;
        // mWeakReference = new WeakReference<PresenterFactory<T>>(factory);
        mTag = tag;
        Log.d(TAG, "PresenterLoader Constructor mFactory=" + mFactory);
    }

    @Override
    protected void onStartLoading() {
        Log.i("loader", "onStartLoading-" + mTag);
        // if we already own a presenter instance, simply deliver it.
        if (mPresenter != null) {
            deliverResult(mPresenter);
            return;
        }

        // Otherwise, force a load
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        Log.i("loader", "onForceLoad-" + mTag);

        // Create the Presenter using the Factory
        if(mFactory != null) {
            mPresenter = mFactory.create();
        }
        // Deliver the result
        deliverResult(mPresenter);
    }

    @Override
    public void deliverResult(T data) {
        super.deliverResult(data);
        Log.i("loader", "deliverResult-" + mTag);
    }

    @Override
    protected void onStopLoading() {
        Log.i("loader", "onStopLoading-" + mTag);
    }

    @Override
    protected void onReset() {
        Log.i("loader", "onReset-" + mTag);
        if (mPresenter != null) {
            mPresenter.onDestroyed();
            mPresenter = null;
        }
    }
}
