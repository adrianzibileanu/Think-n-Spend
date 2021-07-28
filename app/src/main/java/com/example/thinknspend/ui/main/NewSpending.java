package com.example.thinknspend.ui.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thinknspend.MainActivity;
import com.example.thinknspend.R;
import com.example.thinknspend.main.SaveNLoad;
import com.example.thinknspend.main.Spending;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;

import static com.example.thinknspend.ui.main.MainFragment.balance;
import static com.example.thinknspend.ui.main.MainFragment.fun_balance;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_all;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_fun;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_should;
import static com.example.thinknspend.ui.main.MainFragment.spendingsList_useless;
import static com.example.thinknspend.ui.main.MainFragment.totalSpendings;

//import static com.example.thinknspend.ui.main.MainFragment.spendingsList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewSpending#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSpending extends Fragment {
    //public static ArrayList<Spending> spendingsList = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String spName;
    private float spAmount;
    private String spDate;
    private String spCurrency;
    private String spType;
    Calendar dateSelected = Calendar.getInstance();
    public DatePickerDialog datePickerDialog;
    public DateTimeFormatter dateFormatter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    private File saveFile = new File(path,"/" +"tns.ser");

    public NewSpending() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewSpending.
     */
    // TODO: Rename and change types and number of parameters
    public static NewSpending newInstance(String param1, String param2) {
        NewSpending fragment = new NewSpending();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("New Expense");

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_new_spending, container, false);
        View v = inflater.inflate(R.layout.fragment_new_spending, container, false);
        EditText edtDate = (EditText) v.findViewById(R.id.newSpDate);
        Spinner spinner = (Spinner) v.findViewById(R.id.spendingType);
    // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.spending_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinnerC = (Spinner) v.findViewById(R.id.newSpCurrency);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(this.getContext(),
                R.array.currency_aray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerC.setAdapter(adapterC);

        Button btnC = (Button) v.findViewById(R.id.selectDate);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View r) {
                //getting current day,month and year.

                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtDate.setText(day+"/"+month+"/"+year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });



        FloatingActionButton btn = (FloatingActionButton) v.findViewById(R.id.addSp2);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View y)  {
                MainFragment mf = new MainFragment();

                EditText edtName = (EditText) v.findViewById(R.id.newSpName);
                spName = edtName.getText().toString();
                EditText edtAmount = (EditText) v.findViewById(R.id.newSpAmount);

                spDate = edtDate.getText().toString();;
                Spinner spinCurrency = (Spinner) v.findViewById(R.id.newSpCurrency);
                spCurrency = spinCurrency.getSelectedItem().toString();
                Spinner spinType = (Spinner) v.findViewById(R.id.spendingType);
                spType = spinType.getSelectedItem().toString();

                if(spName.isEmpty() || edtAmount.getText().toString().isEmpty()){

                    edtName.setError("Expense name is required!");
                    edtAmount.setError("Expense amount is required!");

                }else{
                spAmount = Float.valueOf(edtAmount.getText().toString());
               // Spending sp = new Spending(spAmount, spCurrency, spType, spName, spDate);
                switch (spType){
                    case "Mandatory":
                        Spending sp0 = new Spending(spendingsList.size(),spAmount, spCurrency, spType, spName, spDate);
                        mf.setSpendingInfo(spendingsList, sp0);
                      //  mf.setSpendingInfo(spendingsList_all, sp0);
                        break;
                    case "Necessary":
                        Spending sp1 = new Spending(spendingsList_should.size(),spAmount, spCurrency, spType, spName, spDate);
                        mf.setSpendingInfo(spendingsList_should, sp1);
                        break;
                    case "Fun":

                        fun_balance = fun_balance-spAmount;
                        if(fun_balance < 0){
                            Spending sp2 = new Spending(spendingsList_fun.size(),spAmount + fun_balance, spCurrency, spType, spName, spDate);
                            mf.setSpendingInfo(spendingsList_fun, sp2);

                            Spending sp3 = new Spending(spendingsList_useless.size(),Math.abs(fun_balance), spCurrency, spType, spName, spDate);
                            mf.setSpendingInfo(spendingsList_useless, sp3);
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Depleted funds")
                                    .setMessage("Your fun expenses funds have been depleted and "+ fun_balance +sp3.getCurrency() +" have been wasted on nothing. Keep going and you'll end up broke!")

                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })

                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            fun_balance = 0.0f;
                            }else {
                            Spending sp2 = new Spending(spendingsList_fun.size(),spAmount, spCurrency, spType, spName, spDate);
                            mf.setSpendingInfo(spendingsList_fun, sp2);
                        }
                        break;
                    case "Useless":
                        Spending sp3 = new Spending(spendingsList_useless.size(),spAmount, spCurrency, spType, spName, spDate);
                        mf.setSpendingInfo(spendingsList_useless, sp3);
                        new AlertDialog.Builder(getContext())
                                .setTitle("Wasted money")
                                .setMessage("Your have succesfully wasted  "+ fun_balance +sp3.getCurrency() +"on nothing. I hope you are proud of yourself!")


                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        System.out.println("Added to "+spType);
                        break;
                }


                balance = balance-spAmount;
             //   if(balance <= 0){
            //        balance = 0.0f;
               // }
                //Save new data
                SaveNLoad save = new SaveNLoad(spendingsList, spendingsList_should, spendingsList_fun, spendingsList_useless, spendingsList_all, balance, fun_balance);
                save(saveFile, save);


                getActivity().getSupportFragmentManager().beginTransaction()
                        //.replace(R.id.main, ns, "findThisFragment")
                        .replace(R.id.container, mf, "mainFragment")
                        .addToBackStack(null)
                        .commit();

            }
            }
        });

        return v;

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