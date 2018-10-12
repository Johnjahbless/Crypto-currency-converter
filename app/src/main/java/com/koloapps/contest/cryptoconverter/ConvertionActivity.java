package com.koloapps.contest.cryptoconverter;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.koloapps.contest.cryptoconverter.Model.Utils;

import java.text.DecimalFormat;

public class ConvertionActivity extends AppCompatActivity {
    DecimalFormat df = new DecimalFormat("####0.00");
    EditText first;
    TextView second, textView;
    Spinner spinner, spinner2;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;

    //defind String Array for spinner
    String[] spinner_first = {"BTC - BitCoin", "ETH - Ethereum", "LTC - Litecoin","ZEC - Zcash", "DASH - Dash", "XRP - Ripple",
    "XMR - Monero", "BCH - Bitcoin Cash", "NEO - NEO", "ADA - Cardeno", "REP - Augur", "DOGE - DOGE", "PPC - Peer Coin", "EOS - EOS"};
    String[] spinner_second = {"USD - US Dollar", "EUR", "NAIRA - Nigeria", "GBP - British Pound",
            "ING - Indian Ruppe", "AUD - Austrialian Dollar", "CAD - Canadian Dollar",
            "SGD - Singapore Dollar", "CHF - Swiss Frame", "MYR - Malaysian Riggit", "JPY - Japanese Yen",
            "CNY - Chinese Yuan Renminbi", "NZD - New Zealand Dollar", "ZAR - South Africa Rand", "BRL - Brazilian Real",
            "SAR - Saudi Arabian Riyal", "KES - Kenyan Shilling", "KRW - South Korean Won", "GHS - Ghanaian Cedi",
            "ARS - Argentine Peso", "RUB - Russian Ruble", "THB - Thailand THAI BAHT", "IDR - Indonesian Rupiah", "PKR - Pakistani Rupee",
    "PHP - Philippine Piso"};

    //defined variable for spinner selected value
    double first_selected, second_selected;

    //defined variable for the value parsed from MainActivity
    double BtcGetUsd, EthGetUsd,AdaGetUsd, BchGetUsd, DashGetUsd, EosGetUsd, LtcGetUsd, DogeGetUsd, PpcGetUsd, NeoGetUsd, RepGetUsd, XmrGetUsd, XrpGetUsd, ZecGetUsd;

    //defined variable to store EditText value
    double getText;

    ImageView imageView;
    View line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // get value parsed from MainActivity
        BtcGetUsd = getIntent().getExtras().getDouble("btc");
        EthGetUsd = getIntent().getExtras().getDouble("eth");
        AdaGetUsd = getIntent().getExtras().getDouble("ada");
        BchGetUsd = getIntent().getExtras().getDouble("bch");
        DashGetUsd = getIntent().getExtras().getDouble("dash");
        EosGetUsd = getIntent().getExtras().getDouble("eos");
        LtcGetUsd = getIntent().getExtras().getDouble("ltc");
        DogeGetUsd = getIntent().getExtras().getDouble("DOGE");
        PpcGetUsd = getIntent().getExtras().getDouble("PPC");
        NeoGetUsd = getIntent().getExtras().getDouble("neo");
        RepGetUsd = getIntent().getExtras().getDouble("rep");
        XmrGetUsd = getIntent().getExtras().getDouble("xmr");
        XrpGetUsd = getIntent().getExtras().getDouble("xrp");
        ZecGetUsd = getIntent().getExtras().getDouble("zec");

