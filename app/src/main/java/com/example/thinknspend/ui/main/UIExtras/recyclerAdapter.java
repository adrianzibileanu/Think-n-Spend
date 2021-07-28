package com.example.thinknspend.ui.main.UIExtras;

import android.content.DialogInterface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thinknspend.MainActivity;
import com.example.thinknspend.R;
import com.example.thinknspend.main.SaveNLoad;
import com.example.thinknspend.main.Spending;
import com.example.thinknspend.ui.main.MainFragment;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.example.thinknspend.ui.main.MainFragment.balance;
import static com.example.thinknspend.ui.main.MainFragment.balanceET;
import static com.example.thinknspend.ui.main.MainFragment.fun_balance;
import static com.example.thinknspend.ui.main.MainFragment.fun_balanceET;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_all;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_fun;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_should;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_useless;
import static com.example.thinknspend.ui.main.MainFragment.total;
import static com.example.thinknspend.ui.main.MainFragment.totalSpTv;
import static com.example.thinknspend.ui.main.MainFragment.totalSpendings;


public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder>{

    private Float floatParser;
    MainFragment mf = new MainFragment();
    private ArrayList<Spending> spendingsList;
    public recyclerAdapter(ArrayList<Spending> spendingsList){
        //mf.setSpendingsList(spendingsList);
        this.spendingsList = spendingsList;
    }
    private static onClickListner onclicklistner;
    private static final int VIEW_HEADER = 0;
    private static final int VIEW_NORMAL = 1;
    private View headerView;

    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    private File saveFile = new File(path,"/" +"tns.ser");

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        //here are the items we need in the list
        private TextView spName;
        private TextView spAmount;
        private TextView spCurrency;
        private TextView spDate;

        public MyViewHolder(final View view){
            super(view);

            spName = view.findViewById(R.id.spName_list);
            spAmount = view.findViewById(R.id.spAmount_list);
            spCurrency = view.findViewById(R.id.spCurrency_list);
            spDate = view.findViewById(R.id.spDate_list);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            System.out.println("Pressing "+spName.getText());



                            new AlertDialog.Builder(v.getContext())
                                .setTitle("Delete Expense")
                                //.setMessage("Are you sure you want to delete this expense? \n" + spName.getText() + " " + spAmount.getText() + " " + spCurrency.getText() + " " + spDate.getText())
                                .setMessage("Are you sure you want to delete this expense? \n" + spName.getText() + " " + spAmount.getText() + " " + spCurrency.getText() + " " + spDate.getText())

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (MainFragment.categTab) {
                                            case 0:
                                        //AN ID SYSTEM SHOULD BE IMPLEMENTED - THIS IS JUST A TEMPORARY WORKAROUND FOR DELETING ITEMS - USE WITH CAUTION!
                                        System.out.println(spendingsList.get(getAdapterPosition()).getType());
                                        switch (spendingsList.get(getAdapterPosition()).getType()) {
                                            case "Mandatory":
                                                for (Spending sp : MainFragment.spendingsList) {
                                                    if(MainFragment.spendingsList.size() == 1){
                                                        MainFragment.spendingsList.remove(sp);
                                                        break;
                                                    }
                                                    if (sp.equals(spendingsList.get(getAdapterPosition()).getName())) {
                                                        MainFragment.spendingsList.remove(sp);
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Necessary":
                                                for (Spending sp : spendingsList_should) {
                                                    if(spendingsList_should.size() == 1){
                                                        spendingsList_should.remove(sp);
                                                        break;
                                                    }
                                                    if (sp.getName().equals(spendingsList.get(getAdapterPosition()).getName())) {
                                                        System.out.println(sp.getName());
                                                        spendingsList_should.remove(sp);
                                                        break;
                                                    }
                                                }

                                                break;
                                            case "Fun":
                                                for (Spending sp : spendingsList_fun) {
                                                    if(spendingsList_fun.size() == 1){
                                                        fun_balance = fun_balance+sp.getAmount();
                                                        spendingsList_fun.remove(sp);
                                                        break;
                                                    }
                                                    if (sp.getName().equals(spendingsList.get(getAdapterPosition()).getName())) {
                                                        System.out.println(sp.getName());
                                                        fun_balance = fun_balance+sp.getAmount();
                                                        spendingsList_fun.remove(sp);
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Useless":
                                                for (Spending sp : spendingsList_useless) {
                                                    if(spendingsList_useless.size() == 1){
                                                        spendingsList_useless.remove(sp);
                                                        break;
                                                    }
                                                    if (sp.getName().equals(spendingsList.get(getAdapterPosition()).getName())) {
                                                        System.out.println(sp.getName());
                                                        spendingsList_useless.remove(sp);
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                                System.out.println(spendingsList.get(getAdapterPosition()).uid);
                                                balance = balance + spendingsList_all.get(getAdapterPosition()).getAmount();
                                                total = total - spendingsList_all.get(getAdapterPosition()).getAmount();
                                                spendingsList_all.remove(getAdapterPosition());



                                                break;
                                            case 1:

                                                spendingsList_all.remove(MainFragment.spendingsList.get(getAdapterPosition()));
                                                balance = balance + MainFragment.spendingsList.get(getAdapterPosition()).getAmount();
                                                total = total - MainFragment.spendingsList.get(getAdapterPosition()).getAmount();
                                                MainFragment.spendingsList.remove(getAdapterPosition());
                                                break;

                                            case 2:
                                                spendingsList_all.remove(spendingsList_should.get(getAdapterPosition()));
                                                balance = balance + spendingsList_should.get(getAdapterPosition()).getAmount();
                                                total = total - spendingsList_should.get(getAdapterPosition()).getAmount();
                                                spendingsList_should.remove(getAdapterPosition());
                                                break;
                                            case 3:
                                                spendingsList_all.remove(spendingsList_fun.get(getAdapterPosition()));
                                                balance = balance + spendingsList_fun.get(getAdapterPosition()).getAmount();
                                                fun_balance = fun_balance + spendingsList_fun.get(getAdapterPosition()).getAmount();
                                                total = total - spendingsList_fun.get(getAdapterPosition()).getAmount();
                                                spendingsList_fun.remove(getAdapterPosition());
                                                break;
                                            case 4:
                                                spendingsList_all.remove(spendingsList_useless.get(getAdapterPosition()));
                                                balance = balance + spendingsList_useless.get(getAdapterPosition()).getAmount();
                                                total = total - spendingsList_useless.get(getAdapterPosition()).getAmount();
                                                spendingsList_useless.remove(getAdapterPosition());
                                                break;
                                        }





                                        SaveNLoad save = new SaveNLoad(MainFragment.spendingsList, spendingsList_should, spendingsList_fun, spendingsList_useless, spendingsList_all, balance, fun_balance);
                                        save(saveFile, save);


                                        notifyDataSetChanged();
                                        balanceET.setText(balance.toString());
                                        fun_balanceET.setText(fun_balance.toString());
                                        totalSpTv.setText("Total: "+total.toString());


                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    return false;
                }



        }


    @NonNull
    @NotNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spending_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull recyclerAdapter.MyViewHolder holder, int position) {
     //   for(Spending sp : spendingsList){


        String name = spendingsList.get(position).getName();
        floatParser = spendingsList.get(position).getAmount();
        String amount = floatParser.toString();
        String currency = spendingsList.get(position).getCurrency();
        String date = spendingsList.get(position).getDate();
        holder.spName.setText(name);
        holder.spAmount.setText(amount);
        holder.spCurrency.setText(currency);
        holder.spDate.setText(date);

     //   }
    }

    public void setOnItemClickListener(onClickListner onclicklistner) {
        recyclerAdapter.onclicklistner = onclicklistner;
    }

    public void setHeader(View v) {
        this.headerView = v;
    }

    public interface onClickListner {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        //return spendingsList.size();
        return spendingsList.size();
    }

    public void save(File saveFile, SaveNLoad snl){
        try {
            FileOutputStream fos
                    = new FileOutputStream(saveFile);
            ObjectOutputStream oos
                    = new ObjectOutputStream(fos);
            oos.writeObject(snl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Save new data
    }

}