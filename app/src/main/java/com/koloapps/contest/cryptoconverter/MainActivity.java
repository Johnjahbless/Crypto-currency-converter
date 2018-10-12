package com.koloapps.contest.cryptoconverter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.koloapps.contest.cryptoconverter.APICALL.APIClient;
import com.koloapps.contest.cryptoconverter.APICALL.APIService;
import com.koloapps.contest.cryptoconverter.Model.ADA;
import com.koloapps.contest.cryptoconverter.Model.BCH;
import com.koloapps.contest.cryptoconverter.Model.BTC;
import com.koloapps.contest.cryptoconverter.Model.CryptoCrcy;
import com.koloapps.contest.cryptoconverter.Model.DASH;
import com.koloapps.contest.cryptoconverter.Model.DOGE;
import com.koloapps.contest.cryptoconverter.Model.EOS;
import com.koloapps.contest.cryptoconverter.Model.ETH;
import com.koloapps.contest.cryptoconverter.Model.LTC;
import com.koloapps.contest.cryptoconverter.Model.NEO;
import com.koloapps.contest.cryptoconverter.Model.PPC;
import com.koloapps.contest.cryptoconverter.Model.REP;
import com.koloapps.contest.cryptoconverter.Model.Utils;
import com.koloapps.contest.cryptoconverter.Model.XMR;
import com.koloapps.contest.cryptoconverter.Model.XRP;
import com.koloapps.contest.cryptoconverter.Model.ZEC;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    CryptoCrcy cryptoCrcy;
    BTC btc;
    ETH eth;
    ADA ada;
    BCH bch;
    DASH dash;
    EOS eos;
    LTC ltc;
    DOGE doge;
    PPC ppc;
    NEO neo;
    REP rep;
    XMR xmr;
    XRP xrp;
    ZEC zec;
    private InterstitialAd mInterstitialAd;
    double BtcGetUsd, EthGetUsd,AdaGetUsd, BchGetUsd, DashGetUsd, EosGetUsd, LtcGetUsd, DogeGetUsd, PpcGetUsd, NeoGetUsd, RepGetUsd, XmrGetUsd, XrpGetUsd, ZecGetUsd;
    ProgressDialog progressDialog;
    TextView textView_btc, textView_eth, textView_ada, textView_bch,textView_dash, textView_eos, textView_ltc, textView_lte, textView_mln, textView_neo, textView_rep, textView_xmr, textView_xrp, textView_zec;
    AdView mAdView2;
    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    DecimalFormat df = new DecimalFormat("####0.000");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3517746418699749~7280583326");
        mAdView2 = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3517746418699749/8344708392");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        
        //initialized progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.retriving));
        progressDialog.setMessage(getString(R.string.waitt));

        //start progressDialog
        progressDialog.show();
        LoadCryptoCompaire();

        textView_btc = (TextView) findViewById(R.id.btc_Text);
        textView_eth = (TextView) findViewById(R.id.eth_Text);
        textView_ada = (TextView) findViewById(R.id.ada_Text);
        textView_bch = (TextView) findViewById(R.id.bch_Text);
        textView_dash = (TextView) findViewById(R.id.dash_Text);
        textView_eos = (TextView) findViewById(R.id.eos_Text);
        textView_ltc = (TextView) findViewById(R.id.ltc_Text);
        textView_lte = (TextView) findViewById(R.id.lte_Text);
        textView_mln = (TextView) findViewById(R.id.mln_Text);
        textView_neo = (TextView) findViewById(R.id.neo_Text);
        textView_rep = (TextView) findViewById(R.id.rep_Text);
        textView_xmr = (TextView) findViewById(R.id.xmr_Text);
        textView_xrp = (TextView) findViewById(R.id.xrp_Text);
        textView_zec = (TextView) findViewById(R.id.zec_Text);



        TextView textView = (TextView) findViewById(R.id.retry);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        //initialized cardView
        CardView cardView_btc = (CardView) findViewById(R.id.btc_card);
        CardView cardView_eth = (CardView) findViewById(R.id.eth_card);
        CardView cardView_btc_eur = (CardView) findViewById(R.id.eur_cardbtc);
        CardView cardView_eth_eur = (CardView) findViewById(R.id.eur_cardeth);
        CardView cardView_btc_ghs = (CardView) findViewById(R.id.idr_cardbtc);
        CardView cardView_eth_ghs = (CardView) findViewById(R.id.idr_cardeth);
        CardView cardView_btc_zar = (CardView) findViewById(R.id.pkr_cardbtc);
        CardView cardView_eth_zar = (CardView) findViewById(R.id.pkr_cardeth);

        CardView cardView_btc_ngn = (CardView) findViewById(R.id.ngn_cardbtc);
        CardView cardView_eth_ngn = (CardView) findViewById(R.id.ngn_cardeth);
        CardView cardView_btc_brl = (CardView) findViewById(R.id.brl_cardbtc);
        CardView cardView_eth_brl = (CardView) findViewById(R.id.brl_cardeth);
        CardView cardView_btc_thb = (CardView) findViewById(R.id.thb_cardbtc);
        CardView cardView_eth_thb = (CardView) findViewById(R.id.thb_cardeth);

        //imitialised no network coonection error layout
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        //initialised swipe refresh layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                (android.R.color.black));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                frameLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
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
                shuffle();


            }
        });


        textView.setOnClickListener(new View.OnClickListener() {


                                        @Override
                                        public void onClick(View view) {
                                           swipeRefreshLayout.setRefreshing(true);
                                            frameLayout.setVisibility(View.GONE);
                                            linearLayout.setVisibility(View.VISIBLE);
                                            LoadCryptoCompaire();

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


                                    }
        );


        cardView_btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_eth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_btc_eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });



        cardView_btc_ghs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_ghs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_btc_brl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_brl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_ngn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent mintent = new Intent(getApplicationContext(), ConvertionActivity.class);
                mintent.putExtra("btc", BtcGetUsd);
                mintent.putExtra("eth", EthGetUsd);
                mintent.putExtra("ada", AdaGetUsd);
                mintent.putExtra("bch", BchGetUsd);
                mintent.putExtra("dash", DashGetUsd);
                mintent.putExtra("eos", EosGetUsd);
                mintent.putExtra("ltc", LtcGetUsd);
                mintent.putExtra("DOGE", DogeGetUsd);
                mintent.putExtra("PPC", PpcGetUsd);
                mintent.putExtra("neo", NeoGetUsd);
                mintent.putExtra("rep", RepGetUsd);
                mintent.putExtra("xmr", XmrGetUsd);
                mintent.putExtra("xrp", XrpGetUsd);
                mintent.putExtra("zec", ZecGetUsd);
                startActivity(mintent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_ngn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUsd);
                intent.putExtra("ada", AdaGetUsd);
                intent.putExtra("bch", BchGetUsd);
                intent.putExtra("dash", DashGetUsd);
                intent.putExtra("eos", EosGetUsd);
                intent.putExtra("ltc", LtcGetUsd);
                intent.putExtra("DOGE", DogeGetUsd);
                intent.putExtra("PPC", PpcGetUsd);
                intent.putExtra("neo", NeoGetUsd);
                intent.putExtra("rep", RepGetUsd);
                intent.putExtra("xmr", XmrGetUsd);
                intent.putExtra("xrp", XrpGetUsd);
                intent.putExtra("zec", ZecGetUsd);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_thb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUsd);
                intent.putExtra("ada", AdaGetUsd);
                intent.putExtra("bch", BchGetUsd);
                intent.putExtra("dash", DashGetUsd);
                intent.putExtra("eos", EosGetUsd);
                intent.putExtra("ltc", LtcGetUsd);
                intent.putExtra("DOGE", DogeGetUsd);
                intent.putExtra("PPC", PpcGetUsd);
                intent.putExtra("neo", NeoGetUsd);
                intent.putExtra("rep", RepGetUsd);
                intent.putExtra("xmr", XmrGetUsd);
                intent.putExtra("xrp", XrpGetUsd);
                intent.putExtra("zec", ZecGetUsd);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_thb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUsd);
                intent.putExtra("ada", AdaGetUsd);
                intent.putExtra("bch", BchGetUsd);
                intent.putExtra("dash", DashGetUsd);
                intent.putExtra("eos", EosGetUsd);
                intent.putExtra("ltc", LtcGetUsd);
                intent.putExtra("DOGE", DogeGetUsd);
                intent.putExtra("PPC", PpcGetUsd);
                intent.putExtra("neo", NeoGetUsd);
                intent.putExtra("rep", RepGetUsd);
                intent.putExtra("xmr", XmrGetUsd);
                intent.putExtra("xrp", XrpGetUsd);
                intent.putExtra("zec", ZecGetUsd);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_zar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUsd);
                intent.putExtra("ada", AdaGetUsd);
                intent.putExtra("bch", BchGetUsd);
                intent.putExtra("dash", DashGetUsd);
                intent.putExtra("eos", EosGetUsd);
                intent.putExtra("ltc", LtcGetUsd);
                intent.putExtra("DOGE", DogeGetUsd);
                intent.putExtra("PPC", PpcGetUsd);
                intent.putExtra("neo", NeoGetUsd);
                intent.putExtra("rep", RepGetUsd);
                intent.putExtra("xmr", XmrGetUsd);
                intent.putExtra("xrp", XrpGetUsd);
                intent.putExtra("zec", ZecGetUsd);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_zar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUsd);
                intent.putExtra("ada", AdaGetUsd);
                intent.putExtra("bch", BchGetUsd);
                intent.putExtra("dash", DashGetUsd);
                intent.putExtra("eos", EosGetUsd);
                intent.putExtra("ltc", LtcGetUsd);
                intent.putExtra("DOGE", DogeGetUsd);
                intent.putExtra("PPC", PpcGetUsd);
                intent.putExtra("neo", NeoGetUsd);
                intent.putExtra("rep", RepGetUsd);
                intent.putExtra("xmr", XmrGetUsd);
                intent.putExtra("xrp", XrpGetUsd);
                intent.putExtra("zec", ZecGetUsd);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });


    }

    @Override
    public void onPause() {
        if (mAdView2 != null) {
            mAdView2.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView2 != null) {
            mAdView2.resume();
        }
    }
    @Override
    public void onDestroy() {
        if (mAdView2 != null) {
            mAdView2.destroy();
        }
        super.onDestroy();
    }

    //when layout is pulled down, method for response
    private void shuffle() {
        frameLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        LoadCryptoCompaire();
    }


    //defined method for the response
    public void LoadCryptoCompaire() {
        //initialized the APIClient
        APIClient apiClient = new APIClient();
        try {
            //defined APIService to get client response
            APIService apiService = APIClient.getClient().create(APIService.class);
            //call client response
            Call<CryptoCrcy> btcCall = apiService.getBTC();
            btcCall.enqueue(new Callback<CryptoCrcy>() {
                @Override
                public void onResponse(Call<CryptoCrcy> call, Response<CryptoCrcy> response) {
                    //initialized respond class
                    cryptoCrcy = new CryptoCrcy();
                    cryptoCrcy = response.body();

                    //initialized the json object class
                    btc = new BTC();
                    eth = new ETH();
                    ada = new ADA();
                    bch = new BCH();
                    dash = new DASH();
                    eos = new EOS();
                    ltc = new LTC();
                    doge = new DOGE();
                    ppc = new PPC();
                    neo = new NEO();
                    rep = new REP();
                    xmr = new XMR();
                    xrp = new XRP();
                    zec = new ZEC();

                    btc = cryptoCrcy.getBTC();
                    eth = cryptoCrcy.getETH();
                    ada = cryptoCrcy.getADA();
                    bch = cryptoCrcy.getBCH();

                    dash = cryptoCrcy.getDASH();
                    eos = cryptoCrcy.getEOS();
                    ltc = cryptoCrcy.getLTC();
                    doge = cryptoCrcy.getDOGE();

                    ppc = cryptoCrcy.getPPC();
                    neo = cryptoCrcy.getNEO();
                    rep = cryptoCrcy.getREP();
                    xmr = cryptoCrcy.getXMR();

                    xrp = cryptoCrcy.getXRP();
                    zec = cryptoCrcy.getZEC();

                    //parse object to variable
                    BtcGetUsd = btc.getUSD();
                    EthGetUsd = eth.getUSD();
                    AdaGetUsd = ada.getUSD();
                    BchGetUsd = bch.getUSD();
                    DashGetUsd = dash.getUSD();
                    EosGetUsd = eos.getUSD();
                    LtcGetUsd = ltc.getUSD();
                    DogeGetUsd = doge.getUSD();
                    PpcGetUsd = ppc.getUSD();
                    NeoGetUsd = neo.getUSD();
                    RepGetUsd = rep.getUSD();
                    XmrGetUsd = xmr.getUSD();
                    XrpGetUsd = xrp.getUSD();
                    ZecGetUsd = zec.getUSD();
                    //display result in TextViews with Local Currency Symbols

                    textView_btc.setText("1 BTC : " + Utils.getCurrencySymbol("USD") + df.format(BtcGetUsd));
                    textView_eth.setText("1 ETH : " + Utils.getCurrencySymbol("USD") + df.format(EthGetUsd));
                    textView_ada.setText("1 ADA : " + Utils.getCurrencySymbol("USD") + df.format(AdaGetUsd));
                    textView_bch.setText("1 BCH : " + Utils.getCurrencySymbol("USD") + df.format(BchGetUsd));
                    textView_dash.setText("1 DASH : " + Utils.getCurrencySymbol("USD") + df.format(DashGetUsd));
                    textView_eos.setText("1 EOS : " + Utils.getCurrencySymbol("USD") + df.format(EosGetUsd));
                    textView_ltc.setText("1 LTC : " + Utils.getCurrencySymbol("USD") + df.format(LtcGetUsd));
                    textView_lte.setText("1 DOGE : " + Utils.getCurrencySymbol("USD") + df.format(DogeGetUsd));
                    textView_mln.setText("1 PPC : " + Utils.getCurrencySymbol("USD") + df.format(PpcGetUsd));
                    textView_neo.setText("1 NEO : " + Utils.getCurrencySymbol("USD") + df.format(NeoGetUsd));
                    textView_rep.setText("1 REP : " + Utils.getCurrencySymbol("USD") + df.format(RepGetUsd));
                    textView_xmr.setText("1 XMR : " + Utils.getCurrencySymbol("USD") + df.format(XmrGetUsd));
                    textView_xrp.setText("1 XRP : " + Utils.getCurrencySymbol("USD") + df.format(XrpGetUsd));
                    textView_zec.setText("1 ZEC : " + Utils.getCurrencySymbol("USD") + df.format(ZecGetUsd));







                    //stop progressDialog
                    progressDialog.dismiss();

                    //stop swipe view refresh
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), R.string.swipe_down, Toast.LENGTH_LONG).show();


                }

                //when network connection is not successful
                @Override
                public void onFailure(Call<CryptoCrcy> call, Throwable t) {
                    t.printStackTrace();
                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), R.string.swipe_down, Toast.LENGTH_LONG).show();

                }
            });


        } catch (Exception e) {

        }

    }

   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hy, Download this Bitcoin and Ethereum android application, it's very useful, you can track Bitcoin and Ethereum exchange prices and can also convert to your local currency, i Love it! Am sure you will also Check it out using this link here.... https://play.google.com/store/apps/details?id=com.koloapps.contest.cryptocomparecurrencyconverter");
            startActivity(shareIntent);

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

