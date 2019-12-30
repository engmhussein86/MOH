package com.moh.app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-3 on 3/28/2017.
 */
public class Users {

    private List<ResultBean> result = new ArrayList<>();

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * ID_NO : 801064759
         * CARD_NO : 172654
         * DEP_ID_NO : 0
         * FULL_NAME_AR : مريم جمال محمد عفانة
         * DEP_NAME : -
         * REL_CODE : 0
         * REL_TYPE_DESC : -
         * BIRTH_DATE : 19860524
         * INS_STATUS : 1
         * INS_STATUS_DESC : فعال
         * CLINIC_CODE : 2836
         * CLINIC_CODE_DESC : الرمـــال
         * INS_TYPE : 8
         * INS_TYPE_DESC : موظف حكومة
         * START_DATE : 20120101
         * EXP_DATE : 20170630
         */

        private String ID_NO;
        private String CARD_NO;
        private String DEP_ID_NO;
        private String FULL_NAME_AR;
        private String DEP_NAME;
        private String REL_CODE;
        private String REL_TYPE_DESC;
        private String BIRTH_DATE;
        private String INS_STATUS;
        private String INS_STATUS_DESC;
        private String CLINIC_CODE;
        private String CLINIC_CODE_DESC;
        private String INS_TYPE;
        private String INS_TYPE_DESC;
        private String START_DATE;
        private String EXP_DATE;

        public String getID_NO() {
            return ID_NO;
        }

        public void setID_NO(String ID_NO) {
            this.ID_NO = ID_NO;
        }

        public String getCARD_NO() {
            return CARD_NO;
        }

        public void setCARD_NO(String CARD_NO) {
            this.CARD_NO = CARD_NO;
        }

        public String getDEP_ID_NO() {
            return DEP_ID_NO;
        }

        public void setDEP_ID_NO(String DEP_ID_NO) {
            this.DEP_ID_NO = DEP_ID_NO;
        }

        public String getFULL_NAME_AR() {
            return FULL_NAME_AR;
        }

        public void setFULL_NAME_AR(String FULL_NAME_AR) {
            this.FULL_NAME_AR = FULL_NAME_AR;
        }

        public String getDEP_NAME() {
            return DEP_NAME;
        }

        public void setDEP_NAME(String DEP_NAME) {
            this.DEP_NAME = DEP_NAME;
        }

        public String getREL_CODE() {
            return REL_CODE;
        }

        public void setREL_CODE(String REL_CODE) {
            this.REL_CODE = REL_CODE;
        }

        public String getREL_TYPE_DESC() {
            return REL_TYPE_DESC;
        }

        public void setREL_TYPE_DESC(String REL_TYPE_DESC) {
            this.REL_TYPE_DESC = REL_TYPE_DESC;
        }

        public String getBIRTH_DATE() {
            return BIRTH_DATE;
        }

        public void setBIRTH_DATE(String BIRTH_DATE) {
            this.BIRTH_DATE = BIRTH_DATE;
        }

        public String getINS_STATUS() {
            return INS_STATUS;
        }

        public void setINS_STATUS(String INS_STATUS) {
            this.INS_STATUS = INS_STATUS;
        }

        public String getINS_STATUS_DESC() {
            return INS_STATUS_DESC;
        }

        public void setINS_STATUS_DESC(String INS_STATUS_DESC) {
            this.INS_STATUS_DESC = INS_STATUS_DESC;
        }

        public String getCLINIC_CODE() {
            return CLINIC_CODE;
        }

        public void setCLINIC_CODE(String CLINIC_CODE) {
            this.CLINIC_CODE = CLINIC_CODE;
        }

        public String getCLINIC_CODE_DESC() {
            return CLINIC_CODE_DESC;
        }

        public void setCLINIC_CODE_DESC(String CLINIC_CODE_DESC) {
            this.CLINIC_CODE_DESC = CLINIC_CODE_DESC;
        }

        public String getINS_TYPE() {
            return INS_TYPE;
        }

        public void setINS_TYPE(String INS_TYPE) {
            this.INS_TYPE = INS_TYPE;
        }

        public String getINS_TYPE_DESC() {
            return INS_TYPE_DESC;
        }

        public void setINS_TYPE_DESC(String INS_TYPE_DESC) {
            this.INS_TYPE_DESC = INS_TYPE_DESC;
        }

        public String getSTART_DATE() {
            return START_DATE;
        }

        public void setSTART_DATE(String START_DATE) {
            this.START_DATE = START_DATE;
        }

        public String getEXP_DATE() {
            return EXP_DATE;
        }

        public void setEXP_DATE(String EXP_DATE) {
            this.EXP_DATE = EXP_DATE;
        }
    }
}
