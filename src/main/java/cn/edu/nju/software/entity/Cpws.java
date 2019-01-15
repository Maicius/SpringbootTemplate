package cn.edu.nju.software.entity;

/**
 * author: maicius
 * date: 2018/12/11
 * description:
 */
public class Cpws {
    private String file_name;
    private String ajjbqk;

    @Override
    public String toString(){
        return "cpws{" +
                "file_name='" + file_name + "\'" +
                ", ajjbqk='" + ajjbqk + "\'" +
                "}";
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getAjjbqk() {
        return ajjbqk;
    }

    public void setAjjbqk(String ajjbqk) {
        this.ajjbqk = ajjbqk;
    }
}
