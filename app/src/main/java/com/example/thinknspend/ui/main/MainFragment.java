package com.example.thinknspend.ui.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thinknspend.MainActivity;
import com.example.thinknspend.R;
import com.example.thinknspend.main.SaveNLoad;
import com.example.thinknspend.main.Spending;
import com.example.thinknspend.ui.main.UIExtras.recyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class MainFragment extends Fragment {

    private static final int REQUESTED_PERMISSION = 1234;

    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    private File saveFile = new File(path,"/" +"tns.ser");
    private MainViewModel mViewModel;
    private RecyclerView recyclerView;
    public static TextView balanceET;
    public static TextView fun_balanceET;
    public static Float balance;
    public static Float fun_balance;
    public static int categTab;
    public static ArrayList<Spending> spendingsList = new ArrayList<>();
    public static ArrayList<Spending> spendingsList_should = new ArrayList<>();
    public static ArrayList<Spending> spendingsList_fun = new ArrayList<>();
    public static ArrayList<Spending> spendingsList_useless = new ArrayList<>();
    public static ArrayList<Spending> spendingsList_all = new ArrayList<>();
    public static ArrayList<Float> totalSpendings = new ArrayList<>();
    private TabLayout categoryTab;
    public static TextView totalSpTv;
    private View divider;
    public static Float total = 0.0f;
    private String[] permissionList = {"MANAGE_STORAGE_PERMISSION"};

    public recyclerAdapter getAdapter() {
        return adapter;
    }

    public void setrecyclerAdapter(recyclerAdapter adapter) {
        this.adapter = adapter;
    }

    private recyclerAdapter adapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Home");
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION},1);
        System.out.println(path.toString());
        if (Environment.isExternalStorageManager() != true)
        {
            new AlertDialog.Builder(getContext())
                    .setTitle("Access Required")
                    .setMessage("In order for the app to save your entries you will need to allow it Storage Access")

                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent permissionIntent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivity(permissionIntent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


            Log.e("DB", "PERMISSION GRANTED");
        }

        load(saveFile);


        //return inflater.inflate(R.layout.main_fragment, container, false);
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = v.findViewById(R.id.mandatorySp);
        //spendingsList = new ArrayList<>();
        totalSpTv = v.findViewById(R.id.spTotal);
        divider = v.findViewById(R.id.divider);

        balanceET = v.findViewById(R.id.balance);
        fun_balanceET = v.findViewById(R.id.fun_balance);
        setAdapter(spendingsList_all);
        total = 0.0f;
        for(Spending totalSpend : spendingsList_all){
           // balance -= totalSpend.getAmount();
            total += totalSpend.getAmount();
            System.out.println(totalSpend.getAmount());
            System.out.println(total);
        }
    //    if(balance != null) {
    //        balance = balance - total;
      //  }
        totalSpTv.setText("Total: "+ total.toString());
        FloatingActionButton btn = (FloatingActionButton) v.findViewById(R.id.addSp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(balance == null || balanceET.getText().toString().isEmpty()){
                    balanceET.setError("Balance required!");
                }else {
                   // ((MainActivity) getActivity()).setActionBarTitle("New Expense");
                    NewSpending ns = new NewSpending();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            //.replace(R.id.main, ns, "findThisFragment")
                            .replace(R.id.container, ns, "newSpendingFragment")
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
        if(balance != null){
            balanceET.setText(balance.toString());
        }
        if( fun_balance != null) {
            fun_balanceET.setText(fun_balance.toString());
        }

        balanceET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit balance");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                input.setText(balanceET.getText());
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (input == null) {
                            input.setError("Balance required!");
                          }else {
                                balance = balance.parseFloat(input.getText().toString());
                                balanceET.setText(input.getText().toString());
                            SaveNLoad save = new SaveNLoad(spendingsList, spendingsList_should, spendingsList_fun, spendingsList_useless, spendingsList_all, balance, fun_balance);
                            save(saveFile,save);
                            }

                        };
                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        fun_balanceET.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                 builder.setTitle("Edit fun balance");

                                                 final EditText input = new EditText(getContext());
                                                 input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                                 builder.setView(input);
                                                 input.setText(fun_balanceET.getText());
                                                 builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {

                                                         if(input == null) {
                                                             input.setError("Balance required!");

                                                         }else {
                                                             fun_balance = fun_balance.parseFloat(input.getText().toString());
                                                             fun_balanceET.setText(input.getText().toString());
                                                             SaveNLoad save = new SaveNLoad(spendingsList, spendingsList_should, spendingsList_fun, spendingsList_useless, spendingsList_all, balance, fun_balance);
                                                             save(saveFile,save);
                                                         }



                                                     }
                                                 });
                                                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         dialog.cancel();
                                                     }
                                                 });

                                                 builder.show();
                                             }
                                         });








        categoryTab = v.findViewById(R.id.tabbedPane);
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getText().toString());
                switch(tab.getText().toString()){
                    case "All":
                        setAdapter(spendingsList_all);
                        System.out.println(tab.getText().toString() + "Selected");
                        totalSpTv.setVisibility(View.VISIBLE);
                        divider.setVisibility(View.VISIBLE);
                        categTab = 0;
                        break;
                    case "Mandatory":
                        setAdapter(spendingsList);
                        System.out.println(tab.getText().toString() + "Selected");
                        totalSpTv.setVisibility(View.GONE);
                        divider.setVisibility(View.GONE);
                        categTab = 1;
                        break;
                    case "Necessary":
                        setAdapter(spendingsList_should);
                        System.out.println(tab.getText().toString() + "Selected");
                        totalSpTv.setVisibility(View.GONE);
                        divider.setVisibility(View.GONE);
                        categTab = 2;
                        break;
                    case "Fun":
                        setAdapter(spendingsList_fun);
                        System.out.println(tab.getText().toString() + "Selected");
                        totalSpTv.setVisibility(View.GONE);
                        divider.setVisibility(View.GONE);
                        categTab = 3;
                        break;
                    case "Useless":
                        setAdapter(spendingsList_useless);
                        System.out.println(tab.getText().toString() + "Selected");
                        totalSpTv.setVisibility(View.GONE);
                        divider.setVisibility(View.GONE);
                        categTab = 4;
                        break;
                }
                //setAdapter();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    public void setAdapter(ArrayList<Spending> al) {
        //recyclerAdapter adapter = new recyclerAdapter(spendingsList);
        adapter = new recyclerAdapter(al);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }


    public void setSpendingInfo(ArrayList<Spending> al, Spending sp) {
        //for(Spending spend : spendingsList)
        al.add(sp);
        //spendingsList_all.add(sp);
        spendingsList_all.add(al.get(sp.uid));
        totalSpendings.add(sp.getAmount());
        if(al.size() <= 0) {
            for (Float spend : totalSpendings)
                totalSpendings.remove(spend);
        }
        }
        //SaveNLoad snl;





    public void load(File saveFile){
        try {

            //Deserlization

            if(!saveFile.exists()) {
                saveFile.createNewFile();
            }

            FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SaveNLoad load = (SaveNLoad)ois.readObject();

            load.readExternal(ois);




            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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