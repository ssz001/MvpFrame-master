package com.ssz.baselibrary.widget.window.dialog;

import android.view.Gravity;

/**
 * @author : zsp
 * time : 2020 01 2020/1/6 15:52
 */
public final class DialogConfig {

    int height, width;
    boolean cancelAble;
    boolean canceledOnTouchOutside;
    int viewId;
    int gravity;
    float dimAmount;
    float horizonMargin;
    float verticalMargin;
    int offsetX;
    int offsetY;
    float alpha;
    int animation;

    private DialogConfig(Builder builder) {
        this.height = builder.height;
        this.width = builder.width;
        this.viewId = builder.viewId;
        this.cancelAble = builder.cancelAble;
        this.canceledOnTouchOutside = builder.canceledOnTouchOutside;
        this.gravity = builder.gravity;
        this.dimAmount = builder.dimAmount;
        this.horizonMargin = builder.horizonMargin;
        this.verticalMargin = builder.verticalMargin;
        this.offsetX = builder.offsetX;
        this.offsetY = builder.offsetY;
        this.alpha = builder.alpha;
        this.animation = builder.animation;
    }

    public static final class Builder {
        private int height, width;
        private boolean cancelAble;
        private boolean canceledOnTouchOutside;
        private int viewId;
        private float dimAmount = 0.6f;
        private float horizonMargin;
        private float verticalMargin;
        private int offsetX;
        private int offsetY;
        private float alpha = 1f;
        private int animation;
        private int gravity = Gravity.CENTER;

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
            if (!canceledOnTouchOutside) this.cancelAble = true;
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

        public Builder dimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder horizontalMargin(float horizonMargin) {
            this.horizonMargin = horizonMargin;
            return this;
        }

        public Builder verticalMargin(float verticalMargin) {
            this.verticalMargin = verticalMargin;
            return this;
        }

        public Builder offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder offsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public Builder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder animations(int animation) {
            this.animation = animation;
            return this;
        }

        public DialogConfig build() {
            return new DialogConfig(this);
        }
    }
}
