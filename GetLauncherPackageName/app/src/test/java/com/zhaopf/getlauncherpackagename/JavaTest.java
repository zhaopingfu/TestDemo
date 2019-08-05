package com.zhaopf.getlauncherpackagename;

import org.junit.Test;

import java.util.Calendar;

/**
 * @author PingFu.Zhao
 * 2019/7/31
 */
public class JavaTest {

    @Test
    public void test() {
        // 上次记录的时间
        Calendar oldCalendar = Calendar.getInstance();
        // oldCalendar.setTimeInMillis(1564588799000L);
        oldCalendar.setTimeInMillis(1564588700000L);
        // 现在的时间
        Calendar currCalendar = Calendar.getInstance();
        // 判断是否是同一天
        boolean result = oldCalendar.get(Calendar.YEAR) == currCalendar.get(Calendar.YEAR)
                && oldCalendar.get(Calendar.MONTH) == currCalendar.get(Calendar.MONTH)
                && oldCalendar.get(Calendar.DATE) == currCalendar.get(Calendar.DATE);
        System.out.println(result);
    }

    @Test
    public void test2() {
        checkTestBean(new TestBean());
        checkTestBean(null);
    }

    private void checkTestBean(Object testBean) {
        if (testBean instanceof TestBean) {
            System.out.println(testBean + " instance TestBean");
        } else {
            System.out.println(testBean + " not instance TestBean");
        }
    }

    static class TestBean {
    }
}