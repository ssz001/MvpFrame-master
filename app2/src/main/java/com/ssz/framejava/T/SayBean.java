package com.ssz.framejava.T;


import com.ssz.framejava.model.remote.net.tool.javabean.JavaBean;

/**
 * @author : zsp
 * time : 2019 11 2019/11/12 15:45
 */
public class SayBean extends JavaBean {
    /**
     * sid : 29920895
     * text : 这个项目我投资了
     * type : video
     * thumbnail : http://wimg.spriteapp.cn/picture/2019/1110/5dc7d596ea844_wpd.jpg
     * video : http://uvideo.spriteapp.cn/video/2019/1110/5dc7d596ea844_wpd.mp4
     * images : null
     * up : 126
     * down : 20
     * forward : 1
     * comment : 15
     * uid : 15121810
     * name : 特仑苏_
     * header : http://wimg.spriteapp.cn/profile/large/2019/07/22/5d3560ce2bd6e_mini.jpg
     * top_comments_content : 喷的是不是屎渣渣
     * top_comments_voiceuri :
     * top_comments_uid : 19695834
     * top_comments_name : 雪鹰领主
     * top_comments_header : http://qzapp.qlogo.cn/qzapp/100336987/DF369171DA1458AF51B0BDF8275FB7FE/100
     * passtime : 2019-11-12 02:53:02
     */

    private String sid;
    private String text;
    private String type;
    private String thumbnail;
    private String video;
    private Object images;
    private String up;
    private String down;
    private String forward;
    private String comment;
    private String uid;
    private String name;
    private String header;
    private String top_comments_content;
    private String top_comments_voiceuri;
    private String top_comments_uid;
    private String top_comments_name;
    private String top_comments_header;
    private String passtime;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTop_comments_content() {
        return top_comments_content;
    }

    public void setTop_comments_content(String top_comments_content) {
        this.top_comments_content = top_comments_content;
    }

    public String getTop_comments_voiceuri() {
        return top_comments_voiceuri;
    }

    public void setTop_comments_voiceuri(String top_comments_voiceuri) {
        this.top_comments_voiceuri = top_comments_voiceuri;
    }

    public String getTop_comments_uid() {
        return top_comments_uid;
    }

    public void setTop_comments_uid(String top_comments_uid) {
        this.top_comments_uid = top_comments_uid;
    }

    public String getTop_comments_name() {
        return top_comments_name;
    }

    public void setTop_comments_name(String top_comments_name) {
        this.top_comments_name = top_comments_name;
    }

    public String getTop_comments_header() {
        return top_comments_header;
    }

    public void setTop_comments_header(String top_comments_header) {
        this.top_comments_header = top_comments_header;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }

    @Override
    public String toString() {
        return "SayBean{" +
                "sid='" + sid + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", video='" + video + '\'' +
                ", images=" + images +
                ", up='" + up + '\'' +
                ", down='" + down + '\'' +
                ", forward='" + forward + '\'' +
                ", comment='" + comment + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", header='" + header + '\'' +
                ", top_comments_content='" + top_comments_content + '\'' +
                ", top_comments_voiceuri='" + top_comments_voiceuri + '\'' +
                ", top_comments_uid='" + top_comments_uid + '\'' +
                ", top_comments_name='" + top_comments_name + '\'' +
                ", top_comments_header='" + top_comments_header + '\'' +
                ", passtime='" + passtime + '\'' +
                '}';
    }
}
