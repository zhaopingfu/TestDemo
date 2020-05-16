package com.zhaopf.testguava;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainBean {
    MainDataBean mainDataBean;

    public static class MainDataBean {
        List<BannerItemBean> bannerList;
    }

    public static class BannerItemBean {
        String url;
    }

    public static MainBean getTestData1() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String strUrl = Optional.fromNullable(MainBean.getTestData1())
                    .toJavaUtil()
                    .map(new Function<MainBean, String>() {
                        @Nullable
                        @Override
                        public String apply(@Nullable MainBean input) {
                            return input.mainDataBean.bannerList.get(0).url;
                        }
                    })
                    .orElse("默认值");
        }
        return null;
    }

    public static MainBean getTestData2() {
        return new MainBean();
    }

    public static MainBean getTestData3() {
        MainBean mainBean = new MainBean();
        mainBean.mainDataBean = new MainDataBean();
        return mainBean;
    }

    public static MainBean getTestData4() {
        MainBean mainBean = new MainBean();
        mainBean.mainDataBean = new MainDataBean();
        mainBean.mainDataBean.bannerList = new ArrayList<>();
        return mainBean;
    }

    public static List<MainBean.BannerItemBean> getTestData5() {
        return null;
    }
}