        //initialized views
        first = (EditText) findViewById(R.id.firstEdit);
        second = (TextView) findViewById(R.id.secondEdit);
        textView = (TextView) findViewById(R.id.textView);
        line = findViewById(R.id.view);
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3517746418699749~7280583326");
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //initialized spinnerFirst
        spinner = (Spinner) findViewById(R.id.spinnerFirst);
        //define ArrayAdapter1 for the Spinner and String Array defined (spinner_first)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ConvertionActivity.this,
                R.layout.support_simple_spinner_dropdown_item, spinner_first);
        //parse arrayAdapter1
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = spinner.getSelectedItemPosition();
                if (pos == 0) {
                    textView.setText("bitcoin");
                    //get Spinner selected item value
                    first_selected = BtcGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    line.setBackgroundColor(getResources().getColor(R.color.btcColor));
                }
                if (pos == 1) {
                    //get Spinner selected item value
                    first_selected = EthGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("ethereum");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }

                if (pos == 2) {
                    //get Spinner selected item value
                    first_selected = LtcGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("litecoin");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 3) {
                    //get Spinner selected item value
                    first_selected = ZecGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("zcash");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 4) {
                    //get Spinner selected item value
                    first_selected = DashGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("dash");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 5) {
                    //get Spinner selected item value
                    first_selected = XrpGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("ripple");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 6) {
                    //get Spinner selected item value
                    first_selected = XmrGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("monero");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 7) {
                    //get Spinner selected item value
                    first_selected = BchGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("bitcoin cash");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }

                if (pos == 8) {
                    //get Spinner selected item value
                    first_selected = NeoGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("neo");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 9) {
                    //get Spinner selected item value
                    first_selected = AdaGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("cardeno");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 10) {
                    //get Spinner selected item value
                    first_selected = RepGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("augur");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 11) {
                    //get Spinner selected item value
                    first_selected = DogeGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("doge");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 12) {
                    //get Spinner selected item value
                    first_selected = PpcGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("peer coin");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
                if (pos == 13) {
                    //get Spinner selected item value
                    first_selected = EosGetUsd;

                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    textView.setText("eos");
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //initialized spinnersecond
        spinner2 = (Spinner) findViewById(R.id.spinnersecond);

        //define ArrayAdapter2 for the Spinner and String Array defined (spinner_second)
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(ConvertionActivity.this,
                R.layout.support_simple_spinner_dropdown_item, spinner_second);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get spinner2 selected item value to string
                String sel = spinner2.getSelectedItem().toString();

                //compare the value
                //if true second_selected should set currency exchange rate. e.g EUR = 1.16095
                if (sel == "USD - US Dollar") {
                    second_selected = 1;
                    //Convert
                    double uu = (first_selected / second_selected) * getText;
                    //display the result in TextView with Local Currency Symbol
                    second.setText(Utils.getCurrencySymbol("USD") + df.format(uu));
                } else if (sel == "EUR") {
                    second_selected = 1.16095;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("EUR") + df.format(uu));
                } else if (sel == "NAIRA - Nigeria") {
                    second_selected = 0.0033;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("NGN") + df.format(uu));
                } else if (sel == "GBP - British Pound") {
                    second_selected = 1.31281;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("GBP") + df.format(uu));
                } else if (sel == "ING - Indian Ruppe") {
                    second_selected = 0.01538;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(df.format(uu));
                } else if (sel == "AUD - Austrialian Dollar") {
                    second_selected = 0.76834;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("AUD") + df.format(uu));
                } else if (sel == "CAD - Canadian Dollar") {
                    second_selected = 0.78099;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("CAD") + df.format(uu));
                } else if (sel == "SGD - Singapore Dollar") {
                    second_selected = 0.73270;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("SGD") + df.format(uu));
                } else if (sel == "CHF - Swiss Franc") {
                    second_selected = 1.00224;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("CHF") + df.format(uu));
                } else if (sel == "MYR - Malaysian Riggit") {
                    second_selected = 0.23565;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("MYR") + df.format(uu));
                } else if (sel == "JPY - Japanese Yen") {
                    second_selected = 0.00880;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("JPY") + df.format(uu));
                } else if (sel == "CNY - Chinese Yuan Renminbi") {
                    second_selected = 0.15040;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("CNY") + df.format(uu));
                } else if (sel == "NZD - New Zealand Dollar") {
                    second_selected = 0.68779;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("NZD") + df.format(uu));
                } else if (sel == "ZAR - South Africa Rand") {
                    second_selected = 0.07097;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("ZAR") + df.format(uu));
                } else if (sel == "BRL - Brazilian Real") {
                    second_selected = 0.30903;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("BRL") + df.format(uu));
                } else if (sel == "SAR - Saudi Arabian Riyal") {
                    second_selected = 0.26665;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("SAR") + df.format(uu));
                } else if (sel == "KES - Kenyan Shilling") {
                    second_selected = 0.00962;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("KES") + df.format(uu));
                } else if (sel == "KRW - South Korean Won") {
                    second_selected = 0.00089;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("KRW") + df.format(uu));
                } else if (sel == "GHS - Ghanaian Cedi") {
                    second_selected = 0.22547;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("GHS") + df.format(uu));
                } else if (sel == "ARS - Argentine Peso") {
                    second_selected = 0.05687;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("ARS") + df.format(uu));
                } else if (sel == "RUB - Russian Ruble") {
                    second_selected = 0.01719;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("RUB") + df.format(uu));
                }  else if (sel == "THB - Thailand THAI BAHT") {
                    second_selected = 0.030;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("THB") + df.format(uu));
                } else if (sel == "IDR - Indonesian Rupiah") {
                    second_selected = 0.0000693873;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("IDR") + df.format(uu));
                } else if (sel == "PKR - Pakistani Rupee") {
                    second_selected = 0.00822098;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("PKR") + df.format(uu));
                } else if (sel == "PHP - Philippine Piso") {
                    second_selected = 0.0187012;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("PKR") + df.format(uu));
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String fd = editable.toString();
                if (fd.length() > 0) {
                    getText = Double.valueOf(fd);
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(df.format(uu));
                }
            }
        });
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });


        }

        if (id == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hy, Download this Bitcoin and Ethereum android application, it's very useful, you can track Bitcoin and Ethereum exchange prices and can also convert to your local currency, i Love it! Am sure you will also Check it out using this link here.... https://play.google.com/store/apps/details?id=com.koloapps.contest.cryptocomparecurrencyconverter");
            startActivity(shareIntent);




        /* } if (id == R.id.settings) {
            Intent intent = new Intent(this, Main4Activity.class);
            startActivity(intent);
            intent.putExtra("btc", BtcGetUsd);
            intent.putExtra("eth", EthGetUsd);

            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });

           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.notifications_allow);
            alertDialogBuilder
                    .setMessage("")
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), R.string.settings_saved, Toast.LENGTH_LONG).show();


                                }
                            })

                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), R.string.settings_saved, Toast.LENGTH_LONG).show();
                            dialog.cancel();

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
*/
        }

        if (id == R.id.exit) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.exitApplication);
            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton(R.string.exit,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);

                                }
                            })

                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



        } if (id == R.id.news) {
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });


        }





        return super.onOptionsItemSelected(item);


            // Inflate the menu; this adds items to the action bar if it is present.


        }
    }


