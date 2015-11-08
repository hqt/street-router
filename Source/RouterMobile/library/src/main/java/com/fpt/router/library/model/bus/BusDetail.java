package com.fpt.router.library.model.bus;

/**
 * Created by ngoan on 11/6/2015.
 */
public class BusDetail {
    public int image;
    public String _content;
    public String _numberBus;
    public BusDetail(){

    }
    public BusDetail(int image, String _content, String _numberBus){
        this.image = image;
        this._content = _content;
        this._numberBus = _numberBus;
    }
}
