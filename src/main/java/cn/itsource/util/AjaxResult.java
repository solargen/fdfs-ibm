package cn.itsource.util;

public class AjaxResult {

    private AjaxResult(){}

    private boolean success;
    private String message;
    private Object resultObj;

    public static AjaxResult me(){
        AjaxResult aj = new AjaxResult();
        aj.setSuccess(true);
        aj.setMessage("操作成功！");
        return aj;
    }

    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public AjaxResult setResultObj(Object resultObj) {
        this.resultObj = resultObj;
        return this;
    }
}
