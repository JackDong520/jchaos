package javabean;

public class SocketInfo {
    private int ResultCode;
    private String ResultMsg;

    public SocketInfo(int resultCode, String resultMsg) {
        ResultCode = resultCode;
        ResultMsg = resultMsg;
    }

    public SocketInfo() {
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        ResultMsg = resultMsg;
    }
}
