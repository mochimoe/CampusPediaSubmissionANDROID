package com.dicoding.picodiploma.datasiswa;

public class DataSiswaReq {

    String siswaId;
    String siswaNama;
    String siswaEmail;
    String siswaNoHP;

    public DataSiswaReq() {
    }

    public DataSiswaReq(String siswaId, String siswaNama, String siswaEmail, String siswaNoHP) {
        this.siswaId = siswaId;
        this.siswaNama = siswaNama;
        this.siswaEmail = siswaEmail;
        this.siswaNoHP = siswaNoHP;
    }

    public String getSiswaId() {
        return siswaId;
    }

    public String getSiswaNama() {
        return siswaNama;
    }

    public String getSiswaEmail() {
        return siswaEmail;
    }

    public String getSiswaNoHP() {
        return siswaNoHP;
    }
}
