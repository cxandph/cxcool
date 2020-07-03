package com.tfp.reg.studyreg.entities;

/**
 * Author: ph
 * Date: 2020/6/18
 * Time: 21:33
 * Description:
 */
public class DownLoadFileEntity {

    private String url;
    private String fileName;
    private String selfCookie;

    @Override
    public String toString() {
        return "DownLoadFileEntity{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", selfCookie='" + selfCookie + '\'' +
                '}';
    }

    public String getSelfCookie() {
        return selfCookie;
    }

    public void setSelfCookie(String selfCookie) {
        this.selfCookie = selfCookie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public DownLoadFileEntity(String url, String fileName,String selfCookie) {
        this.url = url;
        this.fileName = fileName;
        this.selfCookie =selfCookie;
    }

    public DownLoadFileEntity() {

    }



//    public static List<DownLoadFileEntity>
//    getDownLoadFiles(List<String> list, String saveDir, int startPage) {
//        if (ObjectUtil.isEmpty(list)) {
//            return null;
//        }
//        List<DownLoadFileEntity> rlist=  new ArrayList<>();
//        for (int i = 0; i <list.size() ; i++) {
//            rlist.add(new DownLoadFileEntity(list.get(i),
//                    saveDir + "第" + (startPage + i) + "集.mp4",null));
//        }
//        return rlist;
//    }

}
