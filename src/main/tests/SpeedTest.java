package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> idsShort1 = new HashSet<>();
        long timeShort1 = getTimeToGetIds(shortener1, origStrings, idsShort1);
        Set<Long> idsShort2 = new HashSet<>();
        long timeShort2 = getTimeToGetIds(shortener2, origStrings, idsShort2);
        Assert.assertTrue((timeShort1 > timeShort2));

        Set<String> strShot1 = new HashSet<>();
        long timeToGetStr1 = getTimeToGetStrings(shortener1, idsShort1, strShot1);
        Set<String> strShot2 = new HashSet<>();
        long timeToGetStr2 = getTimeToGetStrings(shortener2, idsShort2, strShot2);
        Assert.assertEquals(timeToGetStr1, timeToGetStr2, 30);
    }

    public long getTimeToGetIds(Shortener shortener,
                                Set<String> strings, Set<Long> ids) {
        Date startTime = new Date();
        for (String str : strings) {
            ids.add(shortener.getId(str));
        }
        Date endTime = new Date();
        return (endTime.getTime() - startTime.getTime());
    }

    public long getTimeToGetStrings(Shortener shortener,
                                    Set<Long> ids, Set<String> strings) {
        Date startTime = new Date();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }
        Date endTime = new Date();
        return (endTime.getTime() - startTime.getTime());
    }

}
