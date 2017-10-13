package com.taoerxue.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-29 13:35.
 */
public class BaseService {
     List<Integer> expectationToEducationType(List<Integer> expectationList) {
        //学业
        Integer[] study = new Integer[]{1, 7};
        //学业开发
        Integer[] intellectualDevelopment = new Integer[]{6};
        //兴趣爱好
        Integer[] interestsAndHobbies = new Integer[]{2, 3, 4, 5};

        List<Integer> typeList = new ArrayList<>();

        for (Integer expectation : expectationList) {
            switch (expectation) {
                case 1:
                    typeList.addAll(Arrays.asList(study));
                    break;
                case 2:
                    typeList.addAll(Arrays.asList(intellectualDevelopment));
                    break;
                case 4:
                    typeList.addAll(Arrays.asList(interestsAndHobbies));
                    break;
                default:
                    break;
            }
        }
        return typeList;
    }
}
