package com.emergency.a108_publicapp;

import io.realm.RealmObject;

/**
 * Created by admin on 01-04-2017.
 */

public class TrustContact extends RealmObject{


        private String name , number;

        public TrustContact() {
        }

        public TrustContact(String name, String number) {
            this.name=name;
            this.number =number;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }





        public void setNumber(String genre) {
            this.number = number;
        }
    }


