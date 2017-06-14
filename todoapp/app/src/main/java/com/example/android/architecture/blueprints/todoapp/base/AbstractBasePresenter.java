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


import java.util.LinkedList;
import java.util.Queue;

/**
 * Provides base functionality to cache the View commands and executes when attached.
 * At first time attached will delegate initial task to actual implementer
 *
 * @param <V> View to Represent UI
 */
public class AbstractBasePresenter<V extends BaseView> implements BasePresenter<V> {

    /**
     * Holds All commands to process when view attached
     */
    private Queue<ViewCommand<V>> mCommandQueue = new LinkedList<>();
    /**
     * Holds recent Command which need to be re-execute when attached, If not present command Queue
     * i.e It got executed but executes again when view attached only when not present in Queue
     */
    private ViewCommand<V> mCommandToRedeliver = null;

    private V mView;
    /**
     * To check first time view attachment or not.
     */
    private boolean mViewAttachedBefore = false;

    @Override
    public void onViewAttached(V view) {
        this.mView = view;
        executeCommandQueue(true);
        /**
         * To execute Initial job's in presenter
         */
        if (!this.mViewAttachedBefore) {
            onFirstUIAttachment();
            this.mViewAttachedBefore = true;
        }
    }

    @Override
    public void onViewDetached() {
        this.mView = null;
    }

    @Override
    public void onDestroyed() {
        /**
         * Clean acquired resources as we are leaving
         */
    }

    protected void onFirstUIAttachment() {
    }

    /**
     * This will add command to Queue and process when View attached
     * @param command command to execute
     * @param redeliverOnAttachment to Re-execute on View Attachment
     */
    protected void execute(ViewCommand<V> command, boolean redeliverOnAttachment) {
        mCommandQueue.add(command);
        executeCommandQueue(false);
        if (redeliverOnAttachment) {
            mCommandToRedeliver = command;
        }
    }

    /**
     * This will add command to Queue and execute only once when View attached.
     * @param command command to execute
     */
    protected void executeOnce(ViewCommand<V> command) {
        execute(command, false);
    }

    /**
     * This will add command to Queue and execute when View attached.
     * Also re-execute when we attached and the command not in Queue
     * @param command command to execute
     *
     * <b>Note: Only 1 command can be Re-executed (Last command)</b>
     */
    protected void executeRepeat(ViewCommand<V> command) {
        execute(command, true);
    }

    private void executeCommandQueue(boolean attachment) {
        if (this.mView != null) {
            boolean commandToRedeliverExecuted = false;
            ViewCommand<V> command;
            while ((command = mCommandQueue.poll()) != null) {
                command.execute(this.mView);
                if (command == mCommandToRedeliver) {
                    commandToRedeliverExecuted = true;
                }
            }
            if (attachment && !commandToRedeliverExecuted && mCommandToRedeliver != null) {
                mCommandToRedeliver.execute(mView);
            }
        }
    }

    /**
     * Generic View Command to delegate execution
     * @param <V> view
     */
    public interface ViewCommand<V> {
        void execute(V view);
    }
}
