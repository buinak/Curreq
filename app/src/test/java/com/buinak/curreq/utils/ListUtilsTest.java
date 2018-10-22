package com.buinak.curreq.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListUtilsTest {

    @Test
    public void separateIntoLists() {
        List<String> firstList = Arrays.asList("1", "2", "3", "4", "5");
        List<List<String>> resultList = ListUtils.separateIntoLists(firstList, 2);
        assertThat(resultList.size(), is(3));
    }
}