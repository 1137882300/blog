package com.zhong.timer;

import javax.servlet.ServletContextListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**一天的间隔
 * Created by cc on 2021/4/26
 */
public class DayInterval implements ServletContextListener {
/*
    如果A大于等于B，那么A.after(B)返回true，A.before(B)返回false
 */

    /** time=0 就表示到了第二天了
     * 当前时间到第二天凌晨的秒数
     * @return
     */
    public static Long getCurrentTime2EarlyMorning(long currentTimeMillis){ // 100-60-0-100-60-0
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成这样就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long nextTimeInMillis = cal.getTimeInMillis();
        Long time = (nextTimeInMillis - currentTimeMillis) / 1000;
        System.out.println(time);
        return time;
    }

    public static void main(String[] args) {
        getCurrentTime2EarlyMorning(System.currentTimeMillis());
    }

    /**
     * 当前时间
     * @return date
     */
    public Date currentTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        return date;
    }

    /**
     * 第二天的时间
     * @return
     */
    public Date nextDay(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//1表示明天,-1表示昨天
        date = calendar.getTime(); //这个时间就是明天
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        System.out.println(dateString);
        return date;
    }

}
