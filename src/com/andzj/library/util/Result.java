package com.andzj.library.util;


public class Result<T> {

    private boolean state;// 是否成功标志

    private T data;// 成功时返回的数据/错误信息

	
    public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    public Result() {
    }

    public Result(boolean state, T data) {
        this.setState(state);
        this.data = data;
    }

}
