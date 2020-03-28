package com.rozsa.samples.splash;

import com.rozsa.crow.screen.BaseScreen;
import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;

class SplashScreen extends BaseScreen<SplashViewType, SplashViewType> {
    SplashScreen(Size size) {
        super(size);

//        Size tileSizeFactor = config.getTileSizeFactor().plus(config.getAreaSizeTiles());

        Rect rect = new Rect(0, 0, size.getWidth(), size.getHeight());
        setupViews(rect);
    }

    private void setupViews(Rect rect) {
        BaseView staticView = new StaticView(rect);
        addView(SplashViewType.STATIC, staticView);
        addViewGroup(SplashViewType.STATIC, SplashViewType.STATIC);

        displayViewGroup(SplashViewType.STATIC);
    }

    public void draw(){
        super.draw();
    }

//    StaticView getStaticView() {
//        return (StaticView)getView(SplashViewType.STATIC);
//    }
}
