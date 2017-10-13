package com.taoerxue.qo;

/**
 * Created by lizhihui on 2017-03-23 16:36.
 */
public class AppUserQuery extends Query {

    private Integer studentTypeId;
    private Integer expectation;

    public Integer getStudentTypeId() {
        return studentTypeId;
    }

    public void setStudentTypeId(Integer studentTypeId) {
        this.studentTypeId = studentTypeId;
    }

    public Integer getExpectation() {
        return expectation;
    }

    public void setExpectation(Integer expectation) {
        this.expectation = expectation;
    }


}
