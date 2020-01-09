package com.ssz.baselibrary.widget.dialog;

import android.view.Gravity;

/**
 * @author : zsp
 * time : 2020 01 2020/1/6 15:52
 */
public class DialogConfig {

    int height, width;
    boolean cancelAble;
    boolean canceledOnTouchOutside;
    int viewId;
    int gravity;

    private DialogConfig(Builder builder) {
        this.height = builder.height;
        this.width = builder.width;
        this.viewId = builder.viewId;
        this.cancelAble = builder.cancelAble;
        this.canceledOnTouchOutside = builder.canceledOnTouchOutside;
        this.gravity = builder.gravity;
    }

    public static class Builder {
        private int height, width;
        private boolean cancelAble;
        private boolean canceledOnTouchOutside;
        private int viewId;
        int gravity = Gravity.CENTER;

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            if (!canceledOnTouchOutside)this.cancelAble = true;
            return this;
        }

        public Builder cancelAble(boolean cancelAble) {
            this.cancelAble = cancelAble;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder view(int viewId) {
            this.viewId = viewId;
            return this;
        }

        public DialogConfig build() {
            return new DialogConfig(this);
        }
    }
}
