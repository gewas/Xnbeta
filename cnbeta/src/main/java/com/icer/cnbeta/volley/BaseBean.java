package com.icer.cnbeta.volley;

/**
 * Created by icer on 2015-09-25.
 */
public class BaseBean {
    public static final String RESULT_SUCCESS = "success";
    public String status;

    public boolean isSucceed() {
        return RESULT_SUCCESS.equals(status);
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status='" + status + '\'' +
                '}'+" /\\ ";
    }
}
