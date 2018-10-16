package com.buinak.curreq.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    private ListUtils() {

    }

    public static <T> List<List<T>> separateIntoLists(List<T> list, int amountPerList){
        List<List<T>> resultList = new ArrayList<>();
        List<T> tempList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            tempList.add(list.get(i));
            if ((i + 1) % amountPerList == 0){
                resultList.add(tempList);
                tempList = new ArrayList<>();
            }
            if ((i + 1) == list.size()){
                if (tempList.size() != 0) {
                    resultList.add(tempList);
                }
            }
        }

        return resultList;
    }
}
