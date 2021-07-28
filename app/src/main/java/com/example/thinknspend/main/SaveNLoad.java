package com.example.thinknspend.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.thinknspend.ui.main.MainFragment;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import static com.example.thinknspend.ui.main.MainFragment.balance;
import static com.example.thinknspend.ui.main.MainFragment.fun_balance;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_all;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_fun;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_should;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_useless;
import static com.example.thinknspend.ui.main.MainFragment.total;
import static com.example.thinknspend.ui.main.MainFragment.totalSpendings;

public class SaveNLoad implements Externalizable {

   // private  Float balance;
   // private  Float fun_balance;

  /*  private  ArrayList<Spending> spendingsList = new ArrayList<>();
    private  ArrayList<Spending> spendingsList_should = new ArrayList<>();
    private  ArrayList<Spending> spendingsList_fun = new ArrayList<>();
    private  ArrayList<Spending> spendingsList_useless = new ArrayList<>();
    private  ArrayList<Spending> spendingsList_all = new ArrayList<>();
    private  ArrayList<Float> totalSpendings = new ArrayList<>();*/



    public SaveNLoad() //empty default constructor
    {

        // Display message
        System.out.println(
                "Public no-argument constructor");
    }

    public SaveNLoad(ArrayList<Spending> spendingsList, ArrayList<Spending> spendingsList_should, ArrayList<Spending> spendingsList_fun, ArrayList<Spending> spendingsList_useless, ArrayList<Spending> spendingsList_all, Float balance, Float fun_balance){


        MainFragment.spendingsList = spendingsList;
        MainFragment.spendingsList_should = spendingsList_should;
        MainFragment.spendingsList_fun = spendingsList_fun;
        MainFragment.spendingsList_useless = spendingsList_useless;
        MainFragment.spendingsList_all = spendingsList_all;
        MainFragment.balance = balance;
        MainFragment.fun_balance =  fun_balance;
        //MainFragment.totalSpendings = totalSpendings;

    }




    // Implementing write external method
    public void writeExternal(ObjectOutput out)
            throws IOException
    {
     //   if(spendingsList != null && spendingsList.size() >= 0) {
            out.writeObject(spendingsList);

    //    }
    //    if(spendingsList_should != null && spendingsList_should.size() >= 0) {
            out.writeObject(spendingsList_should);
   //     }
   //     if(spendingsList_fun != null && spendingsList_fun.size() >= 0) {
            out.writeObject(spendingsList_fun);
  //      }
  //      if(spendingsList_useless != null && spendingsList_useless.size() >= 0) {
            out.writeObject(spendingsList_useless);
   //     }
  //      if(spendingsList_all != null && spendingsList_all.size() >= 0) {
            out.writeObject(spendingsList_all);
  //      }

 //       if(totalSpendings != null && totalSpendings.size() >= 0){
           // out.writeObject(totalSpendings);
    //    }

        if(balance != null) {
            out.writeObject(balance.toString());
        } else{
            out.writeObject("0.0");
        }
        if(fun_balance != null) {
            out.writeObject(fun_balance.toString());
        }else{
            out.writeObject("0.0");
        }


    }

    // Implementing readExternal method
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException
    {
    //    if(spendingsList != null && spendingsList.size() >= 1) {

            spendingsList = (ArrayList<Spending>) in.readObject();
   //     }
    //    if(spendingsList_should != null && spendingsList_should.size() >= 1) {
            spendingsList_should = (ArrayList<Spending>) in.readObject();
   //     }
    //    if(spendingsList_fun != null && spendingsList_fun.size() >= 1) {
            spendingsList_fun = (ArrayList<Spending>) in.readObject();
  //      }
  //      if(spendingsList_useless != null && spendingsList_useless.size() >= 1) {
            spendingsList_useless = (ArrayList<Spending>) in.readObject();
   //     }
  //      if(spendingsList_all != null && spendingsList_all.size() >= 1) {
            spendingsList_all = (ArrayList<Spending>) in.readObject();
     //   }

  //      if(totalSpendings != null && totalSpendings.size() > 1){
        //    totalSpendings = (ArrayList<Float>) in.readObject();
     //   }

   //     if(balance != null) {
            balance = balance.parseFloat((String) in.readObject());

    //    }
     //   if(fun_balance != null) {
            fun_balance = fun_balance.parseFloat((String) in.readObject());
     //   }


    }



}
